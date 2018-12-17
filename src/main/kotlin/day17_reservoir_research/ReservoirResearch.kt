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


fun findTilesWithWater(foundVeinsOfClay: String): Collection<Tile> {

    val sliceOfLand = parseScanResults(foundVeinsOfClay)

    val locationOfSpring = ScreenCoordinate(500, 0)
    val tileWithSpring = Tile(locationOfSpring, locationOfSpring)

    val sliceWithExtraTile = sliceOfLand.withAdditionalTile(tileWithSpring)


    return followTheFlowOfTheWaterFrom(sliceWithExtraTile).tilesWithWater

}

tailrec fun followTheFlowOfTheWaterFrom(sliceOfLand: SliceOfLand): SliceOfLand {

    if (sliceOfLand.allTilesHaveBeenExplored()) {
        return sliceOfLand
    }

    return followTheFlowOfTheWaterFrom(exploreTile(sliceOfLand.findTileToExplore(), sliceOfLand))
}

fun exploreTile(tileWithWater: Tile, sliceOfLand: SliceOfLand): SliceOfLand {

    if (sliceOfLand.canWaterFlowDownwardsFrom(tileWithWater)) {
        val newTileWithWater = Tile(tileWithWater.location.next(DOWN), tileWithWater.location)
        return sliceOfLand.withNewTileWithWater(newTileWithWater, tileWithWater.withDownExplored())
    } else {
        val exploredTile = tileWithWater.copy(directionsToExplore = emptyList())
        return sliceOfLand.withExploredTile(exploredTile)
    }


}


data class Tile(val location: ScreenCoordinate,
                val source: ScreenCoordinate,
                val directionsToExplore: List<Direction> = listOf(DOWN)) : Comparable<Tile> {

    override fun compareTo(other: Tile) = location.compareTo(other.location)

    fun withDownExplored() = copy(directionsToExplore = directionsToExplore.minus(DOWN))

}


data class SliceOfLand(private val tilesWithClay: Set<ScreenCoordinate>,
                       val tilesWithWater: SortedSet<Tile> = TreeSet()) {

    fun isThereClayAt(x: Int, y: Int) = tilesWithClay.contains(ScreenCoordinate(x, y))

    fun canWaterFlowDownwardsFrom(tile: Tile) = !tilesWithClay.contains(tile.location.next(DOWN))


    fun findTileToExplore() = tilesWithWater.first { it.directionsToExplore.isNotEmpty() }

    fun allTilesHaveBeenExplored() = tilesWithWater.all { it.directionsToExplore.isEmpty() }

    fun withAdditionalTile(additionalTile: Tile)= copy(tilesWithWater = tilesWithWater
            .plus(additionalTile)
            .toSortedSet())

    fun withNewTileWithWater(foundTile: Tile, exploredTile: Tile) =
            withAdditionalTile(foundTile)
                    .withExploredTile(exploredTile)


    fun withExploredTile(exploredTile: Tile) = copy(tilesWithWater = tilesWithWater
            .minus(findTileToExplore())
            .plus(exploredTile)
            .toSortedSet())




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
