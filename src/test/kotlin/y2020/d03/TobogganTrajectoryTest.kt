package y2020.d03

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.input.parseInput

class TobogganTrajectoryTest {

    @Test
    fun itShouldHitSevenTrees() {

        assertThat(7, equalTo(numberOfTreesToEncounterOnSlope(y2020d03testInput, 3)))
        assertThat(286, equalTo(numberOfTreesToEncounterOnSlope(y2020d03input, 3)))

    }

    @Test
    fun itShouldCheckVariousSlopes() {
        assertThat(2, equalTo(numberOfTreesToEncounterOnSlope(y2020d03testInput, 1)))
        assertThat(7, equalTo(numberOfTreesToEncounterOnSlope(y2020d03testInput, 3)))
        assertThat(3, equalTo(numberOfTreesToEncounterOnSlope(y2020d03testInput, 5)))
        assertThat(4, equalTo(numberOfTreesToEncounterOnSlope(y2020d03testInput, 7)))
        assertThat("With steep decline",2, equalTo(numberOfTreesToEncounterOnSlope(y2020d03testInput, 1, 2)))
    }

    @Test
    fun itShouldCalculateProbabilityOfSuddenArborealStop() {
        val slopes = listOf(
                Pair(1, 1),
                Pair(3, 1),
                Pair(5, 1),
                Pair(7, 1),
                Pair(1, 2)

        )
        val testTreesPerSlope = slopes.map { numberOfTreesToEncounterOnSlope(y2020d03testInput, it.first, it.second) }
        val testSum = testTreesPerSlope.reduce { acc, it -> acc * it }
        assertThat(testSum, equalTo(336))

        val treesPerSlope = slopes.map { numberOfTreesToEncounterOnSlope(y2020d03input, it.first, it.second).toLong() }
        val sum = treesPerSlope.reduce { acc, it -> acc * it }
        assertThat(sum, equalTo(3_638_606_400))
    }

    private fun numberOfTreesToEncounterOnSlope(terrain: String, right: Int, down: Int = 1): Int {
        val lines = parseInput(terrain).toList()
        val width = lines[0].length

        val linesToHits = if (down == 1) { lines } else { lines.filterIndexed { index, _ -> (index % down) == 0 }.toList() }
        return linesToHits.mapIndexed { index, line -> isTreeAt(line, index, right, width) }.filter { it }.count()
    }

    private fun isTreeAt(line: String, index: Int, right: Int, width: Int): Boolean {
        return '#' == line[(index * right) % width]
    }
}
