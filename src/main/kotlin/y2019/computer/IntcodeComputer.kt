package y2019.computer

import y2019.computer.ParameterMode.IMMEDIATE
import y2019.computer.ParameterMode.POSITION
import kotlin.Int.Companion.MIN_VALUE

data class State(val memory: Memory, val instructionPointer: Int = 0, val relativeBase: Int = 0) {

    constructor(sourceCode: String) : this(memory = Memory(sourceCode))
    constructor(sourceCode: String, instructionPointer: Int) : this(memory = Memory(sourceCode), instructionPointer = instructionPointer)

    fun updateMemory(effect: Effect): State = this.copy(
            memory = memory.updateMemory(effect)
    )

    fun jump(effect: Effect, instruction: Instruction) = this.copy(
            instructionPointer = effect.goto(instructionPointer, instruction.numberOfParameters())
    )

    fun readFromMemory(address: Int) = memory.read(address)

    fun fetch() = readFromMemory(instructionPointer)

}

data class Memory(private val program: List<Int>) {
    constructor(sourceCode: String) : this(program = compile(sourceCode))

    fun updateMemory(effect: Effect): Memory = this.copy(
            program = effect.apply(program)
    )

    fun read(address: Int): Int = program[address]
}

class IntcodeComputer(
        private val input: Input = AlwaysZeroInput(),
        private val output: Output = WriteToSystemOutOutput(),
        internal var state: State) {

    constructor(
            input: Input = AlwaysZeroInput(),
            output: Output = WriteToSystemOutOutput(),
            sourceCode: String,
            instructionPointer: Int = 0) : this(input, output, State(sourceCode, instructionPointer))

    constructor(sourceCode: String) : this(state = State(sourceCode))
    constructor(input: Input, output: Output, sourceCode: String) : this(input = input, output = output, state = State(sourceCode))
    constructor(input: Input, output: Output, byteCode: List<Int>) : this(input = input, output = output, state = State(memory = Memory(byteCode)))

    constructor(sourceCode: String, instructionPointer: Int) : this(state = State(sourceCode, instructionPointer))
    constructor(byteCode: List<Int>) : this(state = State(memory = Memory(byteCode)))


    fun tick() {

        val currentInstruction = currentInstruction()
        val opcode = currentOpcode(currentInstruction)
        val instruction = fetchInstruction(opcode)

        val parameterModeFlags = fetchParameterModeFlags(currentInstruction)

        val parameters = fetchParameters(opcode, parameterModeFlags)

        val effect: Effect = instruction.handle(parameters)

        state = state
                .updateMemory(effect)
                .jump(effect, instruction)
    }


    private fun currentOpcode(currentInstruction: Int) = currentInstruction % 100

    private fun fetchInstruction(opcode: Int): Instruction {

        return when (opcode) {
            1 -> AddInstruction()
            2 -> MultiplyInstruction()
            3 -> ReadInputInstruction(input)
            4 -> WriteToOutputInstruction(output)
            5 -> JumpIfTrueInstruction()
            6 -> JumpIfFalseInstruction()
            7 -> LessThanInstruction()
            8 -> EqualsInstruction()
            99 -> ExitInstruction(state.instructionPointer)
            else -> UnknownOpcodeInstruction(currentInstruction(), opcode)
        }

    }

    private fun fetchParameters(opcode: Int, parameterModes: List<ParameterMode>): List<Int> {

        val instructionPointer = state.instructionPointer

        return when (opcode) {
            1 -> listOf(fetchParameter(0, parameterModes), fetchParameter(1, parameterModes), readParameterImmediatly(instructionPointer + 3))
            2 -> listOf(fetchParameter(0, parameterModes), fetchParameter(1, parameterModes), readParameterImmediatly(instructionPointer + 3))
            3 -> listOf(readParameterImmediatly(instructionPointer + 1))
            4 -> listOf(fetchParameter(0, parameterModes))
            5 -> listOf(fetchParameter(0, parameterModes), fetchParameter(1, parameterModes))
            6 -> listOf(fetchParameter(0, parameterModes), fetchParameter(1, parameterModes))
            7 -> listOf(fetchParameter(0, parameterModes), fetchParameter(1, parameterModes), readParameterImmediatly(instructionPointer + 3))
            8 -> listOf(fetchParameter(0, parameterModes), fetchParameter(1, parameterModes), readParameterImmediatly(instructionPointer + 3))
            else -> emptyList()
        }
    }

    private fun fetchParameter(index: Int, parameterModes: List<ParameterMode>): Int {
        val instructionPointer = state.instructionPointer
        return when (determineParameterMode(index, parameterModes)) {
            POSITION -> readParameterByPosition(instructionPointer + 1 + index)
            IMMEDIATE -> readParameterImmediatly(instructionPointer + 1 + index)
        }

    }

    private fun readParameterByPosition(address: Int) = readParameterImmediatly(state.readFromMemory(address))


    private fun readParameterImmediatly(address: Int) = state.readFromMemory(address)


    private fun determineParameterMode(index: Int, parameterModeFlags: List<ParameterMode>) =
            if (index < parameterModeFlags.size)
                parameterModeFlags[index]
            else
                POSITION

    private fun fetchParameterModeFlags(currentInstruction: Int): List<ParameterMode> {
        if (currentInstruction <= 99) return emptyList()
        val instructionCode = currentInstruction.toString()
        return instructionCode.take(instructionCode.length - 2).reversed()
                .map { toParameterMode(it) }
                .toList()
    }


    fun isProgramFinished() = state.instructionPointer == MIN_VALUE

    private fun currentInstruction() = state.fetch()
}


interface Update {

}

interface Effect {
    fun apply(intCode: List<Int>): List<Int> {
        return intCode
    }

    fun goto(instructionPointer: Int, numberOfParameters: Int) = instructionPointer + 1 + numberOfParameters
}

data class WriteToMemoryEffect(val address: Int, val value: Int) : Effect, Update {
    override fun apply(intCode: List<Int>): List<Int> {
        val newIntCode = intCode.toMutableList()
        newIntCode[address] = value
        return newIntCode
    }

}

class NoOpEffect : Effect {
}

class JumpToEffect(val position: Int) : Effect {
    override fun goto(instructionPointer: Int, numberOfParameters: Int) = position
}

class StopEffect() : Effect {
    override fun goto(instructionPointer: Int, numberOfParameters: Int) = MIN_VALUE
}

fun compile(sourceCode: String) = sourceCode.split(',').map { it.toInt() }

