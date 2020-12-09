package y2020.d09

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class `XMAS Encoding Error Test` {

    @Test
    fun examplePartOne() {
        assertThat(findFirstNumberWhichIsntSumOfPreviousNumbers(testInput, 5), equalTo(127))
    }

    @Test
    fun partOne() {
        assertThat(findFirstNumberWhichIsntSumOfPreviousNumbers(y2020d09input), equalTo(32321523))
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
