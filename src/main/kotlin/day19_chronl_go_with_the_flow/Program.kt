package day19_chronl_go_with_the_flow

import day16_chronal_classification.Instruction
import day16_chronal_classification.performOperation

data class Program(val boundRegister: Int, val instructions: List<Instruction>)

const val debug = false

class Executable(val program: Program) {
    var registers = intArrayOf(0, 0, 0, 0, 0, 0)

    var instuctionPointer : Int = 0

    private fun execute() {

        while (instuctionPointer in 0 .. program.instructions.size - 1) {
            executeInstruction()
        }

    }

    private fun executeInstruction() {
        if (debug) print("    ip: ${instuctionPointer}    [${registers.joinToString()}]")

        registers[program.boundRegister] = instuctionPointer

        if (debug) print(" ${program.instructions[instuctionPointer].opCode} ")

        registers = performOperation(program.instructions[instuctionPointer], registers)
        instuctionPointer = registers[program.boundRegister]

        if (debug) println("  -->  [${registers.joinToString()}]")

        instuctionPointer++
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val program = parseProgram(ACTUAL_INSTRUCTIONS) //EXAMPLE_INSTRUCTIONS
            val execution = Executable(program)

            execution.execute()

            println(execution.registers[0])

        }
    }



}


