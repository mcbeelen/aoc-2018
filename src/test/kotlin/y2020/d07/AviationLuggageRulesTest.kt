package y2020.d07

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import org.junit.Test
import util.input.loadLines

class AviationLuggageRulesTest {

    @Test
    fun examplePartOne() {
        val input = loadLines("/y2020/d07/testInput.txt")
        assertThat(countBagColorWhichMayContainShinyGoldBag(input), equalTo(4))
    }

    @Test
    fun partOne() {
        val input = loadLines("/y2020/d07/input.txt")
        assertThat(countBagColorWhichMayContainShinyGoldBag(input), equalTo(238))
    }


    @Test
    fun examplePartTwo() {
        val input = loadLines("/y2020/d07/testInput.txt")
        assertThat(countRequiredAmountOfBagsWithin(input, "faded blue"), equalTo(0))
        assertThat(countRequiredAmountOfBagsWithin(input, "dark olive"), equalTo(7))
        assertThat(countRequiredAmountOfBagsWithin(input, REQUIRED_TYPE_OF_LUGGAGE), equalTo(32))
    }

    @Test
    fun anotherExampleForPartTwo() {
        assertThat(countRequiredAmountOfBagsWithin(anotherExample.lines(), REQUIRED_TYPE_OF_LUGGAGE), equalTo(126))
    }

    @Test
    fun partTwo() {
        val input = loadLines("/y2020/d07/input.txt")
        assertThat(countRequiredAmountOfBagsWithin(input, REQUIRED_TYPE_OF_LUGGAGE), equalTo(82930))
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

private const val anotherExample = """shiny gold bags contain 2 dark red bags.
dark red bags contain 2 dark orange bags.
dark orange bags contain 2 dark yellow bags.
dark yellow bags contain 2 dark green bags.
dark green bags contain 2 dark blue bags.
dark blue bags contain 2 dark violet bags.
dark violet bags contain no other bags."""

