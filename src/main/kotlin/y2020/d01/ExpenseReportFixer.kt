package y2020.d01

import util.collections.toDeque

fun main() {
    val combi = findTwoExpensesThatSumTo2020(y2020d1)
    println("${combi.first} * ${combi.second} = ${combi.first * combi.second}")

}

internal fun findTwoExpensesThatSumTo2020(input: String): Pair<Int, Int> {
    val expenses = parseInputToInts(input).sorted().toDeque()
    return findTwoExpensesThatSumTo2020(expenses)
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

fun <T> parseInput(input: String, lineMapping:  (line: String) -> T) : Iterable<T> {
    return input.trimIndent().lines().map(lineMapping)
}
fun parseInputToInts(input: String) = parseInput(input){ it.toInt() }
