package y2019.day09_sensor_boost

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import y2019.computer.Buffer
import y2019.computer.IntcodeComputer

class ParameterModeRelativeTest {

    @Test
    fun `it should support relative mode`() {

        val sourceCode = "204,4,99,0,1"

        val output = Buffer()
        val boardComputer = IntcodeComputer(sourceCode = sourceCode, output = output)
        boardComputer.runProgram()

        assertThat(output.read(), equalTo(1))

    }
}