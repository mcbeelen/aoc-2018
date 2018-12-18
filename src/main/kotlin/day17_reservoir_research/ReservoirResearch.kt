package day17_reservoir_research

import mu.KotlinLogging
import util.collections.Stack
import util.grid.Direction.*
import util.grid.ScreenCoordinate
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

2500 //

 */
const val INSPECTION_FREQUENCY = Int.MIN_VALUE


fun findAllTilesWithWater(foundVeinsOfClay: String): SliceOfLand {

    val sliceOfLand = parseScanResults(foundVeinsOfClay)

    sliceOfLand.exploreToFindAllTilesOfWater()

    return sliceOfLand

}


class SliceOfLand(private val tilesWithClay: Set<ScreenCoordinate>) {

    private val logger = KotlinLogging.logger { }

    private val spring = SpringOfWater(ScreenCoordinate(500, 0))
    private val surveyedLand: MutableMap<ScreenCoordinate, TileOfSoil> = HashMap()

    private val directionsToExplore = Stack<DirectionToExplore>()

    init {
        scheduleFurtherExploration(DirectionToExplore(spring, DOWN))
    }

    fun isThereClayAt(location: ScreenCoordinate) = tilesWithClay.contains(location)
    internal fun isThereClayAt(x: Int, y: Int) = isThereClayAt(ScreenCoordinate(x, y))

    fun isThereWaterAt(location: ScreenCoordinate) = surveyedLand.containsKey(location)
    internal fun isThereWaterAt(x: Int, y: Int) = isThereWaterAt(ScreenCoordinate(x, y))


    fun exploreToFindAllTilesOfWater() {

        while (directionsToExplore.isNotEmpty()) {
            explore(directionsToExplore.pop())
            allowInspection()
        }
    }


    private fun explore(directionToExplore: DirectionToExplore) {
        val origin = directionToExplore.from
        if (isOverflowing(origin)) {
            directionToExplore.markAsOverflowing()
        } else {
            performActualExploration(directionToExplore)
        }
    }

    private fun performActualExploration(directionToExplore: DirectionToExplore) {

        val origin = directionToExplore.from
        val nextLocation = directionToExplore.nextLocation()

        if (isThereWaterAt(nextLocation)) {
            handleRunningIntoTileWithWater(directionToExplore, origin, nextLocation)
        } else {
            if (isThereClayAt(nextLocation)) {
                handleRunningIntoSomeClay(directionToExplore, origin)
            } else {
                handleDiscoverOfNewTileWithWater(directionToExplore)
            }
        }
    }

    private fun handleRunningIntoSomeClay(directionToExplore: DirectionToExplore, origin: Tile) {
        directionToExplore.markAsBlocked()

        when (directionToExplore.direction) {
            DOWN -> {
                if (!surveyedLand.containsKey(origin.location.next(LEFT))) {
                    scheduleFurtherExploration(leftwardsFrom(origin))
                }

                if (!surveyedLand.containsKey(origin.location.next(RIGHT))) {
                    scheduleFurtherExploration(rightwardsFrom(origin))
                }
            }
            LEFT, RIGHT -> applyBackPressure((origin as TileOfSoil).linkToSource)
        }
    }

    private fun handleRunningIntoTileWithWater(currentExploration: DirectionToExplore, origin: Tile, nextLocation: ScreenCoordinate) {
        if (currentExploration.direction == DOWN) {
            if (origin.linkToSource.direction == DOWN) {
                // We are splashing into existing flow of water
                val joiningTile = surveyedLand.getValue(nextLocation)
                if (joiningTile.isOverflowing()) {
                    currentExploration.markAsOverflowing()
                } else {

                    //TODO("We could be splashing into the blocked part of a prefilled bucket")
                    if (joiningTile.isOnBlockSideOfOverflowingBucket()) {
                        joiningTile.markAsOverflowingTowards(UP)
                        currentExploration.markAsOverflowing()
                    } else {
                        // Communicating Vessels: We need to continue to fill the bucket
                        scheduleFurtherExploration(leftwardsFrom(origin), rightwardsFrom(origin))

                    }

                }

            } else {
                // We are filling up some bucket, so continue in same direction
                scheduleFurtherExploration(DirectionToExplore(origin, origin.linkToSource.direction))
            }
        } else {
            // We are running into a wave or a waterfall
            val joiningTile = surveyedLand.getValue(nextLocation)
            if (joiningTile.isOverflowing()) {
                currentExploration.markAsOverflowing()
            } else {
                logger.warn("Figure out how to handle flowing into into ${joiningTile}")
                logger.warn("Situation: exploring from ${currentExploration.from} in ${currentExploration.direction}")
                logger.info("Running into a tile with source ${joiningTile.linkToSource.from} with ${joiningTile.linkToSource.state}")
                plotReservoir(this)
                println("Marking section as blocking, since it ran sideways into water ")
                applyBackPressure(currentExploration.from.linkToSource)
                // scheduleFurtherExploration(DirectionToExplore(surveyedLand.getValue(nextLocation), origin.linkToSource.direction))

            }


        }
    }


