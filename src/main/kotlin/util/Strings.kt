package util

fun countLetters(label: String): Map<Char, Int> {
    return label
            .groupingBy { it }
            .eachCount()
}