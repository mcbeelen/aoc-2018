package y2018.day19_chrono_go_with_the_flow

import kotlin.system.measureTimeMillis

const val factor = 10551282

class ProgramInKotlin {


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val time = measureTimeMillis {
                println((1..factor).filter { factor.rem(it) == 0 }.sum())
            }

            println("Solved part two in ${time}ms")

        }
    }


}