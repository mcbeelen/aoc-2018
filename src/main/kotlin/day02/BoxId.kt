package day02

class BoxId(private val label: String) {

    fun hasAnyLetterTwice(): Boolean {
        return label
                .groupingBy { it }
                .eachCount()
                .filter { it.value == 2 }
                .any()
    }

}
