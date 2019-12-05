package y2019.computer

import y2019.computer.ParameterMode.*

data class IntCodeSimulator(
        val input: Input = AlwaysZeroInput(),
        val intCode: List<Int>,
        val instructionPointer: Int = 0) {

    constructor(intCode: String) : this(intCode = parseIntCode(intCode))
    constructor(intCode: String, cursor: Int) : this(intCode = parseIntCode(intCode), instructionPointer = cursor)

    fun tick(): IntCodeSimulator {

        val currentInstruction = currentInstruction()
        val opcode = currentOpcode(currentInstruction)
        val instruction = fetchInstruction(opcode)

        val parameterModeFlags = fetchParameterModeFlags(currentInstruction)

        val parameters = fetchParameters(opcode, parameterModeFlags)
        val effect: Effect = instruction.handle(parameters)
        val newIntCode = effect.apply(intCode)

        val newInstructionPointer = instructionPointer + 1 + instruction.numberOfParameters()

        return IntCodeSimulator(input, newIntCode, newInstructionPointer)
    }


    private fun currentOpcode(currentInstruction: Int) = currentInstruction % 100

    private fun fetchInstruction(opcode: Int): Instruction {

        return when (opcode) {
            1 -> AddInstruction()
            2 -> MultiplyInstruction()
            3 -> ReadInputInstruction(input)
            4 -> WriteToOutputInstruction(intCode)
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

            else -> emptyList()

        }
    }

    private fun fetchParameter(index: Int, parameterModes: List<ParameterMode>): Int {
        return when (determineParameterMode(index, parameterModes)) {
            POSITION -> readParameterByPosition(instructionPointer + 1 + index)
            IMMEDIATE -> readParameterImmediatly(instructionPointer + 1 + index)
        }

    }

    private fun readParameterByPosition(index: Int) = readParameterImmediatly(intCode[index])


    private fun readParameterImmediatly(index: Int) = intCode[index]




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

    private fun currentInstruction() = intCode[instructionPointer]
}

interface Effect {
    fun apply(intCode: List<Int>): List<Int> {
        return intCode
    }
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

fun parseIntCode(intCode: String) = intCode.split(',').map { it.toInt() }

