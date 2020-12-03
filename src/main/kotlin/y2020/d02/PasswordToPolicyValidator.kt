package y2020.d02

import util.input.parseInput

fun main() {
    val inputs = parseInput(Year2020Day02Input) { Pair(it.substringBefore(":"), it.substringAfter(":")) }.toList()

    println("Part ONE: " + inputs.filter { isValidPartOne(it.first, it.second) }.count())
    println("Part TWO: " + inputs.filter { isValidPartTwo(it.first, it.second) }.count())
}

fun isValidPartOne(policy: String, password: String): Boolean {
    val requiredChar = policy.takeLast(1)[0]
    val frequency = password.filter { it == requiredChar }.count()
    val lowerLimit = lowerLimit(policy)
    val upperLimit = upperLimit(policy)
    return lowerLimit <= frequency && upperLimit >= frequency
}

fun isValidPartTwo(policy: String, password: String): Boolean {
    val trimmedPassword = password.trim()
    val requiredChar = policy.takeLast(1)[0]
    val firstIndex = lowerLimit(policy)
    val secondIndex = upperLimit(policy)
    return (trimmedPassword[firstIndex - 1] == requiredChar) xor (trimmedPassword[secondIndex - 1] == requiredChar)

}


private fun lowerLimit(policy: String) = policy.substringBefore("-").toInt()
private fun upperLimit(policy: String) = policy.substringAfter("-").dropLast(2).toInt()
