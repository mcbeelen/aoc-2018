package day19_chrono_go_with_the_flow

import day16_chronal_classification.Instruction
import day16_chronal_classification.performOperation
import kotlin.system.measureTimeMillis

data class Program(val boundRegister: Int, val instructions: List<Instruction>)

class Executable(val program: Program) {

    var registers = intArrayOf(0, 0, 0, 0, 0, 0)

    var instuctionPointer : Int = 0

    private fun execute() {
        while (instuctionPointer in 0 .. program.instructions.size - 1) {
            executeInstruction()
        }
    }

    private fun executeInstruction() {

        registers[program.boundRegister] = instuctionPointer
        registers = performOperation(program.instructions[instuctionPointer], registers)
        instuctionPointer = registers[program.boundRegister]

        instuctionPointer++

    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val time = measureTimeMillis {
                val program = parseProgram(ACTUAL_INSTRUCTIONS) //EXAMPLE_INSTRUCTIONS
                val execution = Executable(program)

                execution.execute()

                println(execution.registers[0])

            }

            println("Solved it in ${time}ms")

        }
    }



}


