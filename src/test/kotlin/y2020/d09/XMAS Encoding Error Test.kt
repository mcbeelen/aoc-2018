package y2020.d09

import arrow.core.getOrElse
import arrow.core.toOption
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.Long.Companion.MIN_VALUE

class `XMAS Encoding Error Test` {

    @Test
    fun examplePartOne() {
        assertThat(findFirstNumberWhichIsntSumOfPreviousNumbers(testInput, 5), equalTo(127))
    }

    @Test
    fun partOne() {
        assertThat(findFirstNumberWhichIsntSumOfPreviousNumbers(y2020d09input), equalTo(32321523))
    }

    @Test
    fun examplePartTwo() {
        val listOfContiguousNumbers = findContiguousSetWhichSumEqualsTo(testInput, 127)
        val min = listOfContiguousNumbers.minOrNull().toOption()
        val max = listOfContiguousNumbers.maxOrNull().toOption()

        assertTrue(min.isDefined());
        assertTrue(max.isDefined());

        assertThat(min.getOrElse { MIN_VALUE }, equalTo(15))
        assertThat(max.getOrElse { MIN_VALUE }, equalTo(47))

    }

    @Test
    fun partTwo() {
        val listOfContiguousNumbers = findContiguousSetWhichSumEqualsTo(y2020d09input, 32321523)

        val sorted = listOfContiguousNumbers.sorted()
        val min = sorted.first()
        val max = sorted.last()

        assertThat(min + max, equalTo(4794981))



    }
}


const val testInput = """35
20
15
25
47
40
62
55
65
95
102
117
150
182
127
219
299
277
309
576
"""
