package y2020.d13

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import kotlin.Int.Companion.MIN_VALUE

class `Shuttle Bus Schedule Reader Test` {

    @Test
    fun examplePartOne() {
        assertThat(solveIt(testInput), equalTo(295))
    }

    @Test
    fun partOne() {
        assertThat(solveIt(y2020d13input), equalTo(333))
    }
}


const val testInput = """939
7,13,x,x,59,x,31,19"""
