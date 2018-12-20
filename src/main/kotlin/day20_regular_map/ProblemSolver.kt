package day20_regular_map

import kotlin.system.measureTimeMillis

class PartOne {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val solver = PartOne();
            solver.solveIt()
        }
    }

    private fun solveIt() {
        val facility = drawMap(REGULAR_DIRECTIONS)

        plotFacility(facility)

        println(findLengthOfShortestPathToFurthestRoom(facility))
    }

}

fun findLengthOfShortestPathToFurthestRoom(facility: Facility): Int {
    val origin = facility.origin()
    val furthestFoundRoom = facility.map
            .filter { it.value is Room }
            .map { Pair(it.value, Pathfinder(facility).findShortestPath(origin, it.value as Room)!!.calculateDistance()) }
            .maxBy { it.second }

    println("Found the furthest room to be at: ${furthestFoundRoom!!.first} with a distance of ${furthestFoundRoom.second} ")
    return furthestFoundRoom.second


}


class PartTwoSolver {



    fun solveIt() {
        val facility = drawMap(REGULAR_DIRECTIONS)

        val rooms = facility.map.filter { it.value is Room }.map { it.value }

        println("Total number of rooms found = ${rooms.size}")

        val maxSteps = 999
        val pathfinder = LimitedStepsPathfinder(facility, maxSteps)

        pathfinder.exploreGraphFrom(facility.origin())


        println("During exploration the LimitedStepsPathfinder with max ${maxSteps} found ${pathfinder.numberOfVisitedVertices()} locations")

        println("The number of rooms beyond with at least ${maxSteps + 1} door is ${rooms.size - pathfinder.numberOfVisitedVertices()} ")

    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val solver = PartTwoSolver();

            val time = measureTimeMillis {
                solver.solveIt()
            }

            println("Solved it in ${time}ms")
        }
    }

}
