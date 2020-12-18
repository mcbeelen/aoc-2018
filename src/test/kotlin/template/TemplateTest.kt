package template

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import kotlin.Int.Companion.MIN_VALUE

class TemplateTest {

    @Test
    fun examplePartOne() {
        assertThat(solveIt(testInput), equalTo(MIN_VALUE))
    }

    fun solveIt(testInput: String): Int {
        return MIN_VALUE
    }
}


const val testInput = """PASTE_HERE"""
