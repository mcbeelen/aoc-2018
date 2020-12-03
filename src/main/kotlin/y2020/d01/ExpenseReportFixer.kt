package y2020.d01

import util.collections.toDeque
import util.input.parseInputToInts
import java.util.*
import kotlin.collections.ArrayDeque

fun main() {
    val (first, last) = findTwoExpensesThatSumTo2020(y2020d1)
    println("${first} * ${last} = ${first * last}")

    val (low, middle, high) = findThreeExpensesThatSumTo2020(y2020d1)
    println("${low} * ${middle} * ${high} = ${low * middle * high}")

}

internal fun findTwoExpensesThatSumTo2020(input: String): Pair<Int, Int> {
    val expenses = parseInputToInts(input).sorted().toDeque()
    return findTwoExpensesThatSumTo2020(expenses)
}

internal fun findThreeExpensesThatSumTo2020(input: String): Triple<Int, Int, Int> {
    val expenses = parseInputToInts(input).sorted()
    return findThreeExpensesThatSumTo2020(expenses)
}

fun findThreeExpensesThatSumTo2020(expenses: List<Int>): Triple<Int, Int, Int> {
    for (low in expenses.dropLast(2)) {
        for (middle in expenses.drop(1).dropLast(1)) {
            for (high in expenses.drop(2)) {
                if (low + middle + high == 2020) {
                    return Triple(low, middle, high)
                }
            }
        }
    }
    throw IllegalArgumentException("Input does not contain valid combination of three expenses that sum 2020")
}

internal tailrec fun findTwoExpensesThatSumTo2020(expenses: ArrayDeque<Int>): Pair<Int, Int> {
    val low = expenses.first();
    val high = expenses.last();

    val sum = low + high
    when {
        sum == 2020 -> return Pair(low, high)
        sum < 2020 -> expenses.removeFirst()
        sum >= 2020 -> expenses.removeLast()
    }

    return findTwoExpensesThatSumTo2020(expenses)
}


