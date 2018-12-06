package day06_largest_finite_area

import com.google.common.collect.HashBasedTable
import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import com.google.common.collect.Table
import util.grid.ScreenCoordinate
import util.grid.Square
import util.grid.buildSquareContainingAll
import util.grid.parseXcommaY
import kotlin.system.measureTimeMillis


fun findSafestAreaBetween(coordinatesInput: String): Int {

    val targetCoordinates = parseInputIntoTargetCoordinates(coordinatesInput)
    val square = buildSquareContainingAll(targetCoordinates)


    val targetCoordinatesWithClosestPoints = assignPointsToClosestTarget(square, targetCoordinates)


    val finiteAreas = targetCoordinatesWithClosestPoints
            .filter { isFiniteArea(it.pointClosestToThisTarget, square) }


    val largestTargetArea = finiteAreas.maxBy { it!!.pointClosestToThisTarget.size }

    // - Determine complete area, which needs to be inspects
    // - For each point in the area: find closest TargetCoordinate
    // - Map area into TargetCoordinate, countSize
    // - MaxBy areaSize

    println(largestTargetArea!!.targetCoordinate)

    return largestTargetArea!!.pointClosestToThisTarget.size
}

internal fun assignPointsToClosestTarget(square: Square, targetCoordinates: List<ScreenCoordinate>): List<TargetArea> {

    val pointToTargetWithDistance: Table<ScreenCoordinate, ScreenCoordinate, Int> = HashBasedTable.create()

    square.allPoints()
            .forEach { point ->
                targetCoordinates.forEach {
                    pointToTargetWithDistance.put(point, it, point.distanceTo(it))
                }
            }

    val pointWithMinimumDistanceToClosestTarget = square.allPoints()
            .map {
                val minDistance = pointToTargetWithDistance.row(it).values.minBy { distance -> distance }
                Pair(it, minDistance)
            }.toMap()


    val targetAreas: Multimap<ScreenCoordinate, ScreenCoordinate> = HashMultimap.create()

    square.allPoints()
            .forEach { point ->

                val minimumDistance = pointWithMinimumDistanceToClosestTarget[point]!!
                val targetsAtMinimalDistance = pointToTargetWithDistance.row(point)
                        .filter { target -> target.value == minimumDistance }
                        .toList()

                if (targetsAtMinimalDistance.size == 1) {
                    targetAreas.put(targetsAtMinimalDistance.first().first, point)
                } else {
                    println("Skipping ${point}, due to equal distance")
                }
            }

    return targetCoordinates.map { TargetArea(it, targetAreas.get(it)) }.toList()


}


internal fun parseInputIntoTargetCoordinates(coordinatesInput: String) = coordinatesInput.trimIndent().lines()
        .map { parseXcommaY(it) }

internal fun findClosestTarget(point: ScreenCoordinate, targetCoordinates: List<ScreenCoordinate>) = targetCoordinates
        .minBy { point.distanceTo(it) }


fun isFiniteArea(closestPoints: Collection<ScreenCoordinate>, square: Square): Boolean {
    return closestPoints.none { square.isOnBorder(it) }
}


data class TargetArea(val targetCoordinate: ScreenCoordinate, val pointClosestToThisTarget: Collection<ScreenCoordinate>);




class SafestCoordinateFinder {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val time = measureTimeMillis {
                val sizeOfLargestArea = findSafestAreaBetween(DAY06_INPUT)
                println("The size of the largest finite area is: ${sizeOfLargestArea}")
            }

            println("Solved it in ${time}ms.")
        }
    }
}


const val DAY06_INPUT = """
137, 140
318, 75
205, 290
104, 141
163, 104
169, 164
238, 324
180, 166
260, 198
189, 139
290, 49
51, 350
51, 299
73, 324
220, 171
146, 336
167, 286
51, 254
40, 135
103, 138
100, 271
104, 328
80, 67
199, 180
320, 262
215, 290
96, 142
314, 128
162, 106
214, 326
303, 267
340, 96
211, 278
335, 250
41, 194
229, 291
45, 97
304, 208
198, 214
250, 80
200, 51
287, 50
120, 234
106, 311
41, 116
359, 152
189, 207
300, 167
318, 315
296, 72
"""