package y2019.day07_amplifiers

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import y2019.computer.parseIntCode

class AmplificationCircuitTest {

    @Test
    fun exampleOne() {
        val program = "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"
        assertThat(findMaxThrustSignal(parseIntCode(program)), equalTo(43210))
    }

    @Test
    fun simulateOnePermutation() {
        val program = "3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"
        val thrustSignal = calculateThrustSignal(parseIntCode(program), listOf(4, 2, 3, 1, 0))
        assertThat(thrustSignal, equalTo(42310))
    }



}