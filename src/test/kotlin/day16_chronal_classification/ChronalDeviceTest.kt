package day16_chronal_classification

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
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

        val chunked = CHRONAL_DEVICE_PART_ONE_INPUT.trimIndent().lines().chunked(4)
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

        assertTrue(validate(intArrayOf(3, 2, 3, 1), performBitwiseOrRegister(sample.instruction, sample.before)))
        assertTrue(validate(intArrayOf(3, 2, 1, 1), performBitwiseOrImmediate(sample.instruction, sample.before)))


        assertTrue(validate(3, 2, 1, 1, performSetRegister(sample.instruction, sample.before)))


        assertTrue(validate(4, 4, 1, 0, performGreaterThanImmediateRegister(intArrayOf(9, 1, 3, 2), intArrayOf(4, 4, 4, 0))))
        assertTrue(validate(4, 4, 0, 0, performGreaterThanImmediateRegister(intArrayOf(9, 1, 2, 2), intArrayOf(4, 4, 4, 0))))

        assertTrue(validate(1, 4, 4, 0, performGreaterThanRegisterImmediate(intArrayOf(9, 1, 2, 0), intArrayOf(4, 4, 4, 0))))
        assertTrue(validate(0, 4, 4, 0, performGreaterThanRegisterImmediate(intArrayOf(9, 1, 9, 0), intArrayOf(4, 4, 4, 0))))

        assertTrue(validate(1, 2, 3, 4, performGreaterThanRegisterRegister(intArrayOf(9, 2, 1, 0), intArrayOf(9, 2, 3, 4))))
        assertTrue(validate(0, 2, 3, 4, performGreaterThanRegisterRegister(intArrayOf(9, 1, 3, 0), intArrayOf(9, 2, 3, 4))))


        assertTrue(validate(1, 2, 3, 4, performEqualImmediateRegister(intArrayOf(9, 2, 1, 0), intArrayOf(9, 2, 3, 4))))
        assertTrue(validate(0, 2, 3, 4, performEqualImmediateRegister(intArrayOf(9, 1, 3, 0), intArrayOf(9, 2, 3, 4))))


        assertTrue(validate(1, 2, 3, 4, performEqualRegisterImmediate(intArrayOf(9, 2, 3, 0), intArrayOf(9, 2, 3, 4))))
        assertTrue(validate(0, 2, 3, 4, performEqualRegisterImmediate(intArrayOf(9, 2, 9, 0), intArrayOf(9, 2, 3, 4))))


        assertTrue(validate(1, 2, 4, 4, performEqualRegisterRegister(intArrayOf(9, 2, 3, 0), intArrayOf(9, 2, 4, 4))))
        assertTrue(validate(0, 2, 3, 4, performEqualRegisterRegister(intArrayOf(9, 2, 3, 0), intArrayOf(9, 2, 3, 4))))

    }

    private fun validate(a: Int, b: Int, c: Int, d: Int, actual: IntArray) = validate(intArrayOf(a, b, c, d), actual)


    private fun validate(sample: Sample, actual: IntArray) = validate(sample.after, actual)


    private fun validate(expected: IntArray, actual: IntArray) : Boolean {
        return Arrays.equals(expected, actual)
    }



}


data class Sample(val before: IntArray = intArrayOf(),
                  val instruction: IntArray = intArrayOf(),
                  val after: IntArray = intArrayOf())

fun parseIntoSample(sampleInput: String) : Sample {
    return buildSampleFromChunk(sampleInput.trimIndent().lines().withIndex())
}

private fun buildSampleFromChunk(withIndex: Iterable<IndexedValue<String>>): Sample {
    var sample = Sample()
    withIndex
            .forEach {
                when (it.index) {
                    0 -> sample = sample.copy(before = parseBefore(it.value))
                    1 -> sample = sample.copy(instruction = parseInstruction(it.value))
                    2 -> sample = sample.copy(after = parseAfter(it.value))
                }
            }
    return sample
}


private fun parseIntoSample(chunkOfSampleInput: List<String>) = buildSampleFromChunk(chunkOfSampleInput.withIndex())





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

