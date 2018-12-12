package day12_subterranean_sustainability

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test

const val exampleInitialState = "#..#.#..##......###...###"

const val exampleTransformations = """...## => #
..#.. => #
.#... => #
.#.#. => #
.#.## => #
.##.. => #
.#### => #
#.#.# => #
#.### => #
##.#. => #
##.## => #
###.. => #
###.# => #
####. => #"""

class PlantGrowthTrackerTest {

    @Test
    fun itShouldBeAbleToParseInitialState() {

        val potsWithPlants: Set<Int> = parseInitialState(exampleInitialState)
        potsWithPlants.let {
            assertThat(it, hasSize(equalTo(11)))
            assertTrue(it.containsAll(listOf(0, 3, 5, 8, 9, 16, 17, 18, 22, 23, 24)))
        }
    }




    @Test
    fun isApplicable() {
        val transformation = Transformation(setOf(-1, 1), setOf(-2, 0, 2))

        val potsWithPlants = parseInitialState(exampleInitialState)
        assertFalse(transformation.isApplicable(0, potsWithPlants))
        assertTrue(transformation.isApplicable(4, potsWithPlants))
    }



    @Test
    @Ignore
    fun verifyExample() {

        var tracker = PlantGrowthTracker(exampleInitialState, exampleTransformations)

        tracker = tracker.nextGeneration(20)

        assertThat(tracker.sumOfAllPotsContainingPlants(), equalTo(325))


    }
}