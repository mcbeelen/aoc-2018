package y2019.computer

import y2019.computer.ParameterMode.IMMEDIATE
import y2019.computer.ParameterMode.POSITION
import y2019.computer.ParameterMode.RELATIVE
import kotlin.Int.Companion.MIN_VALUE

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

        state = state.applyEffects(effect, instruction)
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
            RELATIVE -> state.readRelativeParam(index)
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

    fun runProgram() {
        while (!isProgramFinished()) {
            tick()
        }
    }
}

typealias Address = Int
typealias Value = Int

typealias Modification = Pair<Address, Value>

interface Update {
    fun getModification(): Modification

}

interface Effect {
    fun apply(intCode: List<Int>): List<Int> {
        return intCode
    }

    fun goto(instructionPointer: Int, numberOfParameters: Int) = instructionPointer + 1 + numberOfParameters
}


data class WriteToMemoryEffect(val address: Address, val value: Value) : Effect, Update {
    override fun apply(intCode: List<Int>): List<Int> {
        val newIntCode = intCode.toMutableList()
        newIntCode[address] = value
        return newIntCode
    }

    override fun getModification(): Pair<Address, Value> = Pair(address, value)

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

