package y2019.day02_int_code_program

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class IntCodeSimulatorTest {
    @Test
    fun simulateOpsCode1() {
        val intCode = "1,9,10,3,2,3,11,0,99,30,40,50"

        var simulator = IntCodeSimulator(intCode)

        simulator = simulator.tick()

        assertThat(simulator.intCode[3], equalTo(70))

    }
}