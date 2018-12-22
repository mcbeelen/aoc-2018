package day22_node_maze

import day22_node_maze.RegionType.*
import util.grid.ScreenCoordinate
import kotlin.system.measureTimeMillis

class Cave(private val depth: Int, private val target: ScreenCoordinate) {

    val caveMap: MutableMap<ScreenCoordinate, Region> = HashMap()

    fun exploreUntilTarget() {
        for (y in 0..target.top) {
            for (x in 0..target.left) {


                val geologicIndex = calculateGeolocicIndex(x, y)
                val erosionLevel = calculateErosionLevel(geologicIndex)
                val regionType = determineType(erosionLevel)
                val region = Region(geologicIndex, erosionLevel, regionType)
                caveMap[ScreenCoordinate(x, y)] = region

            }
        }
    }


    fun calculateGeolocicIndex(x: Int, y: Int): Int = when {
        x == 0 && y == 0 -> 0

        target.isAt(x, y) -> 0

        y == 0 -> 16807 * x
        x == 0 -> 48271 * y

        else -> caveMap.getValue(ScreenCoordinate(x - 1, y)).erosionLevel * caveMap.getValue(ScreenCoordinate(x, y - 1)).erosionLevel
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

}


class Region(val geologicIndex: Int = 0, val erosionLevel: Int = 0, val regionType: RegionType)

enum class RegionType(val risk: Int) {
    ROCKY(0),
    WET(1),
    NARROW(2)
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