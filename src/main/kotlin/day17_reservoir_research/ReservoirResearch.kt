package day17_reservoir_research

import util.grid.Direction
import util.grid.Direction.*
import util.grid.ScreenCoordinate
import java.util.*

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

    val locationOfSpring = ScreenCoordinate(500, 0)
    val tileWithSpring = Tile(locationOfSpring, null)

    val sliceWithExtraTile = sliceOfLand.withAdditionalTile(tileWithSpring)


    return followTheFlowOfTheWaterFrom(sliceWithExtraTile)

}

tailrec fun followTheFlowOfTheWaterFrom(sliceOfLand: SliceOfLand): SliceOfLand {

    if (sliceOfLand.allTilesHaveBeenExplored()) {
        return sliceOfLand
    }

    return followTheFlowOfTheWaterFrom(exploreTile(sliceOfLand.findTileToExplore(), sliceOfLand))
}

fun exploreTile(tileToExplore: Tile, sliceOfLand: SliceOfLand): SliceOfLand {

    println("Going to explore tile at ${tileToExplore.location} in ${tileToExplore.directionsToExplore}")

    if (sliceOfLand.isTileAtTheFurthestPointDownward(tileToExplore)) {
        return sliceOfLand.withExploredTile(tileToExplore.withExplored(DOWN))
    }

    return when {
        tileToExplore.directionsToExplore.contains(DOWN) -> exploreDownwards(sliceOfLand, tileToExplore)
        tileToExplore.directionsToExplore.contains(LEFT) -> exploreLeftwards(sliceOfLand, tileToExplore)
        tileToExplore.directionsToExplore.contains(RIGHT) -> exploreRightwards(sliceOfLand, tileToExplore)
        else -> throw IllegalArgumentException("tileToExplore does not have a direction to explore")
    }


}


private fun exploreDownwards(sliceOfLand: SliceOfLand, tileToExplore: Tile): SliceOfLand {

    // Do not try to explore tile, which we already explored
    if (sliceOfLand.isThereWaterAt(tileToExplore.location.next(DOWN))) {
        if (tileToExplore.hasSourceOnTheRight()) {
            return sliceOfLand.withExploredTile(tileToExplore.withNeedToExploreLeftWards().withExplored(DOWN))
        } else if (tileToExplore.hasSourceOnTheLeft()) {
            return sliceOfLand.withExploredTile(tileToExplore.withNeedToExploreRightwards().withExplored(DOWN))
        }
    }

    if (sliceOfLand.canWaterFlowDownwardsFrom(tileToExplore)) {
        val newTileWithWater = Tile(tileToExplore.location.next(DOWN), tileToExplore)
        return sliceOfLand.withNewTileWithWater(newTileWithWater, tileToExplore.withExplored(DOWN))
    } else {
        if (tileToExplore.hasSourceFromAbove()) {
            return sliceOfLand.withExploredTile(tileToExplore.withNeedToExploreSideways())
        } else if (tileToExplore.hasSourceOnTheRight()) {
            return sliceOfLand.withExploredTile(tileToExplore.withNeedToExploreLeftWards().withExplored(DOWN))
        } else if (tileToExplore.hasSourceOnTheLeft()) {
            return sliceOfLand.withExploredTile(tileToExplore.withNeedToExploreRightwards().withExplored(DOWN))
        }
    }

    throw IllegalStateException("We should not end up on this spot!")
}


fun exploreLeftwards(sliceOfLand: SliceOfLand, tileToExplore: Tile): SliceOfLand {
    if (sliceOfLand.canWaterFlowFrom(tileToExplore, LEFT)) {
        val newTileWithWater = Tile(tileToExplore.location.next(LEFT), tileToExplore)
        return sliceOfLand.withNewTileWithWater(newTileWithWater, tileToExplore.withExplored(LEFT))
    } else {
        return sliceOfLand.withExploredTile(tileToExplore.withExplored(LEFT))
    }
}

