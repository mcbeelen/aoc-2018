package y2020.d17_conways_cubes

import arrow.core.Option
import arrow.core.extensions.list.monadFilter.filterMap
import util.hyperspace.Point4D
import util.space.Point


fun conwaysGameOfLiveIn3D(initialCubeConfiguration: String): Int {
    var activePoints = parseInitialConfigurationToPoints(initialCubeConfiguration)

    for (cycle in 0 .. 5) {
        val activePointsInNextCycle = HashSet<Point>().toMutableSet()
        for (x in -6 .. 15) {
            for (y in -6 .. 15) {
                for (z in -6 .. 15) {
                    val currentPoint = Point(x, y, z)
                    val count = currentPoint.neighbors().filter { activePoints.contains(it) }.count()
                    if (activePoints.contains(currentPoint) && (count == 2 || count == 3)) {
                        activePointsInNextCycle.add(currentPoint)
                    } else if (count == 3 ){
                        activePointsInNextCycle.add(currentPoint)
                    }
                }
            }
        }
        activePoints = activePointsInNextCycle
    }

    return activePoints.count()
}

private fun parseInitialConfigurationToPoints(initialCubeConfiguration: String): Set<Point> {
    val activePoints = initialCubeConfiguration.lines().mapIndexed { rowIndex, line ->
        line.mapIndexed { columnIndex, letter ->
            if (letter == '#') Option.just(
                Point(
                    rowIndex,
                    columnIndex,
                    0
                )
            ) else Option.empty()
        }
    }.flatten().filterMap { it }.toSet()
    return activePoints
}

fun conwaysGameOfLiveIn4D(initialCubeConfiguration: String): Int {
    var activePoints = parseInitialConfigurationToPointsIn4D(initialCubeConfiguration)

    for (cycle in 0 .. 5) {
        val activePointsInNextCycle = HashSet<Point4D>().toMutableSet()
        for (x in -6 .. 15) {
            for (y in -6 .. 15) {
                for (z in -6 .. 15) {
                    for (w in -6..15) {
                        val currentPoint = Point4D(x, y, z, w)
                        val count = currentPoint.neighbors().filter { activePoints.contains(it) }.count()
                        if (activePoints.contains(currentPoint) && (count == 2 || count == 3)) {
                            activePointsInNextCycle.add(currentPoint)
                        } else if (count == 3) {
                            activePointsInNextCycle.add(currentPoint)
                        }
                    }
                }
            }
        }
        activePoints = activePointsInNextCycle
    }

    return activePoints.count()
}

private fun parseInitialConfigurationToPointsIn4D(initialCubeConfiguration: String): Set<Point4D> {
    val activePoints = initialCubeConfiguration.lines().mapIndexed { rowIndex, line ->
        line.mapIndexed { columnIndex, letter ->
            if (letter == '#') Option.just(
                Point4D(
                    rowIndex,
                    columnIndex,
                    0,
                    0
                )
            ) else Option.empty()
        }
    }.flatten().filterMap { it }.toSet()
    return activePoints
}

fun main() {
    // println(conwaysGameOfLiveIn3D(y2020d17input))
    println(conwaysGameOfLiveIn4D(y2020d17input))
}
