package day16_chronal_classification

import day16_chronal_classification.OpCode.MULR
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
            assertTrue("INSTRUCTION", Arrays.equals(it.instruction, intArrayOf(9, 2, 1, 2)))
            assertTrue("AFTER", Arrays.equals(it.after, intArrayOf(3, 2, 2, 1)))
        }
    }

    @Test
    fun itShouldRecognizeExampleAsMURL() {


        val sample = parseIntoSample(sample)

        assertTrue(doesSampleBehaveLike(MULR, sample))

    }

    private fun doesSampleBehaveLike(opcode: OpCode, sample: Sample): Boolean {

        return when (opcode) {
            MULR -> validateMultiplyRegisters(sample)
            else -> false
        }

    }

    private fun validateMultiplyRegisters(sample: Sample) : Boolean {
        return Arrays.equals(sample.after, performMultiplyRegisters(sample.instruction, sample.before))
    }

    private fun performMultiplyRegisters(instruction: IntArray, before: IntArray): IntArray {
        val registers = before.clone()
        registers[instruction[3]] = registers[instruction[1]] * registers[instruction[2]]
        return registers

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

