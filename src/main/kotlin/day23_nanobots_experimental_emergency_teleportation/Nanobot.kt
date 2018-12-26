package day23_nanobots_experimental_emergency_teleportation

import util.space.ORIGIN
import util.space.Point
import util.space.Sector
import util.space.determineSector
import kotlin.math.abs
import kotlin.system.measureTimeMillis


data class Nanobot(val point: Point, val range: Int) {
    val sector: Sector by lazy { determineSector(point)}

    val distanceToOrigin : Int by lazy { point.distanceTo(ORIGIN) }

    val distanceToBorder : Int by lazy {
        distanceToOrigin - range
    }
    val reachBeyondOrigin: Int by lazy { abs(range - distanceToOrigin) }


    fun zoomOut(factor: Int) = Nanobot(point.zoomOut(factor), range / factor)

    fun hasOverlappingRangeWith(nanobot: Nanobot) = this.point.distanceTo(nanobot.point) <= range + nanobot.range

    fun isPointWithinRange(point: Point) = this.point.distanceTo(point) <= range
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


fun rangeOf(values: Collection<Int>) = values.minBy { it }!!..values.maxBy { it }!!


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