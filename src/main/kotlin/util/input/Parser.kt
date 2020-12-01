package util.input

fun interface InputParser<out T> {
    fun parse(input: String) : T
}


fun <T> parseInput(input: String, parser: InputParser<T>) : Iterable<T> {
    return input.trimIndent().lines().map {
        parser.parse(it)
    }
}
fun parseInputToInts(input: String) = parseInput(input){ it.toInt() }
