package util.grid

import com.natpryce.hamkrest.MatchResult
import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test


class ScreenCoordinateTest {

    @Test
    fun compareTo() {
        val first = ScreenCoordinate(1, 1)
        val second = ScreenCoordinate(1, 1)

        assertThat(first.compareTo(second), equalTo(0))

    }



    @Test
    fun `parseXcommaYInput()`() {
        assertThat(parseXcommaY("8, 3"), equalTo(ScreenCoordinate(8, 3)))
    }

    @Test
    fun distanceTo() {
        val first = ScreenCoordinate(1, 1)
        val second = ScreenCoordinate(1, 2)
        val third = ScreenCoordinate(3, 1)
        val fourth = ScreenCoordinate(5, 5)


        assertThat(first.distanceTo(first), equalTo(0))
        assertThat(first.distanceTo(second), equalTo(1))
        assertThat(first.distanceTo(third), equalTo(2))
        assertThat(first.distanceTo(fourth), equalTo(8))

        assertThat(fourth.distanceTo(second), equalTo(7))
        assertThat(fourth.distanceTo(third), equalTo(6))

    }

    @Test
    fun `should support transpose`() {
        val coordinate = at(2, 2)
        val vector = Vector(3, -1)
        val transpose = coordinate.transpose(vector, 3)

        assertThat(transpose, isAt(11, -1))
    }
}


fun isAt(left: Int, top: Int): Matcher<ScreenCoordinate> {
    return object : Matcher<ScreenCoordinate> {
        override fun invoke(actual: ScreenCoordinate): MatchResult {
            return if (actual.isAt(left, top)) {
                MatchResult.Match
            } else {
                MatchResult.Mismatch("was at <${actual.left}, ${actual.top}>")
            }
        }

        override val description: String
            get() {
                return "isAt <$left, $top>"
            }
    }

}
