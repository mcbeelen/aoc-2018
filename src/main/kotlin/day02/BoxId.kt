package day02

class BoxId(private val label: String) {

    val hasAnyLetterTwice : Boolean by lazy { hasAnyLetterTwice() }
    val hasAnyLetterTripple : Boolean by lazy { hasAnyLetterTripple() }

    private val letterOccurrenceMap = label
            .groupingBy { it }
            .eachCount()

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

}
