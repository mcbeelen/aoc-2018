package y2019.day22_shuffle_the_deck

import kotlin.math.absoluteValue

fun deckOfSize(i: Int) = (0 until i).toList()

fun dealIntoNewStack(originalDeck: List<Int>) = originalDeck.reversed()

fun cutTheDeck(originalDeck: List<Int>, cutOff: Int): List<Int> = if (cutOff > 0) {
    cutFromTheTop(originalDeck, cutOff)
} else {
    cutFromTheBottom(originalDeck, cutOff.absoluteValue)
}

private fun cutFromTheBottom(originalDeck: List<Int>, cutOff: Int) = originalDeck.takeLast(cutOff) + originalDeck.dropLast(cutOff)

private fun cutFromTheTop(originalDeck: List<Int>, cutOff: Int) = originalDeck.drop(cutOff) + originalDeck.take(cutOff)

fun dealWithIncrement(originalDeck: List<Int>, increment: Int): List<Int> {
    val newDeck: MutableList<Int> = ArrayList(originalDeck)
    (originalDeck.indices)
            .forEach {
                val index = it * increment % originalDeck.size
                newDeck[index] = originalDeck[it]
            }
    return newDeck

}

fun shuffleDeck(deckSize: Int, shuffleInstructions: String): List<Int> {
    val instructions = shuffleInstructions.trimIndent().lines()
    var deck = deckOfSize(deckSize)
    instructions.forEach { instruction ->
        deck = processInstruction(deck, instruction)
    }
    return deck
}

private fun processInstruction(deck: List<Int>, instruction: String) : List<Int> {
    return when {
        instruction.startsWith("cut") -> cutTheDeck(deck, extractParameter(instruction))
        instruction.startsWith("deal into new stack") -> dealIntoNewStack(deck)
        instruction.startsWith("deal with increment ") -> dealWithIncrement(deck, extractParameter(instruction))
        else -> deck
    }
}

private fun extractParameter(instruction: String) = instruction.substringAfterLast(" ").toInt()

fun main() {
    val shuffledDeck = shuffleDeck(10007, SHUFFLE_INSTRUCTIONS)
    println(shuffledDeck.indexOf(2019))
}


private const val SHUFFLE_INSTRUCTIONS = """
cut 9712
deal with increment 23
cut 6635
deal with increment 18
cut 887
deal with increment 47
deal into new stack
deal with increment 53
cut -1593
deal with increment 3
cut -6676
deal with increment 69
cut -4313
deal with increment 55
cut 609
deal with increment 42
deal into new stack
deal with increment 51
deal into new stack
cut 880
deal with increment 75
cut -964
deal with increment 33
cut 7911
deal with increment 71
deal into new stack
cut -5716
deal with increment 52
cut 5969
deal with increment 30
cut -3508
deal with increment 25
cut -7645
deal with increment 29
cut 8929
deal into new stack
cut 4850
deal with increment 34
cut -1
deal with increment 55
cut 7491
deal with increment 74
cut 3331
deal with increment 35
cut 8433
deal into new stack
deal with increment 66
cut 3725
deal with increment 3
deal into new stack
deal with increment 19
deal into new stack
cut -8821
deal with increment 27
deal into new stack
cut -9853
deal with increment 71
cut -9286
deal with increment 39
deal into new stack
cut 4288
deal into new stack
deal with increment 11
deal into new stack
deal with increment 50
cut 153
deal with increment 32
cut 6836
deal with increment 65
cut 9504
deal with increment 11
deal into new stack
cut -7646
deal with increment 4
cut 5795
deal with increment 65
deal into new stack
deal with increment 23
cut 7208
deal with increment 17
deal into new stack
cut -5333
deal with increment 18
deal into new stack
deal with increment 46
deal into new stack
deal with increment 73
cut 1661
deal with increment 34
cut 7121
deal with increment 13
cut 1266
deal with increment 71
cut 2328
deal with increment 6
cut 6005
deal with increment 49
cut -3871
deal with increment 9
cut 8441
"""