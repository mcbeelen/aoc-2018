package util.space

import util.space.Sector.*
import kotlin.math.abs

val ORIGIN = Point(0, 0, 0)

data class Point(val x: Int, val y: Int, val z: Int) {
    fun distanceTo(point: Point) = abs(x - point.x) + abs(y - point.y) + abs(z - point.z)
    fun zoomOut(factor: Int) = Point(x / factor, y / factor, z / factor)

    override fun toString(): String {
        return "<$x, $y, $z>"
    }

}


data class Cube(val from: Point, val to: Point) {
    fun size() = (to.x - from.x + 1) * (to.y - from.y + 1) * (to.z - from.z + 1)
}


fun determineSector(point: Point) : Sector {
    return if (point.x < 0) {
        if (point.y < 0) {
            if (point.z < 0) {
                ALPHA
            } else {
                ECHO
            }
        } else {
            if (point.z < 0) {
                BRAVO
            } else {
                FOXTROT
            }
        }
    } else {
        if (point.y < 0) {
            if (point.z < 0) {
                CHARLIE
            } else {
                GAMMA
            }
        } else {
            if (point.z < 0) {
                DELTA
            } else {
                HOTEL
            }
        }
    }
}

enum class Sector {
    ALPHA,
    BRAVO,
    CHARLIE,
    DELTA,
    ECHO,
    FOXTROT,
    GAMMA,
    HOTEL



}
