package y2019.day07_amplifiers

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import y2019.day07_amplifiers.AMPLIFIER_CONTROLLER_SOFTWARE
import y2019.day07_amplifiers.findMaxThrusterSignalInCircularConfiguration

class CircularAmplificationCircuitTest {



    @Test
    fun exampleOne() {

        val sourceCode = "3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26,27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5"
        assertThat(findMaxThrusterSignalInCircularConfiguration(sourceCode), equalTo(139629729L))
    }


    @Test
    fun exampleTwo() {
        val sourceCode = "3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54,-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4,53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10"
        assertThat(findMaxThrusterSignalInCircularConfiguration(sourceCode), equalTo(18216L))

    }

    @Test
    fun actualPartTwo() {
        val sourceCode = AMPLIFIER_CONTROLLER_SOFTWARE
        assertThat(findMaxThrusterSignalInCircularConfiguration(sourceCode), equalTo(30872528L)) // 30872528
    }



}