package y2019.day17_ascii_scaffolding

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class ScafoldingInterceptionFinderTest {

    @Test
    fun calculateAlignmentParameter() {
        assertThat(calculateAlignmentParameter(EXAMPLE_FROM_AOC.replace('^', '#')), equalTo(76))
    }
}


private const val EXAMPLE_FROM_AOC = """
..#..........
..#..........
#######...###
#.#...#...#.#
#############
..#...#...#..
..#####...^..
"""