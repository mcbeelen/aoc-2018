package y2020.d07

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import org.junit.Test
import util.collections.Queue
import util.input.loadLines


class AviationLuggageRulesTest {

    @Test
    fun examplePartOne() {
        assertThat(solveIt("/y2020/d07/testInput.txt"), equalTo(4))
    }

    @Test
    fun partOne() {
        assertThat(solveIt("/y2020/d07/input.txt"), equalTo(238))
    }

    @Test
    fun `light red bags contain 1 bright white bag, 2 muted yellow bags`() {
        val aviationLuggageRulesForLightRedsBags = parseAviationLuggageRule("light red bags contain 1 bright white bag, 2 muted yellow bags.")

        assertThat(aviationLuggageRulesForLightRedsBags.colorCodedBag, equalTo("light red"))
        assertThat(aviationLuggageRulesForLightRedsBags.allowedContents, hasSize(equalTo(2)))
        assertThat(aviationLuggageRulesForLightRedsBags.allowedContents[0].quantity, equalTo(1))
        assertThat(aviationLuggageRulesForLightRedsBags.allowedContents[0].colorCodedBag, equalTo("bright white"))
        assertThat(aviationLuggageRulesForLightRedsBags.allowedContents[1].quantity, equalTo(2))
        assertThat(aviationLuggageRulesForLightRedsBags.allowedContents[1].colorCodedBag, equalTo("muted yellow"))
    }
}


