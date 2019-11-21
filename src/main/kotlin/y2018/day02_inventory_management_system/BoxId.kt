package y2018.day02_inventory_management_system

import util.countLetters

class BoxId( val label: String) {

    val hasAnyLetterTwice : Boolean by lazy { hasAnyLetterTwice() }
    val hasAnyLetterTripple : Boolean by lazy { hasAnyLetterTripple() }

    private val letterOccurrenceMap = countLetters(label)



    private fun hasAnyLetterTwice(): Boolean {
        return hasLetterWithOccurance(2)
    }

    private fun hasAnyLetterTripple(): Boolean {
        return hasLetterWithOccurance(3)
    }



    private fun hasLetterWithOccurance(occurrence: Int): Boolean {
        return letterOccurrenceMap
                .filter { it.value == occurrence }
                .any()
    }

    fun countNumberOfDifferentLetters(anotherBox: BoxId) = label.withIndex()
                .filter { it.value != anotherBox.label.get(it.index)}
                .count()

    fun findCommonLettersWith(other: BoxId) : String {
        return label.withIndex()
                .filter { other.label[it.index] == it.value }
                .map { it.value }
                .joinToString("", "")

    }

}
