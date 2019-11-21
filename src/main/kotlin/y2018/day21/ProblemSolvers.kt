package y2018.day21

import com.google.common.base.Stopwatch
import y2018.day19_chrono_go_with_the_flow.Debugger
import y2018.day19_chrono_go_with_the_flow.Executable
import y2018.day19_chrono_go_with_the_flow.Program
import y2018.day19_chrono_go_with_the_flow.parseProgram
import java.util.concurrent.TimeUnit.MILLISECONDS

class PartOneSolver : Debugger {

    val stopwatch = Stopwatch.createStarted()

    override fun debug(program: Program, instuctionPointer: Int, registers: IntArray) {
        val calculatedValue = registers[4]
        println("Found a halting integer. Register [0] should be set to: ${calculatedValue}")
        println("Found the answer after ${stopwatch.elapsed(MILLISECONDS)}ms")
        System.exit(0)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val program = parseProgram(ACTIVATION_SYSTEM_SOURCE_CODE) //EXAMPLE_INSTRUCTIONS
            val execution = Executable(program, mapOf(Pair(28, PartOneSolver())))

            execution.execute()

        }
    }


}


class PartTwoSolver : Debugger {

    val haltingIntegers : MutableList<Int> = ArrayList()

    val stopwatch = Stopwatch.createStarted()

    override fun debug(program: Program, instuctionPointer: Int, registers: IntArray) {
        val calculatedValue = registers[4]
        if (haltingIntegers.contains(calculatedValue)) {
            println("Found a duplicate: ${calculatedValue}")
            println("Last unique found value is ${haltingIntegers.last()}")

            println("Found the answer after ${stopwatch.elapsed(MILLISECONDS)}ms")

            System.exit(9)
        } else {
            haltingIntegers.add(calculatedValue)
        }

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val program = parseProgram(ACTIVATION_SYSTEM_SOURCE_CODE) //EXAMPLE_INSTRUCTIONS
            val execution = Executable(program, mapOf(Pair(28, PartTwoSolver())))

            execution.execute()

        }
    }

}
