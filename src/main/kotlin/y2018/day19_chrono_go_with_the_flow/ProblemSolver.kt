package y2018.day19_chrono_go_with_the_flow

import kotlin.system.measureTimeMillis

class ProblemSolver {


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