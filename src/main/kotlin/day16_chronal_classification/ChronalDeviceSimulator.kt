package day16_chronal_classification

import day16_chronal_classification.OpCode.*

class ChronalDeviceSimulator {

    var registers = intArrayOf(0, 0, 0, 0)


    fun executeInstruction(instruction: Instruction, registers: IntArray) : IntArray {
        return performOperation(instruction, registers)
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