package y2020.d16

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.greaterThan
import com.natpryce.hamkrest.hasSize
import com.natpryce.hamkrest.lessThan
import org.junit.Test

class `Ticket Translation Test` {

    @Test
    fun examplePartOne() {
        assertThat(`count ticket scanning error rate`(testRules, testNearbyTickets), equalTo(71))
    }

    @Test
    fun partOne() {
        assertThat(`count ticket scanning error rate`(y2020d16_rules, y2020d16_nearbyTickets), equalTo(26941))
    }

    @Test
    fun `it should discard invalid tickets`() {
        val rules = parseRules(testRules)

        val tickets = parseNearbyTickets(testNearbyTickets)

        val validTickets = tickets.filter { it.isValid(rules) }
        assertThat(validTickets, hasSize(equalTo(1)))
    }

    /**
    departure time : 1
    departure location: 13
    departure track: 5
    departure platform: 7
    departure date: 15
    departure station: 19
     */
    @Test
    fun partTwo() {



    }
}


private const val testRules = """class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50"""

private const val testTicket = "7,1,14"

private const val testNearbyTickets = """7,3,47
40,4,50
55,2,20
38,6,12"""
