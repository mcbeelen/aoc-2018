package y2018.day22_node_maze

import y2018.day22_node_maze.Equipment.*
import y2018.day22_node_maze.RegionType.*
import util.grid.ScreenCoordinate
import util.grid.search.Graph
import kotlin.system.measureTimeMillis

class Cave(private val depth: Int, val target: ScreenCoordinate) : Graph<ReachedRegion, ActionInCave>() {


    val caveMap: MutableMap<ScreenCoordinate, Region> = HashMap()

    fun exploreUntil(screenCoordinate: ScreenCoordinate) {
        for (y in 0..screenCoordinate.top) {
            for (x in 0..screenCoordinate.left) {
                caveMap[ScreenCoordinate(x, y)] = exploreRegion(x, y)
            }
        }
    }

    fun exploreUntilTarget() {
        exploreUntil(target)
    }

    private fun exploreRegion(location: ScreenCoordinate) = exploreRegion(location.left, location.top)

    private fun exploreRegion(x: Int, y: Int): Region {
        val geologicIndex = calculateGeolocicIndex(x, y)
        val erosionLevel = calculateErosionLevel(geologicIndex)
        val regionType = determineType(erosionLevel)
        return Region(ScreenCoordinate(x, y), geologicIndex, erosionLevel, regionType)
    }


    fun calculateGeolocicIndex(x: Int, y: Int): Int = when {
        x == 0 && y == 0 -> 0

        target.isAt(x, y) -> 0

        y == 0 -> 16807 * x
        x == 0 -> 48271 * y

        else -> regionAt(ScreenCoordinate(x - 1, y)).erosionLevel * regionAt(ScreenCoordinate(x, y - 1)).erosionLevel
    }

    fun calculateErosionLevel(geologicIndex: Int) = (geologicIndex + depth).rem(20183)

    fun determineType(erosionLevel: Int): RegionType = when (erosionLevel.rem(3)) {
        0 -> ROCKY
        1 -> WET
        else -> NARROW

    }

    fun calculateRiskLevel(): Int {
        var riskLevel = 0

        for (y in 0..target.top) {
            for (x in 0..target.left) {
                riskLevel += caveMap.getValue(ScreenCoordinate(x, y)).regionType.risk
            }
        }

        return riskLevel
    }




    override fun findNeighbours(vertex: ReachedRegion): List<ActionInCave> = possibleActions(vertex)

    fun possibleActions(reachedRegion: ReachedRegion): List<ActionInCave> {
        return switchTools(reachedRegion) + movesToAdjacentRegions(reachedRegion)
    }

    private fun movesToAdjacentRegions(reachedRegion: ReachedRegion) : List<ActionInCave> {

        val currentLocation = reachedRegion.region.location

        val adjacentLocations = getAdjacentLocations(currentLocation)
        return adjacentLocations
                .filter { it.top >= 0 && it.left >= 0 }
                .filter { regionAt(it).regionType.requiredEquipment.contains(reachedRegion.equipedWith)}
                .map { ActionInCave(reachedRegion, reachedRegion.movedTo(caveMap.getValue(it)), 1) }


    }

    private fun getAdjacentLocations(location: ScreenCoordinate) = listOf(
            location.copy(top = location.top + 1),
            location.copy(top = location.top - 1),
            location.copy(left = location.left + 1),
            location.copy(left = location.left + 1)

    )
    private fun regionAt(location: ScreenCoordinate): Region {
        return caveMap.getOrPut(location) { exploreRegion(location) }

    }

    private fun switchTools(reachedRegion: ReachedRegion): List<ActionInCave> =
        when(reachedRegion.equipedWith) {
            TORCH -> listOf(
                    ActionInCave(reachedRegion, reachedRegion.withEquipment(CLIMBING_GEAR), 7),
                    ActionInCave(reachedRegion, reachedRegion.withEquipment(NEITHER), 7)
                    )
            CLIMBING_GEAR -> listOf(
                    ActionInCave(reachedRegion, reachedRegion.withEquipment(TORCH), 7),
                    ActionInCave(reachedRegion, reachedRegion.withEquipment(NEITHER), 7)
            )
            NEITHER -> listOf(
                    ActionInCave(reachedRegion, reachedRegion.withEquipment(CLIMBING_GEAR), 7),
                    ActionInCave(reachedRegion, reachedRegion.withEquipment(TORCH), 7)
            )
        }
    }


data class Region(val location: ScreenCoordinate, val geologicIndex: Int = 0, val erosionLevel: Int = 0, val regionType: RegionType)

enum class RegionType(val risk: Int, val requiredEquipment: Set<Equipment>) {
    ROCKY(0, setOf(CLIMBING_GEAR, TORCH)),
    WET(1, setOf(CLIMBING_GEAR, NEITHER)),
    NARROW(2, setOf(TORCH, NEITHER))
}

enum class Equipment {
    TORCH,
    CLIMBING_GEAR,
    NEITHER
}


class ProblemSolverDay22 {

    companion object {


        @JvmStatic
        fun main(args: Array<String>) {

            val timeInMillis = measureTimeMillis {
                val actualCave = Cave(10647, ScreenCoordinate(7, 770))
                actualCave.exploreUntilTarget()

                println("The total risk level for my cave is: ${actualCave.calculateRiskLevel()}")

            }

            println("Solved part one in ${timeInMillis}ms")


        }
    }
}