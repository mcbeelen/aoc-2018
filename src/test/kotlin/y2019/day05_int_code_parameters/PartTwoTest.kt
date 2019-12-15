package y2019.day05_int_code_parameters

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import y2019.computer.ConstantInput
import y2019.computer.IntcodeComputer
import y2019.computer.LastPrintedReadableOutput
import y2019.computer.compile

class PartTwoTest  {

    @Test
    fun equalsToEight() {
        validateIntcodeComputer("3,9,8,9,10,9,4,9,99,-1,8", 8, 1)
        validateIntcodeComputer("3,3,1108,-1,8,3,4,3,99", 8, 1)
    }

    @Test
    fun notEqualsToEight() {
        validateIntcodeComputer("3,9,8,9,10,9,4,9,99,-1,8", 7, 0)
    }

    @Test
    fun lessThanEight() {
        validateIntcodeComputer("3,9,7,9,10,9,4,9,99,-1,8", 7, 1)
        validateIntcodeComputer("3,9,7,9,10,9,4,9,99,-1,8", 8, 0)
        validateIntcodeComputer("3,3,1107,-1,8,3,4,3,99", 7, 1)
        validateIntcodeComputer("3,3,1107,-1,8,3,4,3,99", 8, 0)
    }


    @Test
    fun inputIsNotZero() {
        validateIntcodeComputer("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", 0, 0)
        validateIntcodeComputer("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9", 9, 1)
        validateIntcodeComputer("3,3,1105,-1,9,1101,0,0,12,4,12,99,1", 0, 0)
        validateIntcodeComputer("3,3,1105,-1,9,1101,0,0,12,4,12,99,1", 9, 1)
    }


    @Test
    fun validateLargeExample() {
        validateIntcodeComputer(LARGE_EXAMPLE, 7, 999)
        validateIntcodeComputer(LARGE_EXAMPLE, 8, 1000)
        validateIntcodeComputer(LARGE_EXAMPLE, 11, 1001)
    }

    private val LARGE_EXAMPLE = "3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"

    private fun validateIntcodeComputer(intCode: String, value: Int, expected: Long) {
        val program = compile(intCode)
        val input = ConstantInput(value)
        val output = LastPrintedReadableOutput()
        val simulator = IntcodeComputer(
                input = input,
                output = output,
                byteCode = program)

        while (!simulator.isProgramFinished()) {
            simulator.tick()
        }

        assertThat(output.lastPrintedValue, equalTo(expected))
    }


}