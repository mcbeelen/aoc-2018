package y2018.day16_chronal_classification


data class Sample(val before: IntArray = intArrayOf(),
                  val instruction: Instruction = Instruction(OpCode.NOOP, intArrayOf()),
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

fun parseInstruction(value: String) : Instruction {

    val assemblyCodes = value
            .split(" ")
            .map { it.trim().toInt() }
            .toIntArray()

    return decompileInstruction(assemblyCodes)


}

fun decompileInstruction(assemblyCodes: IntArray): Instruction {
    val opCode = opCode(assemblyCodes.first())
    val arguments = assemblyCodes.toList().drop(1).toIntArray()
    return Instruction(opCode, arguments)
}

fun parseAfter(value: String) = parseCommaSeperatedValueBetweenBrackets(value)

private fun parseCommaSeperatedValueBetweenBrackets(value: String) = value
        .substringAfter('[').substringBefore(']')
        .split(',')
        .map { it.trim().toInt() }
        .toIntArray()
