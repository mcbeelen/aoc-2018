package y2019.day09_sensor_boost

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import y2019.computer.Buffer
import y2019.computer.IntcodeComputer

class ParameterModeRelativeTest {

    @Test
    fun `it should support relative mode`() {

        verifyRelativeBaseWithShift("204,4,99,0,1", 1)

    }

    @Test
    fun `Opcode 9 should change the relativeBase`() {
        verifyRelativeBaseWithShift("9,1,204,4,99,1", 1)
        verifyRelativeBaseWithShift("109,7,204,3,99,5,6,7,8,9,10,11", 10)

    }

    private fun verifyRelativeBaseWithShift(sourceCode: String, expected: Int) {
        val output = Buffer()
        val boardComputer = IntcodeComputer(sourceCode = sourceCode, output = output)
        boardComputer.runProgram()

        assertThat(output.read(), equalTo(expected.toLong()))
    }
}