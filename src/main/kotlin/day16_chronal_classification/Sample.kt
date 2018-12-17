package day16_chronal_classification


data class Sample(val before: IntArray = intArrayOf(),
                  val instruction: IntArray = intArrayOf(),
                  val after: IntArray = intArrayOf())

fun readDeviceSamples(actualInput: String): List<Sample> {
    val chunked = actualInput.trimIndent().lines().chunked(4)
    return chunked.map { parseIntoSample(it) }
}


fun parseIntoSample(chunkOfSampleInput: List<String>) = buildSampleFromChunk(chunkOfSampleInput.withIndex())


fun parseIntoSample(sampleInput: String) : Sample {
    return buildSampleFromChunk(sampleInput.trimIndent().lines().withIndex())
}

fun buildSampleFromChunk(withIndex: Iterable<IndexedValue<String>>): Sample {
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
