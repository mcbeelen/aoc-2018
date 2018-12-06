package day06_largest_finite_area

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.grid.parseXcommaY


class SafestCoordinateFinderTest {


    @Test
    fun itShouldFindTheSafestAreaInTheExampleIs17() {
        assertThat(findSafestAreaBetween(DAY06_EXAMPLE_INPUT), equalTo(17))
    }

    private fun findSafestAreaBetween(coordinatesInput: String): Int {

        // TODO:
        // - Parse input into coordinates
        // - Determine complete area, which needs to be inspects
        // - For each point in the area: find closest TargetCoordinate
        // - Map area into TargetCoordinate, countSize
        // - MaxBy areaSize

        return 0
    }
}



const val DAY06_EXAMPLE_INPUT = """
1, 1
1, 6
8, 3
3, 4
5, 5
8, 9
"""