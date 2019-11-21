package y2018.day06_largest_finite_area

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.lessThan
import org.junit.Assert.assertTrue
import org.junit.Test
import util.grid.ScreenCoordinate
import util.grid.buildSquareContainingAll


class SafestCoordinateFinderTest {


    @Test
    fun actualProblem() {
        assertThat(findSizeOfLargestArea(DAY06_INPUT), lessThan(4439))
    }


    @Test
    fun itShouldFindTheSafestAreaInTheExampleIs17() {
        assertThat(findSizeOfLargestArea(DAY06_EXAMPLE_INPUT), equalTo(17))
    }


    @Test
    fun itShouldFindSafeAreaOf16PointsWithin32OfAllTargets() {
        assertThat(findSizeOfSafeRegion(DAY06_EXAMPLE_INPUT, 32), equalTo(16))
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

        val B = ScreenCoordinate(1, 6)
        val D = ScreenCoordinate(3, 4)
        val E = ScreenCoordinate(5, 5)

        val targetCoordinates = parseInputIntoTargetCoordinates(DAY06_EXAMPLE_INPUT)
        val square = buildSquareContainingAll(targetCoordinates)

        val targetAreas = assignPointsToClosestTarget(square, targetCoordinates)
        assertThat(targetAreas.size, equalTo(targetCoordinates.size))

        targetAreas
                .single { area -> area.targetCoordinate == B }
                .let { assertThat(it.pointClosestToThisTarget.size, equalTo(9)) }


        targetAreas
                .single { area -> area.targetCoordinate == D }
                .let { assertThat(it.pointClosestToThisTarget.size, equalTo(9)) }


        targetAreas
                .single { area -> area.targetCoordinate == E }
                .let { assertThat(it.pointClosestToThisTarget.size, equalTo(17)) }

        assertTrue("3x6 equals to B and E", targetAreas.none { it.pointClosestToThisTarget.contains(ScreenCoordinate(3, 6)) })
        assertTrue("5,1 equal distance to A and C", targetAreas.none { it.pointClosestToThisTarget.contains(ScreenCoordinate(5, 1)) })


        val finiteAreas = targetAreas
                .filter { isFiniteArea(it.pointClosestToThisTarget, square) }

        assertThat(finiteAreas.count(), equalTo(2))

        assertThat(finiteAreas.maxBy { it.pointClosestToThisTarget.size }!!.targetCoordinate, equalTo(E))

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