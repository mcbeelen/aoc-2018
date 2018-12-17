package day16_chronal_classification

import day16_chronal_classification.OpCode.*
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.lang.UnsupportedOperationException
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
            assertTrue("INSTRUCTION", Arrays.equals(it.instruction, intArrayOf(9, 2, 1, 2)))
            assertTrue("AFTER", Arrays.equals(it.after, intArrayOf(3, 2, 2, 1)))
        }
    }

    @Test
    fun itShouldRecognizeExampleAsMURL() {

        val sample = parseIntoSample(sample)

        assertTrue(doesSampleBehaveLike(ADDI, sample))
        assertFalse(doesSampleBehaveLike(ADDR, sample))
        assertTrue(doesSampleBehaveLike(MULR, sample))
        assertTrue(doesSampleBehaveLike(SETI, sample))

    }

    private fun doesSampleBehaveLike(opcode: OpCode, sample: Sample): Boolean {

        return when (opcode) {
            ADDI -> validate(sample, performAddImmediate(sample.instruction, sample.before))
            ADDR -> validate(sample, performAddRegisters(sample.instruction, sample.before))
            MULR -> validate(sample, performMultiplyRegisters(sample.instruction, sample.before))
            SETI -> validate(sample, performSetImmediate(sample.instruction, sample.before))
            else -> throw UnsupportedOperationException()
        }

    }

    // [3, 2, 1, 1] -> (9 2 1 2)
    @Test
    fun itShouldSupportAllOperations() {
        val sample = parseIntoSample(sample)
        assertTrue(validate(intArrayOf(3, 2, 1, 1), performMultiplyImmediate(sample.instruction, sample.before)))
        assertTrue(validate(intArrayOf(3, 2, 0, 1), performBitwiseAndRegister(sample.instruction, sample.before)))
        assertTrue(validate(intArrayOf(3, 2, 1, 1), performBitwiseAndImmediate(sample.instruction, sample.before)))

    }

    private fun validate(sample: Sample, actual: IntArray) = validate(sample.after, actual)


    private fun validate(expected: IntArray, actual: IntArray) : Boolean {
        return Arrays.equals(expected, actual)
    }



}


data class Sample(val before: IntArray = intArrayOf(),
                  val instruction: IntArray = intArrayOf(),
                  val after: IntArray = intArrayOf())

fun parseIntoSample(sampleInput: String) : Sample {

    var sample = Sample()
    sampleInput.trimIndent().lines().withIndex()
            .forEach {
                when (it.index) {
                    0 -> sample = sample.copy(before = parseBefore(it.value))
                    1 -> sample = sample.copy(instruction = parseInstruction(it.value))
                    2 -> sample = sample.copy(after = parseAfter(it.value))
                }
            }

    return sample

}



/**
 * Before: [3, 2, 1, 1] --> intArrayOf(3, 2, 1, 1)
 */
fun parseBefore(value: String) = parseCommaSeperatedValueBetweenBrackets(value)

fun parseInstruction(value: String) = value
        .split(" ")
        .map { it.trim().toInt() }
        .toIntArray()

fun parseAfter(value: String) = parseCommaSeperatedValueBetweenBrackets(value)

private fun parseCommaSeperatedValueBetweenBrackets(value: String) = value
        .substringAfter('[').substringBefore(']')
        .split(',')
        .map { it.trim().toInt() }
        .toIntArray()

