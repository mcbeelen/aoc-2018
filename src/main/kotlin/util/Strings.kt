package util

fun countLetters(label: String): Map<Char, Int> {
    return label
            .groupingBy { it }
            .eachCount()
}

fun CharSequence.distinctChars() = this.asIterable().distinct()

fun String.withoutWhitespace() = replace("\\s*".toRegex(), "")
