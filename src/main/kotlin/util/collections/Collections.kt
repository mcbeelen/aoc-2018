package util.collections



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

fun <T> Iterable<T>.toDeque() : ArrayDeque<T> {
    return ArrayDeque(this.toList());
}
