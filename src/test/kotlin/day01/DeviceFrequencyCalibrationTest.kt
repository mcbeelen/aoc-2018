package day01

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class DeviceFrequencyCalibrationTest {


    @Test
    fun firstDemo() {
        val input = """
        +1
        +1
        +1
        """.trimIndent()

        assertThat(calibate(input), equalTo(3))

    }

    @Test
    fun secondExample() {
        val input = """
        +1
        +1
        -2
        """.trimIndent()

        assertThat(calibate(input), equalTo(0))
    }
}