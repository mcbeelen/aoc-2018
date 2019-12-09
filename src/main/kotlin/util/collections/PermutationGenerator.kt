package util.collections


fun generatePermutations(elements: List<Int>): Sequence<List<Int>> = sequence {

    if (elements.size == 1) yield(elements)

    for (element in elements) {

        val subSequence = generatePermutations(elements - element)
        subSequence.forEach {
            val combination: List<Int> = listOf(element) + it
            yield(combination)
        }
    }
}
