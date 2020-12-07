package util.input

fun String.blocks() = this.splitToSequence("\r\n\r\n", "\n\n", "\r\n").toList()
