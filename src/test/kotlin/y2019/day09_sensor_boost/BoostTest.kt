package y2019.day09_sensor_boost

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import y2019.computer.Buffer
import y2019.computer.ConstantInput
import y2019.computer.IntcodeComputer
import y2019.computer.Value



fun bigInt(int: Int) = int.toLong()

class BoostTest {


    @Test
    fun firstExample() {
        val sourceCode = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"
        val buffer = inspect(sourceCode)
        assertThat(buffer.read(), equalTo(109L))
    }

    @Test
    fun secondExample() {
        val sourceCode = "1102,34915192,34915192,7,4,7,99,0"
        val read = simulate(sourceCode)
        assertThat(read.toString().length, equalTo(16))
    }


    @Test
    fun `program with relative write`() {
        val buffer = Buffer()
        buffer.write(bigInt(654_987))
        val intcodeComputer = IntcodeComputer(input = buffer,
                output = buffer,
                sourceCode = "109,10,203,-1,4,9,99")

        intcodeComputer.runProgram()

        assertThat(buffer.read(), equalTo(bigInt(654_987)))
    }


    @Test
    fun partOne() {
        val buffer = Buffer()
        val computer = IntcodeComputer(sourceCode = BOOST_PROGRAM, input = ConstantInput(1), output = buffer)

        computer.runProgram()

        val actual = buffer.read()
        assertThat(actual, equalTo(3_512_778_005L))
    }

    @Test
    fun partTwo() {
        val buffer = Buffer()
        val computer = IntcodeComputer(sourceCode = BOOST_PROGRAM, input = ConstantInput(2), output = buffer)

        computer.runProgram()

        val actual = buffer.read()
        assertThat(actual, equalTo(bigInt(35_920)))
    }



    private fun simulate(sourceCode: String): Value? {
        return inspect(sourceCode).read()
    }

    private fun inspect(sourceCode: String): Buffer {
        val output = Buffer()
        val intcodeComputer = IntcodeComputer(sourceCode = sourceCode, output = output)

        intcodeComputer.runProgram()
        return output
    }
}