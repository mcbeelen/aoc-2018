package util.space

import com.natpryce.hamkrest.MatchResult
import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import y2019.day12_moons_of_jupiter.Velocity


class PointTest {

    @Test
    fun `it should yield 26 neighbours`() {
        val neighbors = ORIGIN.neighbors()
        assertThat(neighbors, hasSize(equalTo(26)))

        assertTrue(neighbors.contains(Point(-1, -1, -1)))
        assertTrue(neighbors.contains(Point(1, 1, 1)))
        assertFalse(neighbors.contains(ORIGIN))
        assertFalse(neighbors.contains(Point(2, 1,1)))
    }
}

fun isAt(x: Int, y: Int, z: Int): Matcher<Point> {
    return object : Matcher<Point> {
        override fun invoke(actual: Point): MatchResult {
            return if (actual.isAt(x, y, z)) {
                MatchResult.Match
            } else {
                MatchResult.Mismatch("was at <${actual.x}, ${actual.y}, ${actual.z}>")
            }
        }

        override val description: String
            get() {
                return "isAt <$x, $y, $z>"
            }
    }

}

fun isCurrently(x: Int, y: Int, z: Int): Matcher<Velocity> {
    return object : Matcher<Velocity> {
        override fun invoke(actual: Velocity): MatchResult {
            return if (actual == Velocity(x, y, z)) {
                MatchResult.Match
            } else {
                MatchResult.Mismatch("currently was <${actual.x}, ${actual.y}, ${actual.z}>")
            }
        }

        override val description: String
            get() {
                return "isAt <$x, $y, $z>"
            }
    }

}


