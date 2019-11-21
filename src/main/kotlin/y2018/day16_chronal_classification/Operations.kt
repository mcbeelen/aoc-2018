package y2018.day16_chronal_classification

import y2018.day16_chronal_classification.OpCode.*

enum class OpCode(val instruction: Int) {
    ADDI(4),
    ADDR(11),

    MULI(1),
    MULR(13),

    BANI(10),
    BANR(0),

    BORI(2),
    BORR(8),

    SETI(14),
    SETR(3),

    GTIR(7),
    GRRI(6),
    GTRR(15),

    EQIR(12),
    EQRI(9),
    EQRR(5),
    
    
    
    
    
    NOOP(9999)
    
}


val opCodes = values().toList().sortedBy { it.instruction }
fun opCode(opCodeIndex: Int) = opCodes[opCodeIndex]

data class Instruction(val opCode: OpCode, val arguments: IntArray)


fun performOperation(instruction: Instruction, registers: IntArray): IntArray {
    return when (instruction.opCode) {
        ADDI -> performAddImmediate(instruction, registers)
        ADDR -> performAddRegisters(instruction, registers)

        MULI -> performMultiplyImmediate(instruction, registers)
        MULR -> performMultiplyRegisters(instruction, registers)

        SETI -> performSetImmediate(instruction, registers)
        SETR -> performSetRegister(instruction, registers)

        BANI -> performBitwiseAndImmediate(instruction, registers)
        BANR -> performBitwiseAndRegister(instruction, registers)

        BORI -> performBitwiseOrImmediate(instruction, registers)
        BORR -> performBitwiseOrRegister(instruction, registers)

        GTIR -> performGreaterThanImmediateRegister(instruction, registers)
        GRRI -> performGreaterThanRegisterImmediate(instruction, registers)
        GTRR -> performGreaterThanRegisterRegister(instruction, registers)

        EQIR -> performEqualImmediateRegister(instruction, registers)
        EQRI -> performEqualRegisterImmediate(instruction, registers)
        EQRR -> performEqualRegisterRegister(instruction, registers)



        NOOP -> registers
    }
}



internal fun performAddImmediate(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    registers[arguments[2]] = registers[arguments[0]] + arguments[1]
    return registers
}

internal fun performAddRegisters(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    registers[arguments[2]] = registers[arguments[0]] + registers[arguments[1]]
    return registers
}



internal fun performMultiplyRegisters(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    registers[arguments[2]] = registers[arguments[0]] * registers[arguments[1]]
    return registers
}

internal fun performMultiplyImmediate(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    registers[arguments[2]] = registers[arguments[0]] * arguments[1]
    return registers
}



internal fun performBitwiseAndRegister(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    registers[arguments[2]] = registers[arguments[0]] and registers[arguments[1]]
    return registers
}

internal fun performBitwiseAndImmediate(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    registers[arguments[2]] = registers[arguments[0]] and arguments[1]
    return registers
}



internal fun performBitwiseOrRegister(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    registers[arguments[2]] = registers[arguments[0]] or registers[arguments[1]]
    return registers
}

internal fun performBitwiseOrImmediate(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    registers[arguments[2]] = registers[arguments[0]] or arguments[1]
    return registers
}



internal fun performSetRegister(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    registers[arguments[2]] = registers[arguments[0]]
    return registers
}

internal fun performSetImmediate(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    registers[arguments[2]] = arguments[0]
    return registers
}



internal fun performGreaterThanImmediateRegister(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    if (arguments[0] > registers[arguments[1]]) {
        registers[arguments[2]] = 1
    } else {
        registers[arguments[2]] = 0

    }
    return registers
}

internal fun performGreaterThanRegisterImmediate(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    if (registers[arguments[0]] > arguments[1]) {
        registers[arguments[2]] = 1
    } else {
        registers[arguments[2]] = 0

    }
    return registers
}


internal fun performGreaterThanRegisterRegister(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    if (registers[arguments[0]] > registers[arguments[1]]) {
        registers[arguments[2]] = 1
    } else {
        registers[arguments[2]] = 0

    }
    return registers
}




internal fun performEqualImmediateRegister(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    if (arguments[0] == registers[arguments[1]]) {
        registers[arguments[2]] = 1
    } else {
        registers[arguments[2]] = 0

    }
    return registers

}
internal fun performEqualRegisterImmediate(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    if (registers[arguments[0]] == arguments[1]) {
        registers[arguments[2]] = 1
    } else {
        registers[arguments[2]] = 0

    }
    return registers
}


internal fun performEqualRegisterRegister(instruction: Instruction, before: IntArray): IntArray {
    val registers = before.clone()
    val arguments = instruction.arguments
    if (registers[arguments[0]] == registers[arguments[1]]) {
        registers[arguments[2]] = 1
    } else {
        registers[arguments[2]] = 0

    }
    return registers
}

