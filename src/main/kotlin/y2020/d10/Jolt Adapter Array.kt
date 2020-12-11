package y2020.d10

import util.input.parseInputToInts
import java.util.*
import kotlin.collections.ArrayList


fun multiplcationOfFrequencyOfOnesAndThrees(input: String): Int {
    val sortedJolts = parseInputToInts(input).sorted()
    val deltasBetweenJolts = mapToDeltas(sortedJolts)

    val frequencyOfOne = Collections.frequency(deltasBetweenJolts, 1)
    val frequencyOfThree = Collections.frequency(deltasBetweenJolts, 3)

    return frequencyOfOne * frequencyOfThree

}

private fun mapToDeltas(sortedJolts: List<Int>): List<Int> {
    val deltasBetweenJolts = sortedJolts.mapIndexed { index: Int, i: Int ->
        if (index > 0) {
            i - sortedJolts[index - 1]
        } else {
            i
        }
    }.toMutableList()
    deltasBetweenJolts.add(3)
    return deltasBetweenJolts
}

fun countDistinctWaysToArrangeAdapters(input: String): Long {
    val sortedJolts = parseInputToInts(input).sorted()
    val deltasBetweenJolts = mapToDeltas(sortedJolts)

    return mapToLengthOfSectionsBetweenThrees(deltasBetweenJolts)
        .map { numberOfPossiblePermutationsPerSection(it) }
        .multiply()
}

private fun List<Long>.multiply() = this.reduce { acc, d -> acc * d }


fun numberOfPossiblePermutationsPerSection(lenght: Int) : Long = when(lenght) {
    0 -> 1L
    1 -> 1L
    2 -> 2L
    3 -> 4L
    4 -> 7L
    else -> lenght - 1 + numberOfPossiblePermutationsPerSection(lenght - 1)
}

private fun mapToLengthOfSectionsBetweenThrees(deltasBetweenJolts: List<Int>): List<Int> {
    val lengthOfSections = ArrayList<Int>().toMutableList()

    var lengthCounter = 0
    deltasBetweenJolts.forEach {
        if (it == 3) {
            lengthOfSections.add(lengthCounter)
            lengthCounter = 0
        } else {
            lengthCounter++
        }
    }
    lengthOfSections.add(lengthCounter)
    return lengthOfSections
}

fun main() {
    println(multiplcationOfFrequencyOfOnesAndThrees(y2020d10input))
    println(countDistinctWaysToArrangeAdapters(y2020d10input))
}
