package y2020.d09

import arrow.core.Option
import arrow.core.Option.Companion.empty
import arrow.core.Option.Companion.just
import util.collections.toDeque
import util.input.parseInputToInts
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

    public fun asSortedDeque() = ArrayDeque(queue).sorted().toDeque()
}



internal tailrec fun findTwoNumbersThatSumToTarget(expenses: ArrayDeque<Long>, target: Long): Option<Pair<Long, Long>> {
    val low = expenses.first()
    val high = expenses.last()

    val sum = low + high
    when {
        sum == target -> return just(Pair(low, high))
        sum < target -> expenses.removeFirst()
        sum >= target -> expenses.removeLast()
    }

    if (expenses.isEmpty()) {
        return empty()
    }

    return findTwoNumbersThatSumToTarget(expenses, target)
}
