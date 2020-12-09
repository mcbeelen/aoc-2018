package util.input

fun interface InputParser<out T> {
    fun parse(input: String) : T
}


fun parseInput(input: String) : Iterable<String> {
    return parseInput(input) { it }
}

fun <T> parseInput(input: String, parser: InputParser<T>) : Iterable<T> {
    return input.trimIndent().lines()
            .filter { it.isNotBlank() }
            .map {
        parser.parse(it.trim())
    }
}
fun parseInputToInts(input: String) = parseInput(input){ it.toInt() }
fun parseInputToLongs(input: String) = parseInput(input){ it.toLong() }
