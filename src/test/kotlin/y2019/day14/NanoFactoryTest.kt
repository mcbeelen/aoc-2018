package y2019.day14

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class NanoFactoryTest {

    @Test
    fun acceptExampleOne() {
        assertThat(minimumAmountOfOreNeededForOneFuel(EXAMPLE_ONE), equalTo(31))
    }

    @Test
    fun shouldParseReaction() {
        val reactionDescription = "7 A, 1 C => 1 D"
        val reaction : Reaction = parseReaction(reactionDescription)

        assertThat(reaction.input[0], equalTo(Chemical(7, "A")))
        assertThat(reaction.input[1], equalTo(Chemical(1, "C")))
        assertThat(reaction.output, equalTo(Chemical(1, "D")))

    }


}





private const val EXAMPLE_ONE = """
10 ORE => 10 A
1 ORE => 1 B
7 A, 1 B => 1 C
7 A, 1 C => 1 D
7 A, 1 D => 1 E
7 A, 1 E => 1 FUEL
"""