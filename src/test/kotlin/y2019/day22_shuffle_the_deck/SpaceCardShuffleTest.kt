package y2019.day22_shuffle_the_deck

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Ignore
import org.junit.Test
import kotlin.system.exitProcess

class SpaceCardShuffleTest {
    @Test
    fun `it should deal into new stack,`() {

        val shuffledDeck = shuffleDeck(10, "deal into new stack")

        assertThat(shuffledDeck[0], equalTo(9L))
        assertThat(shuffledDeck[5], equalTo(4L))
        assertThat(shuffledDeck[9], equalTo(0L))
    }

    @Test
    fun `it should cut the deck by 3`() {
        val shuffledDeck = shuffleDeck(10, "cut 3")

        assertThat(shuffledDeck, equalTo(deckOf(3, 4, 5, 6, 7, 8, 9, 0, 1, 2)))
    }

    private fun deckOf(vararg values: Int) = values.map { it.toLong() }.toList()

    @Test
    fun `it should cut the deck by -4`() {
        val shuffledDeck = shuffleDeck(10, "cut -4")

        assertThat(shuffledDeck, equalTo(deckOf(6, 7, 8, 9, 0, 1, 2, 3, 4, 5)))
    }

    @Test
    fun itShouldDealWithIncrementsOfThree() {
        val shuffledDeck = shuffleDeck(10, "deal with increment 3")
        assertThat(shuffledDeck, equalTo(deckOf(0, 7, 4, 1, 8, 5, 2, 9, 6, 3)))
    }

    @Test
    fun itShouldDealWithIncrementsOfSeven() {
        val shuffledDeck = shuffleDeck(10, "deal with increment 7")
        assertThat(shuffledDeck, equalTo(deckOf(0, 3, 6, 9, 2, 5, 8, 1, 4, 7)))
    }


    @Test
    fun firstExample() {
        val shuffledDeck = shuffleDeck(10, FIRST_EXAMPLE)
        assertThat(shuffledDeck, equalTo(deckOf(0, 3, 6, 9, 2, 5, 8, 1, 4, 7)))
    }

    @Test
    fun supportExampleTwo() {
        val deck = deckOf(6, 7, 8, 9, 0, 1, 2, 3, 4, 5)
        val shuffledDeck = dealWithIncrement(deck, 7)
        assertThat(shuffledDeck, equalTo(deckOf(6, 9, 2, 5, 8, 1, 4, 7, 0, 3)))

    }

    @Test
    fun secondExample() {
        val shuffledDeck = shuffleDeck(10, SECOND_EXAMPLE)
        assertThat(shuffledDeck, equalTo(deckOf(3, 0, 7, 4, 1, 8, 5, 2, 9, 6)))
    }

    @Test
    fun fourthExample() {
        val shuffledDeck = shuffleDeck(10, FOURTH_EXAMPLE)
        assertThat(shuffledDeck, equalTo(deckOf(9, 2, 5, 8, 1, 4, 7, 0, 3, 6)))
    }

    @Test
    fun abelianGroup() {

        val deckOfSize = deckOfSize(119315717514047)
        val instructions = SHUFFLE_INSTRUCTIONS.trimIndent().lines()

        val shuffledDeck = shuffleDeck(deckOfSize, instructions.shuffled())

        assertThat(shuffledDeck.indexOf(2019), equalTo(6431))


    }

    @Test @Ignore
    fun findPattern() {
        val deckOfSize = deckOfSize(10007)
        var shuffledDeck = deckOfSize
        val instructions = SHUFFLE_INSTRUCTIONS.trimIndent().lines()
        var counter = 0
        while (counter < 10) {
            counter++
            shuffledDeck = shuffleDeck(shuffledDeck, instructions)
            println(shuffledDeck.subList(0, 99))
        }

    }
}


private const val FIRST_EXAMPLE = """
deal with increment 7
deal into new stack
deal into new stack    
"""


private const val SECOND_EXAMPLE = """
cut 6
deal with increment 7
deal into new stack    
"""


private const val FOURTH_EXAMPLE = """
deal into new stack
cut -2
deal with increment 7
cut 8
cut -4
deal with increment 7
cut 3
deal with increment 9
deal with increment 3
cut -1"""

/*
--- Part Two ---
After a while,
you realize your shuffling skill won't improve much more with merely a single deck of cards.
You ask every 3D printer on the ship to make you some more cards while you check on the ship repairs.
While reviewing the work the droids have finished so far, you think you see Halley's Comet fly past!

When you get back, you discover that the 3D printers have combined their power to create for you
a single, giant, brand new, factory order deck of 119315717514047 space cards.

Finally, a deck of cards worthy of shuffling!

You decide to apply your complete shuffle process (your puzzle input) to the deck 101741582076661 times in a row.

You'll need to be careful, though - one wrong move with this many cards and you might overflow your entire ship!

After shuffling your new, giant, factory order deck that many times,
what number is on the card that ends up in position 2020?


 */