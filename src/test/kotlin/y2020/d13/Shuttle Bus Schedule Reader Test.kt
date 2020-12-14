package y2020.d13

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Ignore
import org.junit.Test

class `Shuttle Bus Schedule Reader Test` {

    @Test
    fun examplePartOne() {
        assertThat(findFirstDepartingBusAndWaittime(testInput), equalTo(295))
    }

    @Test
    fun partOne() {
        assertThat(findFirstDepartingBusAndWaittime(y2020d13input), equalTo(333))
    }

    @Test
    fun firstExampleForPartTwo() {

        assertThat(findFirstTimestampForConsecutiveDepartures("17,x,13,19"), equalTo(3417))
        assertThat(findFirstTimestampForConsecutiveDepartures("67,7,59,61"), equalTo(754018))
        assertThat(findFirstTimestampForConsecutiveDepartures("67,x,7,59,61"), equalTo(779210))
        assertThat(findFirstTimestampForConsecutiveDepartures("67,7,x,59,61"), equalTo(1261476))
        assertThat(findFirstTimestampForConsecutiveDepartures("1789,37,47,1889"), equalTo(1_202_161_486))

        assertThat(findFirstTimestampForConsecutiveDepartures(testInput.lines()[1]), equalTo(1_068_781L))
    }

    @Test
    fun partTwo() {
        assertThat(findFirstTimestampForConsecutiveDepartures(y2020d13input.lines()[1]), equalTo(690_123_192_779_524L))
    }

    @Test
    fun `combine primes with offset`() {
        val firstCombo  = foldCombination(Pair(3L, 0), Pair(5L, 1))
        assertThat(firstCombo.first, equalTo(15L))
        assertThat(firstCombo.second, equalTo(6))

        val secondCombo = foldCombination(firstCombo, Pair(7L, 2))
        assertThat(secondCombo.first, equalTo(105L))
        assertThat(secondCombo.second, equalTo(51))
    }


}


const val testInput = """939
7,13,x,x,59,x,31,19"""
