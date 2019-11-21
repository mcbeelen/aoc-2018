package y2018.day16_chronal_classification

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import y2018.day16_chronal_classification.OpCode.*
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.*

val sample = """
Before: [3, 2, 1, 1]
9 2 1 2
After:  [3, 2, 2, 1]"""


class ChronalDeviceTest {


    @Test
    fun itShouldProperlyParseProvidedSamples() {
        parseIntoSample(sample).let {
            assertTrue("BEFORE", Arrays.equals(it.before, intArrayOf(3, 2, 1, 1)))
            assertThat("INSTRUCTION", it.instruction.opCode, equalTo(EQRI))
            assertTrue("INSTRUCTION", Arrays.equals(it.instruction.arguments, intArrayOf(2, 1, 2)))
            assertTrue("AFTER", Arrays.equals(it.after, intArrayOf(3, 2, 2, 1)))
        }

        val chunked = CHRONAL_DEVICE_RECORDED_SAMPLES.trimIndent().lines().chunked(4)
        val samples = chunked
                .map { parseIntoSample(it) }


        assertThat(samples.size, equalTo(793))


    }



    @Test
    fun itShouldRecognizeExampleAsMURL() {

        val sample = parseIntoSample(sample)

        assertTrue(doesSampleBehaveLike(ADDI, sample))
        assertFalse(doesSampleBehaveLike(ADDR, sample))
        assertTrue(doesSampleBehaveLike(MULR, sample))
        assertTrue(doesSampleBehaveLike(SETI, sample))

    }



    // [3, 2, 1, 1] -> (9 2 1 2)
    @Test
    fun itShouldSupportAllOperations() {
        val sample = parseIntoSample(sample)
        assertTrue(validate(intArrayOf(3, 2, 1, 1), performMultiplyImmediate(sample.instruction, sample.before)))

        assertTrue(validate(intArrayOf(3, 2, 0, 1), performBitwiseAndRegister(sample.instruction, sample.before)))
        assertTrue(validate(intArrayOf(3, 2, 1, 1), performBitwiseAndImmediate(sample.instruction, sample.before)))

        assertTrue(validate(intArrayOf(3, 2, 3, 1), performBitwiseOrRegister(sample.instruction, sample.before)))
        assertTrue(validate(intArrayOf(3, 2, 1, 1), performBitwiseOrImmediate(sample.instruction, sample.before)))


        assertTrue(validate(3, 2, 1, 1, performSetRegister(sample.instruction, sample.before)))


        assertTrue(validate(4, 4, 1, 0, performGreaterThanImmediateRegister(decompileInstruction(intArrayOf(9, 1, 3, 2)), intArrayOf(4, 4, 4, 0))))
        assertTrue(validate(4, 4, 0, 0, performGreaterThanImmediateRegister(decompileInstruction(intArrayOf(9, 1, 2, 2)), intArrayOf(4, 4, 4, 0))))

        assertTrue(validate(1, 4, 4, 0, performGreaterThanRegisterImmediate(decompileInstruction(intArrayOf(9, 1, 2, 0)), intArrayOf(4, 4, 4, 0))))
        assertTrue(validate(0, 4, 4, 0, performGreaterThanRegisterImmediate(decompileInstruction(intArrayOf(9, 1, 9, 0)), intArrayOf(4, 4, 4, 0))))

        assertTrue(validate(1, 2, 3, 4, performGreaterThanRegisterRegister(decompileInstruction(intArrayOf(9, 2, 1, 0)), intArrayOf(9, 2, 3, 4))))
        assertTrue(validate(0, 2, 3, 4, performGreaterThanRegisterRegister(decompileInstruction(intArrayOf(9, 1, 3, 0)), intArrayOf(9, 2, 3, 4))))


        assertTrue(validate(1, 2, 3, 4, performEqualImmediateRegister(decompileInstruction(intArrayOf(9, 2, 1, 0)), intArrayOf(9, 2, 3, 4))))
        assertTrue(validate(0, 2, 3, 4, performEqualImmediateRegister(decompileInstruction(intArrayOf(9, 1, 3, 0)), intArrayOf(9, 2, 3, 4))))


        assertTrue(validate(1, 2, 3, 4, performEqualRegisterImmediate(decompileInstruction(intArrayOf(9, 2, 3, 0)), intArrayOf(9, 2, 3, 4))))
        assertTrue(validate(0, 2, 3, 4, performEqualRegisterImmediate(decompileInstruction(intArrayOf(9, 2, 9, 0)), intArrayOf(9, 2, 3, 4))))


        assertTrue(validate(1, 2, 4, 4, performEqualRegisterRegister(decompileInstruction(intArrayOf(9, 2, 3, 0)), intArrayOf(9, 2, 4, 4))))
        assertTrue(validate(0, 2, 3, 4, performEqualRegisterRegister(decompileInstruction(intArrayOf(9, 2, 3, 0)), intArrayOf(9, 2, 3, 4))))

    }

    private fun validate(a: Int, b: Int, c: Int, d: Int, actual: IntArray) = validate(intArrayOf(a, b, c, d), actual)





}




