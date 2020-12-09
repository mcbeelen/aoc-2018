package y2020.d09

import arrow.core.Option
import arrow.core.Option.Companion.empty
import arrow.core.Option.Companion.just
import util.collections.toDeque
import util.input.parseInputToLongs


fun findFirstNumberWhichIsntSumOfPreviousNumbers(input: String, preamble: Int = 25): Long {
    val seriesOfNumbers = parseInputToLongs(input).toList()
    val viewOnPreviousNumbers = ViewOnPreviousNumbers(preamble)
    for (i in 0 .. seriesOfNumbers.size) {
        val nextNumber = seriesOfNumbers[i]
        if (i < preamble) {
            viewOnPreviousNumbers.push(nextNumber)
        } else {
            val previousNumbers = viewOnPreviousNumbers.asSortedDeque()
            val twoNumbersThatSumToTarget = findTwoNumbersThatSumToTarget(previousNumbers, nextNumber)
            if (twoNumbersThatSumToTarget.isEmpty()) {
                return nextNumber
            } else {
                viewOnPreviousNumbers.push(nextNumber)
            }
        }
    }
    throw IllegalArgumentException("No error found")
}

class ViewOnPreviousNumbers(val preamble: Int) {
    fun push(nextNumber: Long) {
        queue.add(nextNumber)
        if (queue.size > preamble) {
            queue.removeFirst()
        }
    }

    private val queue = ArrayDeque<Long>()

    fun asSortedDeque() = ArrayDeque(queue).sorted().toDeque()
}

internal tailrec fun findTwoNumbersThatSumToTarget(availableValues: ArrayDeque<Long>, target: Long): Option<Pair<Long, Long>> {
    val low = availableValues.first()
    val high = availableValues.last()

    val sum = low + high
    when {
        sum == target -> return just(Pair(low, high))
        sum < target -> availableValues.removeFirst()
        sum >= target -> availableValues.removeLast()
    }

    if (availableValues.isEmpty()) {
        return empty()
    }

    return findTwoNumbersThatSumToTarget(availableValues, target)
}

fun findContiguousSetWhichSumEqualsTo(input: String, target: Long): List<Long> {
    val seriesOfNumbers = parseInputToLongs(input).toList()
    val maxIndexOfHighestValuePartOfTheContiguousSet = seriesOfNumbers.indexOf(target) - 1

    for (start in 0 .. maxIndexOfHighestValuePartOfTheContiguousSet) {
        for (end in start + 1 .. maxIndexOfHighestValuePartOfTheContiguousSet) {
            val contiguousSerie = seriesOfNumbers.subList(start, end)
            val sum = contiguousSerie.sum()
            if (sum == target) {
                return contiguousSerie
            }
        }
    }
    return emptyList()
}
