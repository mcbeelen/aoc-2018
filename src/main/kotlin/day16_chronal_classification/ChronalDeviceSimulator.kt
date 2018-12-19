package day16_chronal_classification

import day16_chronal_classification.OpCode.*

class ChronalDeviceSimulator {

    var registers = intArrayOf(0, 0, 0, 0)


    fun executeInstruction(instruction: Instruction, registers: IntArray) : IntArray {
        return performOperation(instruction, registers)
    }

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


    private fun runSimulation() {

        CHRONAL_DEVICE_INSTRUCTIONS.trimIndent().lines()
                .map { parseInstruction(it) }
                .forEach { registers = executeInstruction(it, registers) }


        println(registers.joinToString())

    }





    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            val simulator = ChronalDeviceSimulator()

            simulator.runSimulation()


        }

    }


}