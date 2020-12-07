package y2020.d06

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class SumOfCountTest {

    @Test
    fun examplePartOne() {
        assertThat(sumOfCountAnyAnswers(y2020d06testInput), equalTo(11))
    }

    @Test
    fun examplePartTwo() {
        assertThat(sumOfCountAllAnswers(y2020d06testInput), equalTo(6))
    }

    @Test
    fun partOne() {
        assertThat(sumOfCountAnyAnswers(y2020d06input), equalTo(6742))
    }

    @Test
    fun partTwo() {
        assertThat(sumOfCountAllAnswers(y2020d06input), equalTo(3447))
    }
}


val y2020d06testInput = """abc

a
b
c

ab
ac

a
a
a
a

b"""
