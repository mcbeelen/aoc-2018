package y2020.d17_conways_cubes

import arrow.core.Option
import arrow.core.extensions.list.monadFilter.filterMap
import util.space.Point


fun solveIt(initialCubeConfiguration: String): Int {
    var activePoints = parseInitialConfiguration(initialCubeConfiguration)

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

private fun parseInitialConfiguration(initialCubeConfiguration: String): Set<Point> {
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


fun main() {
    println(solveIt(y2020d17input))
}
