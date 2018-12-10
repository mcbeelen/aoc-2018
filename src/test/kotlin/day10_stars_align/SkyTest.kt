package day10_stars_align

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class SkyTest {


    @Test
    fun itShouldBeAbleToLocateStars() {

        val sky = Sky(listOf(
                parseInputToStar("position=<3, 9> velocity=<1, -2>"),
                parseInputToStar("position=< 9,  1> velocity=< 0,  2>")
        ))

        assertTrue(sky.hasStarAt(Position(3, 9)))
        assertTrue(sky.hasStarAt(Position(9, 1)))

        assertFalse(sky.hasStarAt(Position(9, 2)))
        assertFalse(sky.hasStarAt(Position(8, 1)))

    }
}