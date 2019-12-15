package y2019.computer

import y2019.computer.ParameterMode.IMMEDIATE
import y2019.computer.ParameterMode.POSITION
import y2019.computer.ParameterMode.RELATIVE
import java.math.BigInteger
import kotlin.Int.Companion.MIN_VALUE


typealias Address = Int
typealias Value = Long
typealias Opcode = Int
typealias Modification = Pair<Address, Value>

typealias ByteCode = List<Value>

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
    constructor(input: Input, output: Output, byteCode: List<Value>) : this(input = input, output = output, state = State(memory = Memory(byteCode)))

    constructor(sourceCode: String, instructionPointer: Int) : this(state = State(sourceCode, instructionPointer))
    constructor(byteCode: List<Value>) : this(state = State(memory = Memory(byteCode)))


    fun tick() {

        val currentInstruction = currentInstruction()
        val opcode = currentOpcode(currentInstruction)
        val instruction = fetchInstruction(opcode)

        val parameterModeFlags = fetchParameterModeFlags(currentInstruction)

        val parameters = fetchParameters(opcode, parameterModeFlags)

        val effect: Effect = instruction.handle(parameters)

        state = state.applyEffects(effect, instruction)
    }


    private fun currentOpcode(currentInstruction: Value): Opcode = (currentInstruction % 100).toInt()

    private fun fetchInstruction(opcode: Opcode): Instruction {

        return when (opcode) {
            1 -> AddInstruction()
            2 -> MultiplyInstruction()
            3 -> ReadInputInstruction(input)
            4 -> WriteToOutputInstruction(output)
            5 -> JumpIfTrueInstruction()
            6 -> JumpIfFalseInstruction()
            7 -> LessThanInstruction()
            8 -> EqualsInstruction()
            9 -> AdjustRelativeBaseInstruction()
            99 -> ExitInstruction(state.instructionPointer)
            else -> UnknownOpcodeInstruction(currentInstruction(), opcode)
        }

    }

    private fun fetchParameters(opcode: Opcode, parameterModes: List<ParameterMode>): List<Value> {

        return when (opcode) {
            1 -> listOf(fetchInputParameter(0, parameterModes), fetchInputParameter(1, parameterModes), fetchWriteParameter(2, parameterModes))
            2 -> listOf(fetchInputParameter(0, parameterModes), fetchInputParameter(1, parameterModes), fetchWriteParameter(2, parameterModes))
            3 -> listOf(fetchWriteParameter(0, parameterModes))
            4 -> listOf(fetchInputParameter(0, parameterModes))
            5 -> listOf(fetchInputParameter(0, parameterModes), fetchInputParameter(1, parameterModes))
            6 -> listOf(fetchInputParameter(0, parameterModes), fetchInputParameter(1, parameterModes))
            7 -> listOf(fetchInputParameter(0, parameterModes), fetchInputParameter(1, parameterModes), fetchWriteParameter(2, parameterModes))
            8 -> listOf(fetchInputParameter(0, parameterModes), fetchInputParameter(1, parameterModes), fetchWriteParameter(2, parameterModes))
            9 -> listOf(fetchInputParameter(0, parameterModes))
            else -> emptyList()
        }
    }

    private fun fetchWriteParameter(index: Int, parameterModes: List<ParameterMode>): Value {
        val instructionPointer = state.instructionPointer
        return when (determineParameterMode(index, parameterModes)) {
            RELATIVE -> {
                val offset = readParameterImmediatly(instructionPointer + 1 + index)
                return state.relativeBase + offset
            }
            else -> readParameterImmediatly(instructionPointer + 1 + index)
        }
    }

    private fun fetchInputParameter(index: Address, parameterModes: List<ParameterMode>): Value {
        val instructionPointer = state.instructionPointer
        return when (determineParameterMode(index, parameterModes)) {
            POSITION -> readParameterByPosition(instructionPointer + 1 + index)
            IMMEDIATE -> readParameterImmediatly(instructionPointer + 1 + index)
            RELATIVE -> state.readRelativeParam(index)
        }

    }

    private fun readParameterByPosition(address: Address) : Value = readParameterImmediatly(state.readFromMemory(address).toAddress())


    private fun readParameterImmediatly(address: Address): Value = state.readFromMemory(address)


    private fun determineParameterMode(index: Int, parameterModeFlags: List<ParameterMode>) =
            if (index < parameterModeFlags.size)
                parameterModeFlags[index]
            else
                POSITION

    private fun fetchParameterModeFlags(currentInstruction: Value): List<ParameterMode> {
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

fun Long.toAddress(): Address = this.toInt()


interface Update {
    fun getModification(): Modification
}

interface RelativeBaseAdjustment {
    fun getAdjustment() : Int
}

interface Effect {
    fun goto(instructionPointer: Int, numberOfParameters: Int) = instructionPointer + 1 + numberOfParameters
}




data class WriteToMemoryEffect(val address: Address, val value: Value) : Effect, Update {
    override fun getModification(): Pair<Address, Value> = Pair(address, value)
}

class NoOpEffect : Effect {
}

class JumpToEffect(val address: Address) : Effect {
    override fun goto(instructionPointer: Int, numberOfParameters: Int) = address
}

class StopEffect() : Effect {
    override fun goto(instructionPointer: Int, numberOfParameters: Int) = MIN_VALUE
}


class AdjustRelativeBaseEffect(private val offset: Int) : Effect, RelativeBaseAdjustment {
    override fun getAdjustment() = offset

}

fun compile(sourceCode: String) : List<Value> = sourceCode.split(',').map { it.toLong() }

