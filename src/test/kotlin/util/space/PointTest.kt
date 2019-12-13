package util.space

import com.natpryce.hamkrest.MatchResult
import com.natpryce.hamkrest.Matcher
import y2019.day12_moons_of_jupiter.Velocity


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


