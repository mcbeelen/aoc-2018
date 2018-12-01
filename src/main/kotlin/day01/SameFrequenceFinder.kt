package day01

import kotlin.system.measureTimeMillis

class SameFrequenceFinder {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val modulations = DAY_1_INPUT.trimIndent().lines().map { it.toInt() }.toList()

            val timeElapsed = measureTimeMillis {
                println("First frequence seen twice: " + findFirstRecurringFrequency(modulations))

            }
            println("  Solved it in: ${timeElapsed}ms")


        }
    }
}


fun findFirstRecurringFrequency(input: List<Int>): Int {

    var currentFrequency = 0
    val frequencySet: MutableSet<Int> = HashSet()

    circularSequence(input)
            .takeWhile { !frequencySet.contains(currentFrequency) }
            .forEach {
                frequencySet.add(currentFrequency)
                currentFrequency += it
            }

    return currentFrequency

}

private fun circularSequence(input: List<Int>): Sequence<Int> {
    return sequence {
        var index = 0

        while (true) {
            yield(input[index])
            index++
            if (index == input.size) {
                index = 0
            }
        }

    }
}



