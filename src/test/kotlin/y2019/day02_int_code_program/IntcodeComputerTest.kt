package y2019.day02_int_code_program

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import y2019.computer.IntcodeComputer

class IntcodeComputerTest {
    @Test
    fun simulateOpsCode1() {
        val intCode = "1,9,10,3,2,3,11,0,99,30,40,50"

        var simulator = IntcodeComputer(intCode)

        simulator = simulator.tick()

        assertThat(simulator.program[3], equalTo(70))
        assertThat(simulator.instructionPointer, equalTo(4))
    }

    @Test
    fun `simpulateOpsCode2 multiply`() {
        val intCode = "1,9,10,70,2,3,11,0,99,30,40,50"
        var simulator = IntcodeComputer(intCode, 4)

        simulator = simulator.tick()

        assertThat(simulator.program[0], equalTo(3500))
        assertThat(simulator.instructionPointer, equalTo(8))
    }


    @Test
    fun `cursor points to 99 means program is finished`() {
        val intCode = "1,9,10,70,2,3,11,0,99,30,40,50"

        assertFalse(IntcodeComputer(intCode, 0).isProgramFinished())
        assertFalse(IntcodeComputer(intCode, 4).isProgramFinished())
        assertTrue(IntcodeComputer(intCode, 8).isProgramFinished())

    }
}