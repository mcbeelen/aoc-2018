package day10_stars_align

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class SkyTest {


    private val originalSky: Sky
        get() {
            val sky = Sky(listOf(
                    parseInputToStar("position=<3, 9> velocity=<1, -2>"),
                    parseInputToStar("position=< 9,  1> velocity=< 0,  2>")
            ))
            return sky
        }

    @Test
    fun itShouldBeAbleToLocateStars() {

        val sky = originalSky

        assertTrue(sky.hasStarAt(Position(3, 9)))
        assertTrue(sky.hasStarAt(Position(9, 1)))

        assertFalse(sky.hasStarAt(Position(9, 2)))
        assertFalse(sky.hasStarAt(Position(8, 1)))

    }

    @Test
    fun itShouldBeAbleToMoveAllStarsInTheSky() {

        var sky = originalSky

        sky = sky.moveStars()

        assertFalse(sky.hasStarAt(Position(3, 9)))
        assertFalse(sky.hasStarAt(Position(9, 1)))

        assertTrue(sky.hasStarAt(Position(4, 7)))
        assertTrue(sky.hasStarAt(Position(9, 3)))


        sky = sky.moveStars()

        assertFalse(sky.hasStarAt(Position(4, 7)))
        assertFalse(sky.hasStarAt(Position(9, 3)))

        assertTrue(sky.hasStarAt(Position(5, 5)))
        assertTrue(sky.hasStarAt(Position(9, 5)))


    }
}