package day02

class BoxId(private val label: String) {

    val hasAnyLetterTwice : Boolean by lazy { hasAnyLetterTwice() }
    val hasAnyLetterTripple : Boolean by lazy { hasAnyLetterTripple() }


    private fun hasAnyLetterTwice(): Boolean {
        return hasLetterWithOccurance(2)
    }

    private fun hasAnyLetterTripple(): Boolean {
        return hasLetterWithOccurance(3)
    }

    private fun hasLetterWithOccurance(i: Int): Boolean {
        return label
                .groupingBy { it }
                .eachCount()
                .filter { it.value == i }
                .any()
    }

}
