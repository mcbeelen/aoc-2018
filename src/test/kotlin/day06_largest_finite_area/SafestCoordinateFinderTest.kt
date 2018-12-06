package day06_largest_finite_area

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test


class SafestCoordinateFinderTest {


    @Test
    fun itShouldFindTheSafestAreaInTheExampleIs17() {
        assertThat(findSafestAreaBetween(DAY06_EXAMPLE_INPUT), equalTo(17))
    }

    private fun findSafestAreaBetween(coordinatesInput: String): Int {
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