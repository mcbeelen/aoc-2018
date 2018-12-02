package util

fun <T> circularSequence(input: List<T>): Sequence<T> {
    return sequence {
        var index = 0

        while (true) {
            yield(input[index])
            index++
            if (index == input.size) {
                index = 0
            }
        }

    }
}
