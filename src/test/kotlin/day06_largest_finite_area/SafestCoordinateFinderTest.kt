package day06_largest_finite_area

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.grid.ScreenCoordinate
import util.grid.Square
import util.grid.parseXcommaY


class SafestCoordinateFinderTest {


    @Test
    fun itShouldFindTheSafestAreaInTheExampleIs17() {
        assertThat(findSafestAreaBetween(DAY06_EXAMPLE_INPUT), equalTo(17))
    }

    @Test
    fun itShouldParseTheExampleInput() {
        val targetCoordinates = parseInputIntoTargetCoordinates(DAY06_EXAMPLE_INPUT)
        assertThat(targetCoordinates.size, equalTo(6))
        assertThat(targetCoordinates.first(), equalTo(ScreenCoordinate(1, 1)))
        assertThat(targetCoordinates.last(), equalTo(ScreenCoordinate(8, 9)))
    }


    @Test
    fun itShouldCreateSquareFromOneCommaOneWithDimensions8x9() {


        val exampleCoordinates = listOf(ScreenCoordinate(1, 1), ScreenCoordinate(8, 9), ScreenCoordinate(3, 3))

        buildSquareContainingAll(exampleCoordinates).let {
            assertThat("Left", it.left, equalTo(1))
            assertThat("Top", it.top, equalTo(1))
            assertThat("Bottom", it.bottom, equalTo(1))
            assertThat("Right", it.right, equalTo(9))
        }
    }

    private fun findSafestAreaBetween(coordinatesInput: String): Int {

        val targetCoordinates = parseInputIntoTargetCoordinates(coordinatesInput)
        val square = buildSquareContainingAll(targetCoordinates)


        // TODO:
        // - Determine complete area, which needs to be inspects
        // - For each point in the area: find closest TargetCoordinate
        // - Map area into TargetCoordinate, countSize
        // - MaxBy areaSize

        return 0
    }

    private fun buildSquareContainingAll(coordinates: List<ScreenCoordinate>): Square {
        return Square(coordinates.first(), 1, 1)
    }

    private fun parseInputIntoTargetCoordinates(coordinatesInput: String) = coordinatesInput.trimIndent().lines()
            .map { parseXcommaY(it) }
}



const val DAY06_EXAMPLE_INPUT = """
1, 1
1, 6
8, 3
3, 4
5, 5
8, 9
"""