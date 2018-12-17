package day16_chronal_classification

class ChronalDevice {
}


internal fun performAddImmediate(instruction: IntArray, before: IntArray): IntArray {
    val registers = before.clone()
    registers[instruction[3]] = registers[instruction[1]] + instruction[2]
    return registers
}

internal fun performAddRegisters(instruction: IntArray, before: IntArray): IntArray {
    val registers = before.clone()
    registers[instruction[3]] = registers[instruction[1]] + registers[instruction[2]]
    return registers
}



internal fun performMultiplyRegisters(instruction: IntArray, before: IntArray): IntArray {
    val registers = before.clone()
    registers[instruction[3]] = registers[instruction[1]] * registers[instruction[2]]
    return registers
}

internal fun performMultiplyImmediate(instruction: IntArray, before: IntArray): IntArray {
    val registers = before.clone()
    registers[instruction[3]] = registers[instruction[1]] * instruction[2]
    return registers
}

internal fun performBitwiseAndRegister(instruction: IntArray, before: IntArray): IntArray {
    val registers = before.clone()
    registers[instruction[3]] = registers[instruction[1]] and registers[instruction[2]]
    return registers
}

internal fun performBitwiseAndImmediate(instruction: IntArray, before: IntArray): IntArray {
    val registers = before.clone()
    registers[instruction[3]] = registers[instruction[1]] and instruction[2]
    return registers
}




internal fun performSetImmediate(instruction: IntArray, before: IntArray): IntArray {
    val registers = before.clone()
    registers[instruction[3]] = instruction[1]
    return registers
}



enum class OpCode {
    ADDR,
    ADDI,

    MULR,
    MULI,

    BANR,
    BANI,

    BORR,
    BORI,

    SETR,
    SETI,

    GTIR,
    GRRI,
    GTRR,

    EQIR,
    EQRI,
    EQRR
}