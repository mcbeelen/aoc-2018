package util.space

import arrow.core.Option
import arrow.core.extensions.list.monadFilter.filterMap
import util.space.Sector.*
import kotlin.math.abs

val ORIGIN = Point(0, 0, 0)

data class Point(val x: Int, val y: Int, val z: Int) {
    fun distanceTo(point: Point) = abs(x - point.x) + abs(y - point.y) + abs(z - point.z)
    fun zoomOut(factor: Int) = Point(x / factor, y / factor, z / factor)

    override fun toString(): String {
        return "<$x, $y, $z>"
    }

    fun isAt(x: Int, y: Int, z: Int): Boolean = this.x == x && this.y == y && this.z == z
    fun neighbors(): List<Point> {
        return (-1..1).map { x ->
            (-1..1).map { y ->
                (-1..1).map { z ->
                    if ((x == y) && (x == z) && (x == 0)) {
                        Option.empty()
                    } else
                        Option.just(Point(this.x + x, this.y + y, this.z + z))
                }
            }.flatten()
        }.flatten().filterMap { it }
    }
}


data class Cube(val from: Point, val to: Point) {
    fun size(): Long {
        val dimX = to.x - from.x + 1L
        val dimY = to.y - from.y + 1L
        val dimZ = to.z - from.z + 1L

        if (dimX > 2097151 || dimY > 2097151 || dimZ > 2097151) {
            return Long.MAX_VALUE
        }

        return abs(dimX) * abs(dimY) * abs(dimZ)

    }

    val xRange: IntRange by lazy {
        from.x..to.x
    }
    val yRange: IntRange by lazy {
        from.y..to.y
    }
    val zRange: IntRange by lazy {
        from.z..to.z
    }


    fun shortestDistanceTo(point: Point): Int {
        return distanceX(point) + distanceY(point) + distanceZ(point)
    }

    private fun distanceX(point: Point): Int {
        if (point.x in xRange) {
            return 0
        }
        return if (point.x > to.x) {
            point.x - to.x
        } else {
            from.x - point.x
        }
    }

    private fun distanceY(point: Point): Int {
        if (point.y in yRange) {
            return 0
        }
        return if (point.y > to.y) {
            point.y - to.y
        } else {
            from.y - point.y
        }
    }

    private fun distanceZ(point: Point): Int {
        if (point.z in zRange) {
            return 0
        }
        return if (point.z > to.z) {
            point.z - to.z
        } else {
            from.z - point.z
        }
    }

}


enum class Sector {
    ALPHA_LEFT_FRONT_DOWN,
    BRAVO_LEFT_BACK_DOWN,
    CHARLIE_RIGHT_FRONT_DOWN,
    DELTA_RIGHT_BACK_DOWN,
    ECHO_LEFT_FRONT_UP,
    FOXTROT_LEFT_BACK_UP,
    GAMMA_RIGHT_FRONT_UP,
    HOTEL_RIGHT_BACK_UP
}


fun determineSector(point: Point): Sector {
    return if (point.x < 0) {
        if (point.y < 0) {
            if (point.z < 0) {
                ALPHA_LEFT_FRONT_DOWN
            } else {
                ECHO_LEFT_FRONT_UP
            }
        } else {
            if (point.z < 0) {
                BRAVO_LEFT_BACK_DOWN
            } else {
                FOXTROT_LEFT_BACK_UP
            }
        }
    } else {
        if (point.y < 0) {
            if (point.z < 0) {
                CHARLIE_RIGHT_FRONT_DOWN
            } else {
                GAMMA_RIGHT_FRONT_UP
            }
        } else {
            if (point.z < 0) {
                DELTA_RIGHT_BACK_DOWN
            } else {
                HOTEL_RIGHT_BACK_UP
            }
        }
    }
}


/*
 Left : negative X          Right:  positiveX
 Front: negative Y          Back    : positive Y
 Down:  negative Z          Up:     positive Z
 */

