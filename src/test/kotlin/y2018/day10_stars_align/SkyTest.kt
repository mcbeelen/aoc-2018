package y2018.day10_stars_align

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
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

    @Test
    fun itShouldBeAbleToDetermineItsDimensions() {

        var sky = originalSky

        assertThat(sky.height().value.first, equalTo(1))
        assertThat(sky.height().value.last, equalTo(9))

        sky = sky.moveStars()

        assertThat(sky.height().value.first, equalTo(3))
        assertThat(sky.height().value.last, equalTo(7))

    }

    @Test
    fun itShouldDetermineThatEightInTheMinimumHeightForTheExample() {

        var sky = buildExampleSky()
        val height = sky.height().value
        assertThat(height.first, equalTo(-4))
        assertThat(height.last, equalTo(11))
        assertThat(sky.height().size(), equalTo(16))

        sky = shirkToMinimumHeight(sky)

        assertThat(sky.height().size(), equalTo(8))


        plotSky(sky)

    }

}



fun buildExampleSky() : Sky {
    return Sky(parseToStars(STAR_ALIGN_EXAMPLE))
}

