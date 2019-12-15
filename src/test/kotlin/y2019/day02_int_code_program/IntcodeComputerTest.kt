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

        val simulator = IntcodeComputer(intCode)

        simulator.tick()

        assertThat(simulator.state.readFromMemory(3), equalTo(70L))
        assertThat(simulator.state.instructionPointer, equalTo(4))
    }

    @Test
    fun `simpulateOpsCode2 multiply`() {
        val intCode = "1,9,10,70,2,3,11,0,99,30,40,50"
        val simulator = IntcodeComputer(intCode, 4)

        simulator.tick()

        assertThat(simulator.state.readFromMemory(0), equalTo(3500L))
        assertThat(simulator.state.instructionPointer, equalTo(8))
    }


    @Test
    fun `cursor points to 99 means program is finished`() {
        val sourceCode = "1,9,10,70,2,3,11,0,99,30,40,50"

        assertFalse(IntcodeComputer(sourceCode, 0).isProgramFinished())
        assertFalse(IntcodeComputer(sourceCode, 4).isProgramFinished())
        val intcodeComputer = IntcodeComputer(sourceCode, 8)
        intcodeComputer.tick()
        assertTrue(intcodeComputer.isProgramFinished())

    }
}