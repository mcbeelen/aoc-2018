package y2018.day10_stars_align

import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.assertion.assertThat
import org.junit.Test

class StarTest {

    val isAt = Matcher(Star::isAt)


    @Test
    fun itShouldMoveTheExampleCorrectly() {

        val exampleInput = "position=<3, 9> velocity=<1, -2>"

        var star = parseInputToStar(exampleInput)
        assertThat(star, isAt(Position(3, 9)))

        star = star.move()
        assertThat(star, isAt(Position(4, 7)))

        star = star.move()
        assertThat(star, isAt(Position(5, 5)))

        star = star.move()
        assertThat(star, isAt(Position(6, 3)))


    }



}