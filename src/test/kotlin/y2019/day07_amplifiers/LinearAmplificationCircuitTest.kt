package y2019.day07_amplifiers

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import y2019.computer.compile

class LinearAmplificationCircuitTest {

    @Test
    fun exampleOne() {
        val program = "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"
        assertThat(findMaxThrustSignalInLinearConfiguration(compile(program)), equalTo(43210L))
    }

    @Test
    fun simulateOnePermutation() {
        val program = "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"
        val thrustSignal = calculateThrustSignalInLinearConfiguration(compile(program), listOf(4, 2, 3, 1, 0))
        assertThat(thrustSignal, equalTo(42310L))
    }

    @Test
    fun exampleTwo() {
        val program = "3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0"
        assertThat(findMaxThrustSignalInLinearConfiguration(compile(program)), equalTo(54321L))
    }

    @Test
    fun exampleThree() {
        val program = "3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33,1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0"
        assertThat(findMaxThrustSignalInLinearConfiguration(compile(program)), equalTo(65210L))
    }

    @Test
    fun verifyPartOne() {
        val program = AMPLIFIER_CONTROLLER_SOFTWARE
        assertThat(findMaxThrustSignalInLinearConfiguration(compile(program)), equalTo(70597L))

    }
}