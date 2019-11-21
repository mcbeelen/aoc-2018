package y2018.day25_four_dimensional_adventure

import util.collections.Stack

class ConstellationCounter {

    companion object {


        @JvmStatic
        fun main(args: Array<String>) {

            val spaceTimePoints = SPACE_TIME_POINTS.trimIndent().lines().map { parseToSpaceTimePoint(it) }

            println("${countNumberOfConstellations(spaceTimePoints)}")

        }
    }

}

fun countNumberOfConstellations(points : List<SpaceTimePoint>) : Int {

    var unexploredPoints : Stack<SpaceTimePoint> = Stack()
    var unassignedPoints : MutableList<SpaceTimePoint> = points.toMutableList()

    var counter = 0
    while (unassignedPoints.isNotEmpty()) {

        counter++
        val spaceTimePoint = unassignedPoints.first()
        unexploredPoints.push(spaceTimePoint)
        unassignedPoints.remove(spaceTimePoint)

        while (unexploredPoints.isNotEmpty()) {
            val pointToExplore = unexploredPoints.pop()

            val pointsInSameConstellation = unassignedPoints.filter { it.isInSameConstellation(pointToExplore) }
            unexploredPoints.pushAll(pointsInSameConstellation)
            unassignedPoints.removeAll(pointsInSameConstellation)
        }


    }


    return counter

}