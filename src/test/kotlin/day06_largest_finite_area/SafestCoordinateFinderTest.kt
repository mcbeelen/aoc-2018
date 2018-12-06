package day06_largest_finite_area

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.grid.ScreenCoordinate
import util.grid.Square
import util.grid.buildSquareContainingAll
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
            assertThat("Right", it.right, equalTo(8))
            assertThat("Bottom", it.bottom, equalTo(9))
        }
    }

    @Test
    fun itShouldCountAreas() {


        val targetCoordinates = parseInputIntoTargetCoordinates(DAY06_EXAMPLE_INPUT)
        val square = buildSquareContainingAll(targetCoordinates)
        val targetCoordinatesWithClosestPoints = square.allPoints().groupBy { findClosestTarget(it, targetCoordinates) }

        assertThat(targetCoordinatesWithClosestPoints[ScreenCoordinate(3, 4)]!!.size, equalTo(9))
        assertThat(targetCoordinatesWithClosestPoints[ScreenCoordinate(5, 5)]!!.size, equalTo(17))



    }

    private fun findSafestAreaBetween(coordinatesInput: String): Int {

        val targetCoordinates = parseInputIntoTargetCoordinates(coordinatesInput)
        val square = buildSquareContainingAll(targetCoordinates)
        val targetCoordinatesWithClosestPoints = square.allPoints().groupBy { findClosestTarget(it, targetCoordinates) }




        // - Determine complete area, which needs to be inspects
        // - For each point in the area: find closest TargetCoordinate
        // - Map area into TargetCoordinate, countSize
        // - MaxBy areaSize

        return 0
    }

    private fun findClosestTarget(point: ScreenCoordinate, targetCoordinates: List<ScreenCoordinate>) = targetCoordinates
                .minBy { point.distanceTo(it)  }


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