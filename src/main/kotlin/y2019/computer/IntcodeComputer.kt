package y2019.computer

import y2019.computer.ParameterMode.*

data class IntcodeComputer(
        val input: Input = AlwaysZeroInput(),
        val output : Output = WriteToSystemOutOutput(),
        val program: List<Int>,
        val instructionPointer: Int = 0) {

    constructor(intCode: String) : this(program = parseIntCode(intCode))
    constructor(intCode: String, cursor: Int) : this(program = parseIntCode(intCode), instructionPointer = cursor)

    fun tick(): IntcodeComputer {

        val currentInstruction = currentInstruction()
        val opcode = currentOpcode(currentInstruction)
        val instruction = fetchInstruction(opcode)

        val parameterModeFlags = fetchParameterModeFlags(currentInstruction)

        val parameters = fetchParameters(opcode, parameterModeFlags)

        val effect: Effect = instruction.handle(parameters)
        val newIntCode = effect.apply(program)
        val newInstructionPointer = effect.goto(instructionPointer, instruction.numberOfParameters())

        return this.copy(program = newIntCode, instructionPointer = newInstructionPointer)
    }


    private fun currentOpcode(currentInstruction: Int) = currentInstruction % 100

    private fun fetchInstruction(opcode: Int): Instruction {

        return when (opcode) {
            1 -> AddInstruction()
            2 -> MultiplyInstruction()
            3 -> ReadInputInstruction(input)
            4 -> WriteToOutputInstruction(program, output)
            5 -> JumpIfTrueInstruction()
            6 -> JumpIfFalseInstruction()
            7 -> LessThanInstruction()
            8 -> EqualsInstruction()
            99 -> ExitInstruction()
            else -> UnknownOpcodeInstruction(currentInstruction(), opcode)
        }

    }

    private fun fetchParameters(opcode: Int, parameterModes: List<ParameterMode>): List<Int> {

        return when {
            opcode == 1 -> listOf(fetchParameter(0, parameterModes), fetchParameter(1, parameterModes), readParameterImmediatly(instructionPointer + 3))
            opcode == 2 -> listOf(fetchParameter(0, parameterModes), fetchParameter(1, parameterModes), readParameterImmediatly(instructionPointer + 3))
            opcode == 3 -> listOf(readParameterImmediatly(instructionPointer + 1))
            opcode == 4 -> listOf(fetchParameter(0, parameterModes))
            opcode == 5 -> listOf(fetchParameter(0, parameterModes), fetchParameter(1, parameterModes))
            opcode == 6 -> listOf(fetchParameter(0, parameterModes), fetchParameter(1, parameterModes))
            opcode == 7 -> listOf(fetchParameter(0, parameterModes), fetchParameter(1, parameterModes), readParameterImmediatly(instructionPointer + 3))
            opcode == 8 -> listOf(fetchParameter(0, parameterModes), fetchParameter(1, parameterModes), readParameterImmediatly(instructionPointer + 3))
            else -> emptyList()

        }
    }

    private fun fetchParameter(index: Int, parameterModes: List<ParameterMode>): Int {
        return when (determineParameterMode(index, parameterModes)) {
            POSITION -> readParameterByPosition(instructionPointer + 1 + index)
            IMMEDIATE -> readParameterImmediatly(instructionPointer + 1 + index)
        }

    }

    private fun readParameterByPosition(index: Int) = readParameterImmediatly(program[index])


    private fun readParameterImmediatly(index: Int) = program[index]




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

    private fun currentInstruction() = program[instructionPointer]
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

