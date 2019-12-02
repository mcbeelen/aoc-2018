package y2019.day02_int_code_program

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class IntCodeSimulatorTest {
    @Test
    fun simulateOpsCode1() {
        val intCode = "1,9,10,3,2,3,11,0,99,30,40,50"

        var simulator = IntCodeSimulator(intCode)

        simulator = simulator.tick()

        assertThat(simulator.intCode[3], equalTo(70))
        assertThat(simulator.cursor, equalTo(4))
    }

    @Test
    fun `simpulateOpsCode2 multiply`() {
        val intCode = "1,9,10,70,2,3,11,0,99,30,40,50"
        var simulator = IntCodeSimulator(intCode, 4)

        simulator = simulator.tick()

        assertThat(simulator.intCode[0], equalTo(3500))
        assertThat(simulator.cursor, equalTo(8))
    }


    @Test
    fun `cursor points to 99 means program is finished`() {
        val intCode = "1,9,10,70,2,3,11,0,99,30,40,50"

        assertFalse(IntCodeSimulator(intCode, 0).isProgramFinished())
        assertFalse(IntCodeSimulator(intCode, 4).isProgramFinished())
        assertTrue(IntCodeSimulator(intCode, 8).isProgramFinished())

    }
}