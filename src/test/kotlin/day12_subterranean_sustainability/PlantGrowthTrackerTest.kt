package day12_subterranean_sustainability

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
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
    fun verifyExample() {

        var tracker = PlantGrowthTracker(exampleInitialState, exampleTransformations)

        tracker = tracker.nextGeneration(20)

        assertThat(tracker.sumOfAllPotsContainingPlants(), equalTo(325))


    }
}