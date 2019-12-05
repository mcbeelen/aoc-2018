package y2019.day04


fun couldBePassword(password: Int): Boolean {
    val digits = password.digits()
    return digitsNeverDecrease(digits) and containsOneRepeatingLetter(digits)
}

fun couldStillBePassword(password: Int): Boolean {
    val digits = password.digits()
    val digitsWithoutLargeGroups = digits.filterIndexed { index, c -> ! isPartOfLargerGroep(c, index, digits) }

    return digitsNeverDecrease(digits) and containsOneRepeatingLetter(digitsWithoutLargeGroups)
}

fun isPartOfLargerGroep(c: Char, index: Int, digits: String): Boolean {
    if (index < 4 && c == digits[index + 1] && c == digits[index + 2]) return true
    if (index in 1..4 && (c == digits[index - 1]) && c == digits[index + 1]) return true
    if (index >= 2 && c == digits[index - 2] && c == digits[index - 1]) return true
    return false
}


private fun containsOneRepeatingLetter(digits: String): Boolean {
    digits.forEachIndexed() { index: Int, c: Char ->
        if (index > 0 && c == digits[index - 1]) {
            return true
        }
    }
    return false
}

private fun digitsNeverDecrease(digits: String): Boolean {
    digits.forEachIndexed() { index: Int, c: Char ->
        if (index > 0 && c < digits[index - 1]) {
            return false
        }
    }
    return true
}


private fun Int.digits(): String {
    return this.toString()
}


fun main() {

    println((109165 until 576723)
            .filter { couldBePassword(it) }
            .count())

    println((109165 until 576723)
            .filter { couldStillBePassword(it) }
            .count())



}

