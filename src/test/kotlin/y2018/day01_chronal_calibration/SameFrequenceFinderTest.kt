package y2018.day01_chronal_calibration

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class SameFrequenceFinderTest {

    @Test
    fun firstExample() {
        val input = toListOfInt("+1, -1")
        assertThat(findFirstRecurringFrequency(input), equalTo(0))
    }

    @Test
    fun secondExample() {
        val input = toListOfInt("+3, +3, +4, -2, -4");
        assertThat(findFirstRecurringFrequency(input), equalTo(10))
    }

    @Test
    fun thirdExample() {
        val input = toListOfInt("-6, +3, +8, +5, -6")
        assertThat(findFirstRecurringFrequency(input), equalTo(5))
    }

    @Test
    fun fourthExample() {
        val input = toListOfInt("+7, +7, -2, -7, -4")
        assertThat(findFirstRecurringFrequency(input), equalTo(14))
    }

    private fun toListOfInt(inputString: String): List<Int> {
        val input = inputString.split(",")
                .map { it.trim() }
                .map { it.toInt() }
        return input
    }


}

