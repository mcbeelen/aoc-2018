package y2019.computer

import y2019.computer.ParameterMode.*

data class Memory(val program: List<Int>, val instructionPointer: Int = 0)

class IntcodeComputer(
        private val input: Input = AlwaysZeroInput(),
        private val output : Output = WriteToSystemOutOutput(),
        internal var memory : Memory) {

    constructor(
            input: Input = AlwaysZeroInput(),
            output : Output = WriteToSystemOutOutput(),
            sourceCode: List<Int>,
            instructionPointer: Int = 0) : this(input, output, Memory(sourceCode, instructionPointer))

    constructor(intCode: String) : this(memory = Memory(parseIntCode(intCode)))
    constructor(input: Input, output: Output, sourceCode: List<Int>) : this(input, output, Memory(sourceCode))
    constructor(sourceCode: String, instructionPointer: Int) : this(memory = Memory(parseIntCode(sourceCode), instructionPointer))



    fun tick() {

        val currentInstruction = currentInstruction()
        val opcode = currentOpcode(currentInstruction)
        val instruction = fetchInstruction(opcode)

        val parameterModeFlags = fetchParameterModeFlags(currentInstruction)

        val parameters = fetchParameters(opcode, parameterModeFlags)

        val effect: Effect = instruction.handle(parameters)
        val newIntCode = effect.apply(memory.program)
        val newInstructionPointer = effect.goto(memory.instructionPointer, instruction.numberOfParameters())

        memory = Memory(newIntCode, newInstructionPointer)
    }


    private fun currentOpcode(currentInstruction: Int) = currentInstruction % 100

    private fun fetchInstruction(opcode: Int): Instruction {

        return when (opcode) {
            1 -> AddInstruction()
            2 -> MultiplyInstruction()
            3 -> ReadInputInstruction(input)
            4 -> WriteToOutputInstruction(memory.program, output)
            5 -> JumpIfTrueInstruction()
            6 -> JumpIfFalseInstruction()
            7 -> LessThanInstruction()
            8 -> EqualsInstruction()
            99 -> ExitInstruction()
            else -> UnknownOpcodeInstruction(currentInstruction(), opcode)
        }

    }

    private fun fetchParameters(opcode: Int, parameterModes: List<ParameterMode>): List<Int> {

        val instructionPointer = memory.instructionPointer

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
        val instructionPointer = memory.instructionPointer
        return when (determineParameterMode(index, parameterModes)) {
            POSITION -> readParameterByPosition(instructionPointer + 1 + index)
            IMMEDIATE -> readParameterImmediatly(instructionPointer + 1 + index)
        }

    }

    private fun readParameterByPosition(index: Int) = readParameterImmediatly(memory.program[index])


    private fun readParameterImmediatly(index: Int) = memory.program[index]




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


    fun isProgramFinished() = currentInstruction() == 99

    private fun currentInstruction() = memory.program[memory.instructionPointer]
}

interface Effect {
    fun apply(intCode: List<Int>): List<Int> {
        return intCode
    }

    fun goto(instructionPointer: Int, numberOfParameters: Int) = instructionPointer + 1 + numberOfParameters
}

data class WriteToMemoryEffect(val address: Int, val value: Int) : Effect {
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

fun parseIntCode(intCode: String) = intCode.split(',').map { it.toInt() }

