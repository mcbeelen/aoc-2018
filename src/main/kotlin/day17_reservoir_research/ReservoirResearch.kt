package day17_reservoir_research

import util.collections.Queue
import util.grid.Direction.*
import util.grid.ScreenCoordinate
import java.util.*
import kotlin.collections.HashMap

/**
How many tiles can be reached by the water?
To prevent counting forever,
ignore tiles with a y coordinate smaller than the smallest y coordinate in your scan data
or larger than the largest one.

Any x coordinate is valid.

In this example, the lowest y coordinate given is 1, and the highest is 13,
causing the water spring (in row 0) and the water falling off the bottom of the render
(in rows 14 through infinity)to be ignored.

So, in the example above,
counting both water at rest (~)
and other sand tiles the water can hypothetically reach (|),
the total number of tiles the water can reach is 57.

How many tiles can the water reach within the range of y values in your scan?

 */


fun findAllTilesWithWater(foundVeinsOfClay: String): SliceOfLand {

    val sliceOfLand = parseScanResults(foundVeinsOfClay)

    sliceOfLand.exploreToFindAllTilesOfWater()

    return sliceOfLand

}


class SliceOfLand(private val tilesWithClay: Set<ScreenCoordinate>) {

    private val spring = SpringOfWater(ScreenCoordinate(500, 0))
    private val surveyedLand: MutableMap<ScreenCoordinate, TileOfSoil> = HashMap()

    private val directionsToExplore = Queue<DirectionToExplore>()

    init {
        directionsToExplore.enqueue(DirectionToExplore(spring, DOWN))
    }


    fun isThereClayAt(location: ScreenCoordinate) = tilesWithClay.contains(location)
    internal fun isThereClayAt(x: Int, y: Int) = isThereClayAt(ScreenCoordinate(x, y))

    fun isThereWaterAt(location: ScreenCoordinate) = surveyedLand.containsKey(location)
    internal fun isThereWaterAt(x: Int, y: Int) = isThereWaterAt(ScreenCoordinate(x, y))


    fun exploreToFindAllTilesOfWater() {
        while (directionsToExplore.isNotEmpty()) {
            explore(directionsToExplore.dequeue())
        }
    }

    private fun explore(directionToExplore: DirectionToExplore) {

        println("Exploring from ${directionToExplore.from}  ${directionToExplore.direction}")

        if (isOverflowing(directionToExplore.from)) {
            directionToExplore.markAsOverflowing()
        } else {
            val nextLocation = directionToExplore.nextLocation()

            if (isThereClayAt(nextLocation)) {
                directionToExplore.markAsBlocked()
                when (directionToExplore.direction) {
                    DOWN -> {
                        directionsToExplore.enqueue(leftwardsFrom(directionToExplore.from))
                        directionsToExplore.enqueue(rightwardsFrom(directionToExplore.from))
                    }
                    RIGHT -> println("Stop exploration at ${directionToExplore.from}")
                    LEFT -> println("Stop exploration at ${directionToExplore.from}")
                }
            } else {
                reachedNewLocation(directionToExplore)
            }
        }


    }

    private fun isOverflowing(from: Tile) = from.location.top == maxY

    private fun reachedNewLocation(directionToExplore: DirectionToExplore) {
        val from = directionToExplore.from
        val location = directionToExplore.nextLocation()
        val to = TileOfSoil(location, from, directionToExplore.direction)


        surveyedLand[location] = to
        println("Reached from ${to.location} from ${from.location} heading ${directionToExplore.direction}")

        directionsToExplore.enqueue(downwardsFrom(to))

    }


    val minX: Int by lazy { tilesWithClay.map { it.left }.min() ?: Int.MIN_VALUE }
    val maxX: Int by lazy { tilesWithClay.map { it.left }.max() ?: Int.MIN_VALUE }

    val minY: Int by lazy { tilesWithClay.map { it.top }.min() ?: Int.MIN_VALUE }
    val maxY: Int by lazy { tilesWithClay.map { it.top }.max() ?: Int.MIN_VALUE }

    fun countTilesWithWater(): Int {

        var counter = 0

        for (x in minX - 1..maxX + 1) {
            for (y in minY..maxY) {
                if (isThereWaterAt(x, y)) {
                    counter++
                }
            }
        }

        return counter

    }


}


internal fun parseScanResults(foundVeinsOfClay: String): SliceOfLand {

    val tilesWithClay: MutableSet<ScreenCoordinate> = HashSet()

    foundVeinsOfClay.trimIndent().lines().forEach {
        if (it.startsWith('x')) {
            tilesWithClay.addAll(clayInVerticalVein(it))
        } else {
            tilesWithClay.addAll(clayInHorizontalVein(it))
        }
    }

    return SliceOfLand(tilesWithClay)

}

fun clayInVerticalVein(verticalVein: String): Set<ScreenCoordinate> {
    val tilesWithClay: MutableSet<ScreenCoordinate> = HashSet()
    val x: Int = verticalVein.substringAfter("x=").substringBefore(",").toInt()
    val split = verticalVein.substringAfter("y=").split("..")
    val yRange = IntRange(split[0].toInt(), split[1].toInt())

    for (y in yRange) {
        tilesWithClay.add(ScreenCoordinate(x, y))
    }
    return tilesWithClay
}

fun clayInHorizontalVein(horizontalVein: String): Set<ScreenCoordinate> {
    val tilesWithClay: MutableSet<ScreenCoordinate> = HashSet()
    val y: Int = horizontalVein.substringAfter("y=").substringBefore(",").toInt()
    val split = horizontalVein.substringAfter("x=").split("..")
    val xRange = IntRange(split[0].toInt(), split[1].toInt())

    for (x in xRange) {
        tilesWithClay.add(ScreenCoordinate(x, y))
    }
    return tilesWithClay
}
