package y2020.d16

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.ListMultimap
import com.google.common.collect.Multimap


fun `count ticket scanning error rate`(ruleDefinition: String, nearbyTickets: String): Int {
    val rules = parseRules(ruleDefinition)

    val tickets = parseNearbyTickets(nearbyTickets)

    val allFieldValues = tickets.flatMap { it.fieldValues }

    return allFieldValues
        .filter { isNotValidForAnyField(rules, it) }
        .sum()
}


internal fun parseNearbyTickets(nearbyTickets: String) = nearbyTickets.lines().map { Ticket(it) }


internal fun parseRules(ruleDefinition: String): List<Rule> {
    return ruleDefinition.lines()
        .map { toRule(it) }
}

private fun toRule(it: String): Rule {
    val fieldName = it.substringBefore(": ")
    val ruleDefinitions = it.substringAfter(": ")
    val ranges = ruleDefinitions.split(" or ").toList()

    val firstRange = ranges[0].toRange()
    val secondRange = ranges[1].toRange()
    return Rule(fieldName, firstRange, secondRange)

}

private fun isNotValidForAnyField(rules: List<Rule>, fieldValue: Int) = rules.all { !it.isValid(fieldValue) }


private fun String.toRange(): IntRange {
    val start = this.substringBefore("-").toInt()
    val inclusiveEnd = this.substringAfter("-").toInt()
    return start..inclusiveEnd
}

data class Rule(val fieldName: String, val firstRange: IntRange, val secondRange: IntRange) {
    fun isValid(fieldValue: Int) = firstRange.contains(fieldValue) || secondRange.contains(fieldValue)
}

data class Ticket(val fieldValues: List<Int>) {
    fun isValid(rules: List<Rule>) = fieldValues.all { value -> rules.any { rule: Rule -> rule.isValid(value) } }

    constructor(nearbyTicket: String) : this(toFieldValues(nearbyTicket))
}

private fun toFieldValues(nearbyTicket: String) = nearbyTicket.split(',')
    .map { it.toInt() }
    .toList()


fun main() {
    val rules = parseRules(y2020d16_rules)
    val valuesOnMyTicket = y2020d16_yourTicket.split(',').map { it.toLong() }.toList()
    val tickets = parseNearbyTickets(y2020d16_nearbyTickets)
    val validTickets = tickets.filter { it.isValid(rules) }

    val ruleNameWithCandidateColumns = buildCandidateMap(rules).toMutableMap()

    val indexPerRule = HashMap<String, Int>().toMutableMap()


    rules.forEach { rule ->
        if (! indexPerRule.containsKey(rule.fieldName)) {
            for (index in rules.indices) {
                if (!validTickets.all { rule.isValid(it.fieldValues[index]) }) {
                    ruleNameWithCandidateColumns[rule.fieldName] = ruleNameWithCandidateColumns.getValue(rule.fieldName).minus(index)
                }
            }
        }
    }

    while (indexPerRule.size < rules.size) {
        ruleNameWithCandidateColumns
            .filter { it.value.size == 1 }
            .forEach {
                println("${it.key} is at index ${it.value[0]}")
                indexPerRule[it.key] = it.value[0]
            }
        indexPerRule.forEach { fieldName, index ->
            ruleNameWithCandidateColumns.remove(fieldName)
            ruleNameWithCandidateColumns.keys.forEach {
                ruleNameWithCandidateColumns[it] = ruleNameWithCandidateColumns.getValue(it).minus(index)
            }
        }
    }

    val output = indexPerRule.filter { it.key.startsWith("departure") }
        .map { it.value }
        .map { valuesOnMyTicket[it] }
        .reduce { acc, i -> acc * i }

    println(output)


}

fun buildCandidateMap(rules: List<Rule>): Map<String, List<Int>> {

    val candidateMap = HashMap<String, List<Int>>().toMutableMap()
    rules.forEach {
        candidateMap.put(it.fieldName, listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19))
    }
    return candidateMap
}

fun allAvailableIndexes(rules: List<Rule>): List<Int> {
    return ArrayList((0 until rules.size).toMutableList())
}