fun exploreRightwards(sliceOfLand: SliceOfLand, tileToExplore: Tile): SliceOfLand {

    // Do not try to explore tile, which we already explored
    if (sliceOfLand.isThereWaterAt(tileToExplore.location.next(RIGHT))) {
        return sliceOfLand.withExploredTile(tileToExplore.withExplored(RIGHT))
    }

    if (sliceOfLand.canWaterFlowFrom(tileToExplore, RIGHT)) {
        val newTileWithWater = Tile(tileToExplore.location.next(RIGHT), tileToExplore)
        return sliceOfLand.withNewTileWithWater(newTileWithWater, tileToExplore.withExplored(RIGHT))
    } else {
        return sliceOfLand.withExploredTile(tileToExplore.withExplored(RIGHT))
                .withStartingToFillTheReservoir(tileToExplore)
    }
}


data class Tile(val location: ScreenCoordinate,
                val source: Tile?,
                val directionsToExplore: List<Direction> = listOf(DOWN)) : Comparable<Tile> {

    override fun compareTo(other: Tile) = location.compareTo(other.location)

    fun withExplored(direction: Direction) = copy(directionsToExplore = directionsToExplore.minus(direction))

    fun withNeedToExploreSideways() = copy(directionsToExplore = listOf(LEFT, RIGHT))
    fun withNeedToExploreLeftWards() = copy(directionsToExplore = listOf(LEFT))
    fun withNeedToExploreRightwards() = copy(directionsToExplore = listOf(RIGHT))


    fun hasSourceFromAbove() = source == null || source.location.isAbove(location)
    fun hasSourceOnTheRight() = source!!.location.isToTheRightOf(location)
    fun hasSourceOnTheLeft() = source!!.location.isToTheLeftOf(location)

}


data class SliceOfLand(private val tilesWithClay: Set<ScreenCoordinate>,
                       val tilesWithWater: SortedSet<Tile> = TreeSet()) {


    val minX: Int by lazy { tilesWithClay.map { it.left }.min() ?: Int.MIN_VALUE }
    val maxX: Int by lazy { tilesWithClay.map { it.left }.max() ?: Int.MIN_VALUE }

    val minY: Int by lazy { tilesWithClay.map { it.top }.min() ?: Int.MIN_VALUE }
    val maxY: Int by lazy { tilesWithClay.map { it.top }.max() ?: Int.MIN_VALUE }


    fun isThereClayAt(x: Int, y: Int) = tilesWithClay.contains(ScreenCoordinate(x, y))

    fun isThereWaterAt(x: Int, y: Int) = tilesWithWater.any { it.location.isAt(x, y) }
    fun isThereWaterAt(location: ScreenCoordinate) = tilesWithWater.any { it.location == location }


    fun canWaterFlowDownwardsFrom(tile: Tile) = !tilesWithClay.contains(tile.location.next(DOWN))

    fun canWaterFlowFrom(from: Tile, direction: Direction): Boolean {
        val nextLocation = from.location.next(direction)
        return !tilesWithClay.contains(nextLocation)
    }


    fun findTileToExplore() = tilesWithWater.first { it.directionsToExplore.isNotEmpty() }

    fun allTilesHaveBeenExplored() = tilesWithWater.all { it.directionsToExplore.isEmpty() }

    fun withAdditionalTile(additionalTile: Tile) = copy(tilesWithWater = tilesWithWater
            .plus(additionalTile)
            .toSortedSet())

    fun withNewTileWithWater(foundTile: Tile, exploredTile: Tile) =
            withAdditionalTile(foundTile)
                    .withExploredTile(exploredTile)


    fun withExploredTile(exploredTile: Tile): SliceOfLand {
        val exploredTileInPreviousState = tilesWithWater.first { it.location == exploredTile.location }
        return copy(tilesWithWater = tilesWithWater
                .minus(exploredTileInPreviousState)
                .plus(exploredTile)
                .toSortedSet())
    }

    fun isTileAtTheFurthestPointDownward(tile: Tile) = tile.location.top == maxY
    fun withStartingToFillTheReservoir(tileToExplore: Tile): SliceOfLand {

        val upstreamTile = findUpstreamTileToExplore(tileToExplore).withNeedToExploreSideways()
        return withExploredTile(upstreamTile)

    }

    private tailrec fun findUpstreamTileToExplore(tileToExplore: Tile): Tile {
        if (tileToExplore.source!!.location.isAbove(tileToExplore.location)) {
            return tileToExplore.source
        }
        return findUpstreamTileToExplore(tileToExplore.source)

    }

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
