package day23_nanobots_experimental_emergency_teleportation

import util.space.Cube
import util.space.ORIGIN
import util.space.Point


data class Formation(val bots: List<Nanobot>) {

    val xRange: IntRange by lazy {
        rangeOf(bots.map { it.point.x })
    }
    val yRange: IntRange by lazy {
        rangeOf(bots.map { it.point.y })
    }
    val zRange: IntRange by lazy {
        rangeOf(bots.map { it.point.z })
    }

    val from = Point(xRange.first, yRange.first, zRange.first)
    val to = Point(xRange.last, yRange.last, zRange.last)

    fun zoomOut(factor: Int) = Formation(bots.map { it.zoomOut(factor) })

    fun findPointInRangeOfMostNanobotsClosestToOrigin(from: Point, to: Point): Point {

        println("Exploring space from ${from} to ${to}")

        val pointsWithMaxNumberOfBotsInRange: MutableList<Point> = findPointsInRangeOfMaximumAmountOfBots(from, to)
        return pointsWithMaxNumberOfBotsInRange.minBy { it.distanceTo(ORIGIN) }!!
    }

    private fun findPointsInRangeOfMaximumAmountOfBots(from: Point, to: Point): MutableList<Point> {
        val pointsWithMaxNumberOfBotsInRange: MutableList<Point> = ArrayList()
        var maxNumberOfBotsInRange = 800

        (from.x..to.x).forEach { x ->
            (from.y..to.y).forEach { y ->
                (from.z..to.z).forEach { z ->
                    val point = Point(x, y, z)
                    val quantity = bots.filter { isPointWithinRangeOf(point, it) }.count()
                    if (quantity > maxNumberOfBotsInRange) {
                        maxNumberOfBotsInRange = quantity
                        pointsWithMaxNumberOfBotsInRange.clear()
                        pointsWithMaxNumberOfBotsInRange.add(point)

                        println("Found a point in range of ${quantity} bots at ${point}")

                    } else if (quantity == maxNumberOfBotsInRange) {
                        pointsWithMaxNumberOfBotsInRange.add(point)
                        println("  Additional a point in same range of ${quantity} bots at ${point}")
                    }
                }
            }
        }

        return pointsWithMaxNumberOfBotsInRange
    }



    fun findBiggestClique() : Formation {
        val botsWithNumberOfConnections = bots.map { Pair(it, countConnections(it)) }
        var descending = botsWithNumberOfConnections.map { it.second }.toSortedSet().toList()

        while (descending.isNotEmpty()) {
            val candidate = descending.first()
            if (botsWithNumberOfConnections.filter { it.second >= candidate }.count() == candidate) {

                val botsInBiggestClique = botsWithNumberOfConnections.filter { it.second >= candidate }.map { it.first }.toList()
                return Formation(botsInBiggestClique)


            }
            descending = descending.drop(1)
        }

        return Formation(emptyList())
    }

    fun findSizeOfBiggestClique() = findBiggestClique().bots.size

    private fun countConnections(nanobot: Nanobot) =
        bots.filter { it.hasOverlappingRangeWith(nanobot) }
                .count()

    fun determineSearchSpace(): Cube {

        var minX = Int.MIN_VALUE
        var minY = Int.MIN_VALUE
        var minZ = Int.MIN_VALUE

        var maxX = Int.MAX_VALUE
        var maxY = Int.MAX_VALUE
        var maxZ = Int.MAX_VALUE

        bots.forEach {
            val (x, y, z) = it.point
            val r = it.range

            if (x - r > minX) { minX = x - r }
            if (y - r > minY) { minY = y - r }
            if (z - r > minZ) { minZ = z - r }

            if (x + r < maxX) { maxX = x + r }
            if (y + r < maxY) { maxY = y + r }
            if (z + r < maxZ) { maxZ = z + r }


        }

        return Cube(Point(minX, minY, minZ), Point(maxX, maxY, maxZ))

    }

    fun exploreSearchSpace(searchSpace: Cube) : List<Point> {

        val candidates : MutableList<Point> = ArrayList()

        val botWithSmallestRange = bots.sortedBy { it.range }.first()

        val botWithLowestHighX = bots.sortedByDescending { it.point.x - it.range }.first()
        val botWithLowestHighY = bots.sortedByDescending { it.point.y - it.range }.first()
        val botWithLowestHighZ = bots.sortedByDescending { it.point.z - it.range }.first()

        val botWithHighestLowX = bots.sortedBy { it.point.x + it.range }.first()
        val botWithHighestLowY = bots.sortedBy { it.point.y + it.range }.first()
        val botWithHighestLowZ = bots.sortedBy { it.point.z + it.range }.first()

        val mostCriticalBots = listOf(botWithSmallestRange,
                botWithLowestHighX, botWithLowestHighY, botWithLowestHighZ,
                botWithHighestLowX, botWithHighestLowY, botWithHighestLowZ
                )

        val from = searchSpace.from
        val to = searchSpace.to

        (from.x..to.x).forEach { x ->
            (from.y..to.y).forEach { y ->
                (from.z..to.z).forEach { z ->
                    val point = Point(x, y, z)

                    if (mostCriticalBots.all { isPointWithinRangeOf(point, it) }) {
                        candidates.add(point)

                        println("Candidate found at ${point}. Number of candidates so far: ${candidates.size}")

                    }
                }
            }
        }

        return candidates

    }

}



fun parseFormation(formationInput: String): Formation {
    val bots = formationInput.trimIndent().lines().map { parseNanobot(it) }
    return Formation(bots)
}
