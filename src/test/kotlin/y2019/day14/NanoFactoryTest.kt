package y2019.day14

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.greaterThan
import com.natpryce.hamkrest.lessThan
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NanoFactoryTest {


    @Test
    fun acceptExampleOne() {
        assertThat(minimumAmountOfOreNeededForOneFuel(EXAMPLE_ONE), equalTo(31L))
    }

    @Test
    fun acceptExampleTwo() {
        assertThat(minimumAmountOfOreNeededForOneFuel(SECOND_EXAMPLE), equalTo(165L))
    }

    @Test
    fun acceptFifthExample() {
        assertThat(minimumAmountOfOreNeededForOneFuel(FIFTH_EXAMPLE), equalTo(2210736L))
    }

    @Test
    fun shouldParseReaction() {
        val reactionDescription = "7 A, 1 C => 1 D"
        val reaction: Reaction = parseReaction(reactionDescription)

        assertThat(reaction.input[0], equalTo(Ingredient(7, "A")))
        assertThat(reaction.input[1], equalTo(Ingredient(1, "C")))
        assertThat(reaction.output, equalTo(Ingredient(1, "D")))

        assertFalse(reaction.needs("B"))
        assertTrue(reaction.needs("A"))

    }


    @Test
    fun acceptActualPartOne() {
        assertThat(minimumAmountOfOreNeededForOneFuel(NANO_REACTIONS), equalTo(783895L))
    }


    @Test
    fun buildReverseOrderOfChemicalsToProduce() {
        val parseReactions = parseReactions(EXAMPLE_ONE)
        val orderedListOfChemicals: List<String> = determineOrderInWhichChemicalsShouldBeProduced(parseReactions)

        assertThat(orderedListOfChemicals, equalTo(listOf("FUEL", "E", "D", "C", "A", "B")))
    }

    @Test
    fun checkIfReactionCanBePerformed() {
        val reactionDescriptions = "10 ORE => 1 FUEL"
        val parseReactions = parseReactions(reactionDescriptions)

        val nanoFactory = NanoFactory(parseReactions)

        val ingredient = Ingredient(1L, "FUEL")
        val (canBeProduced, recipe) = nanoFactory.canIngredientBeProduced(ingredient, CargoHold(stock = mapOf(Pair("ORE", 10L))))
        assertTrue(canBeProduced)
        assertThat(recipe.steps.peek().size, equalTo(1L))
        assertFalse(nanoFactory.canIngredientBeProduced(ingredient, CargoHold(stock = mapOf(Pair("ORE", 9L)))).first)

    }

    @Test
    fun checkIfRecursiveReactionCanBePerformed() {
        val reactionDescriptions = """5 ORE => 1 A
            2 A => 1 FUEL"""
        val parseReactions = parseReactions(reactionDescriptions)
        val nanoFactory = NanoFactory(parseReactions)
        assertTrue(canQuantityOfFuelBeProduced(1L, nanoFactory, 10L).first)
        assertFalse(canQuantityOfFuelBeProduced(1L, nanoFactory, 9L).first)

    }



    @Test
    fun `verify initial example for part two`() {
        val parseReactions = parseReactions(EXAMPLE_ONE)
        val nanoFactory = NanoFactory(parseReactions)
        assertTrue("Should be able to produce 1 FUEL from 31 ORE", canQuantityOfFuelBeProduced(1L, nanoFactory, 31L).first)
        assertFalse("Should be able to produce 1 FUEL from 30 ORE", canQuantityOfFuelBeProduced(1L, nanoFactory, 30L).first)
    }


    @Test
    fun `use second example for part two`() {
        val parseReactions = parseReactions(SECOND_EXAMPLE)
        val nanoFactory = NanoFactory(parseReactions)

        assertFalse("Should be able to produce 1 FUEL from 164 ORE", canQuantityOfFuelBeProduced(1L, nanoFactory, 164L).first)

        val cargoHold = buildCargoHold(165L)
        val (canBeProduced, recipe) =  canQuantityOfFuelBeProduced(1L, nanoFactory, cargoHold)
        assertTrue("Should be able to produce 1 FUEL from 165 ORE", canBeProduced)

        val updatedCargoHold = cargoHold.performAllProcesses(recipe.steps)

        assertThat("Amount of FUEL", updatedCargoHold.availableQuantity(FUEL), equalTo(1L))
        assertThat("Remaining B", updatedCargoHold.availableQuantity("B"), equalTo(1L))
        assertThat("Remaining C", updatedCargoHold.availableQuantity("C"), equalTo(3L))


    }

    @Test
    fun `yield fuel for third example`() {
        val parseReactions = parseReactions(THIRD_EXAMPLE)
        val nanoFactory = NanoFactory(parseReactions)

        assertTrue("Should be able to produce 1 FUEL from 13312 ORE", canQuantityOfFuelBeProduced(1L, nanoFactory, 13312L).first)
        assertFalse("Should be able to produce 1 FUEL from 13311 ORE", canQuantityOfFuelBeProduced(1L, nanoFactory, 13311L).first)
    }

    @Test
    fun verifyMaximumAmountOfFuelForThirdExample() {

        assertThat(maximumAmountOfFuelToProduce(THIRD_EXAMPLE), equalTo(82892753L))
    }

    @Test
    fun verifyMaximumAmountOfFuelForFourthExample() {
        assertThat(maximumAmountOfFuelToProduce(FOURTH_EXAMPLE), equalTo(5586022L))
    }

    @Test
    fun verifyMaximumAmountOfFuelForFifthExample() {
        assertThat(maximumAmountOfFuelToProduce(FIFTH_EXAMPLE), equalTo(460664L))
    }

    @Test
    fun actualPartTwo() {
        assertThat(maximumAmountOfFuelToProduce(NANO_REACTIONS), equalTo(1896688L))


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

private const val SECOND_EXAMPLE = """
9 ORE => 2 A
8 ORE => 3 B
7 ORE => 5 C
3 A, 4 B => 1 AB
5 B, 7 C => 1 BC
4 C, 1 A => 1 CA
2 AB, 3 BC, 4 CA => 1 FUEL
"""


private const val THIRD_EXAMPLE = """
157 ORE => 5 NZVS
165 ORE => 6 DCFZ
44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL
12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ
179 ORE => 7 PSHF
177 ORE => 5 HKGWZ
7 DCFZ, 7 PSHF => 2 XJWVT
165 ORE => 2 GPVTF
3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT"""


private const val FOURTH_EXAMPLE = """
2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG
17 NVRVD, 3 JNWZP => 8 VPVL
53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL
22 VJHF, 37 MNCFX => 5 FWMGM
139 ORE => 4 NVRVD
144 ORE => 7 JNWZP
5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC
5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV
145 ORE => 6 MNCFX
1 NVRVD => 8 CXFTF
1 VJHF, 6 MNCFX => 4 RFSQX
176 ORE => 6 VJHF"""

private const val FIFTH_EXAMPLE = """
171 ORE => 8 CNZTR
7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL
114 ORE => 4 BHXH
14 VRPVC => 6 BMBT
6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL
6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT
15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW
13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW
5 BMBT => 4 WPTQ
189 ORE => 9 KTJDG
1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP
12 VRPVC, 27 CNZTR => 2 XDBXC
15 KTJDG, 12 BHXH => 5 XCVML
3 BHXH, 2 VRPVC => 7 MZWV
121 ORE => 7 VRPVC
7 XCVML => 6 RJRHP
5 BHXH, 4 VRPVC => 5 LTCX    
"""