    fun drainAllExcessWater() {
        surveyedLand
                .filter { it.value.isOverflowing() }
                .forEach { surveyedLand.remove(it.key)}


        surveyedLand
                .filter { hasAnOpeningOnTheSide(it.value) }
                .forEach { surveyedLand.remove(it.key)}


    }

    private fun hasAnOpeningOnTheSide(tile: TileOfSoil): Boolean {

        return (! isClosedToTheRight(tile)) || (! isClosedToTheLeft(tile))



    }

    private fun isClosedToTheLeft(tile: TileOfSoil): Boolean {
        var x = tile.location.left
        val y = tile.location.top
        while (isThereWaterAt(x, y)) {
            x--
        }
        return isThereClayAt(x, y)
    }


    private fun isClosedToTheRight(tile: TileOfSoil): Boolean {
        var x = tile.location.left
        val y = tile.location.top
        while (isThereWaterAt(x, y)) {
            x++
        }
        return isThereClayAt(x, y)
    }


    private fun scheduleFurtherExploration(vararg newDirections: DirectionToExplore) {
        newDirections.forEach {
            directionsToExplore.push(it)
        }


    }

    private fun applyBackPressure(linkToSource: Link) {

        val direction = linkToSource.direction

        val forkingTile = if (direction == DOWN) {
            linkToSource.to
        } else {
            markAllAsBlocked(linkToSource)
        }

        if (forkingTile.canNotFlowSideways()) {
            val upstreamTile = forkingTile.linkToSource.from
            if (!surveyedLand.containsKey(upstreamTile.location.next(LEFT))) {
                scheduleFurtherExploration(leftwardsFrom(upstreamTile))
            }
            if (!surveyedLand.containsKey(upstreamTile.location.next(RIGHT))) {
                scheduleFurtherExploration(rightwardsFrom(upstreamTile))
            }

        }

    }

    private tailrec fun markAllAsBlocked(linkToSource: Link): Tile {
        val direction = linkToSource.direction
        linkToSource.from.markAsBlockedFrom(direction)
        if (direction != linkToSource.from.linkToSource.direction) {
            return linkToSource.from
        }
        return markAllAsBlocked(linkToSource.from.linkToSource)


    }

    private fun isOverflowing(from: Tile) = from.location.top == maxY

    private fun handleDiscoverOfNewTileWithWater(directionToExplore: DirectionToExplore) {

        val from = directionToExplore.from
        val location = directionToExplore.nextLocation()
        val to = TileOfSoil(location, from, directionToExplore.direction)

        surveyedLand[location] = to

        scheduleFurtherExploration(downwardsFrom(to))

    }


    var counter = 0

    private fun allowInspection() {
        counter++

        if (INSPECTION_FREQUENCY != Int.MIN_VALUE && counter.rem(INSPECTION_FREQUENCY) == 0) {
            plotReservoir(this)
        }
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

    fun tileAt(x: Int, y: Int) = surveyedLand.getValue(ScreenCoordinate(x, y))






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




class ReservoirResearchSolver {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {


            val sliceOfLand = findAllTilesWithWater(CLAY_SCAN_RESULTS)
            println("Initially water will be available at ${sliceOfLand.countTilesWithWater()} locations")


            sliceOfLand.drainAllExcessWater()

            plotReservoir(sliceOfLand)

            println("Eventually water will remain at ${sliceOfLand.countTilesWithWater()} locations")




        }
    }
}