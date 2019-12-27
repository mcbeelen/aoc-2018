package y2019.day22_shuffle_the_deck

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class SpaceCardShuffleTest {
    @Test
    fun `it should deal into new stack,`() {

        val shuffledDeck = shuffleDeck(10, "deal into new stack")

        assertThat(shuffledDeck[0], equalTo(9))
        assertThat(shuffledDeck[5], equalTo(4))
        assertThat(shuffledDeck[9], equalTo(0))
    }

    @Test
    fun `it should cut the deck by 3`() {
        val shuffledDeck = shuffleDeck(10, "cut 3")

        assertThat(shuffledDeck, equalTo(listOf(3, 4, 5, 6, 7, 8, 9, 0, 1, 2)))
    }

    @Test
    fun `it should cut the deck by -4`() {
        val shuffledDeck = shuffleDeck(10, "cut -4")

        assertThat(shuffledDeck, equalTo(listOf(6, 7, 8, 9, 0, 1, 2, 3, 4, 5)))
    }

    @Test
    fun itShouldDealWithIncrementsOfThree() {
        val shuffledDeck = shuffleDeck(10, "deal with increment 3")
        assertThat(shuffledDeck, equalTo(listOf(0, 7, 4, 1, 8, 5, 2, 9, 6, 3)))
    }

    @Test
    fun itShouldDealWithIncrementsOfSeven() {
        val shuffledDeck = shuffleDeck(10, "deal with increment 7")
        assertThat(shuffledDeck, equalTo(listOf(0, 3, 6, 9, 2, 5, 8, 1, 4, 7)))
    }



    @Test
    fun firstExample() {
        val shuffledDeck = shuffleDeck(10, FIRST_EXAMPLE)
        assertThat(shuffledDeck, equalTo(listOf(0, 3, 6, 9, 2, 5, 8, 1, 4, 7)))
    }

    @Test
    fun supportExampleTwo() {
        val deck = listOf(6, 7, 8, 9, 0, 1, 2, 3, 4, 5)
        val shuffledDeck = dealWithIncrement(deck, 7)
        assertThat(shuffledDeck, equalTo(listOf(6, 9, 2, 5, 8, 1, 4, 7, 0, 3)))

    }

    @Test
    fun secondExample() {
        val shuffledDeck = shuffleDeck(10, SECOND_EXAMPLE)
        assertThat(shuffledDeck, equalTo(listOf(3, 0, 7, 4, 1, 8, 5, 2, 9, 6)))
    }

    @Test
    fun fourthExample() {
        val shuffledDeck = shuffleDeck(10, FOURTH_EXAMPLE)
        assertThat(shuffledDeck, equalTo(listOf(9, 2, 5, 8, 1, 4, 7, 0, 3, 6)))
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