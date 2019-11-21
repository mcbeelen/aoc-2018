package y2018.day23_nanobots_experimental_emergency_teleportation

import util.space.*
import util.space.Sector.*
import kotlin.math.abs
import kotlin.ranges.IntRange.Companion.EMPTY
import kotlin.system.measureTimeMillis


data class Nanobot(val point: Point, val range: Int) {
    val sector: Sector by lazy { determineSector(point) }

    val distanceToOrigin: Int by lazy { point.distanceTo(ORIGIN) }

    val distanceToBorder: Int by lazy {
        distanceToOrigin - range
    }
    val reachBeyondOrigin: Int by lazy { abs(range - distanceToOrigin) }


    fun zoomOut(factor: Int) = Nanobot(point.zoomOut(factor), range / factor)

    fun hasOverlappingRangeWith(nanobot: Nanobot) = this.point.distanceTo(nanobot.point) <= range + nanobot.range

    fun isPointWithinRange(point: Point) = this.point.distanceTo(point) <= range

    fun rangeInSectorHotel(): IntRange {
        return when (sector) {
            ALPHA_LEFT_FRONT_DOWN -> {
                if (abs(point.x + point.y + point.z) >= range) {
                    EMPTY
                } else {
                    0 .. range + point.x + point.y + point.z
                }
            }
            BRAVO_LEFT_BACK_DOWN -> {
                if (abs(point.x + point.z) >= range) {
                    EMPTY
                } else {
                    val reach = point.x + point.z + range
                    return point.y - reach .. point.y + reach
                }
            }
            CHARLIE_RIGHT_FRONT_DOWN -> {
                if (abs(point.y + point.z) >= range) {
                    EMPTY
                } else {
                    val reach = range + point.y + point.z
                    return point.x - reach .. point.x + reach
                }
            }
            DELTA_RIGHT_BACK_DOWN -> {
                if (abs(point.z) >= range) {
                    EMPTY
                } else {
                    val reach = range + point.z
                    return point.x + point.y - reach .. point.x + point.y + reach
                }
            }
            ECHO_LEFT_FRONT_UP -> {
                if (abs(point.x + point.y) >= range) {
                    EMPTY
                } else {
                    val reach = range + point.x + point.y
                    return point.z - reach .. point.z + reach
                }
            }
            FOXTROT_LEFT_BACK_UP -> {
                if (abs(point.x) >= range) {
                    EMPTY
                } else {
                    val reach = range + point.x
                    return point.y + point.z - reach .. point.y + point.z + reach
                }
            }
            GAMMA_RIGHT_FRONT_UP -> {
                if (abs(point.y) >= range) {
                    EMPTY
                } else {
                    val reach = range + point.y
                    return point.x + point.z - reach .. point.x + point.z + reach
                }
            }
            HOTEL_RIGHT_BACK_UP -> {
                val upperBound = point.x + point.y + point.z + range
                if (point.x + point.y + point.z >= range) {
                    return point.x + point.y + point.z - range .. point.x + point.y + point.z + range
                } else {
                    return 0 .. point.x + point.y + point.z + range
                }

            }
        }

    }

    fun reaches(cube: Cube): Boolean {
        return cube.shortestDistanceTo(point) <= this.range



    }

}

fun isBotWithinRangeOf(someBot: Nanobot, nanobot: Nanobot) = isPointWithinRangeOf(someBot.point, nanobot)

fun isPointWithinRangeOf(point: Point, nanobot: Nanobot) = nanobot.isPointWithinRange(point)


fun parseNanobot(nanoInput: String): Nanobot {

    val coordinates = nanoInput
            .substringAfter("<").substringBefore(">").split(",")
            .map { it.toInt() }
    val point = Point(coordinates[0], coordinates[1], coordinates[2])

    val range = nanoInput.substringAfter("r=").toInt()

    return Nanobot(point, range)
}


fun rangeOf(values: Collection<Int>) = values.minBy { it }!! .. values.maxBy { it }!!


inline class Quantity(val value: Int)


class NanobotPartOneSolver {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val time = measureTimeMillis {
                val formation = parseFormation(NANOBOTS_FORMATION)
                val botWithBiggestRange = formation.bots.maxBy { it.range }!!
                val numberOfBotsInRange = formation.bots.filter { isBotWithinRangeOf(it, botWithBiggestRange) }
                        .count()

                println("Day 23 Part ONE: ${numberOfBotsInRange}")

            }
            println("Done in ${time}ms")

        }
    }
}