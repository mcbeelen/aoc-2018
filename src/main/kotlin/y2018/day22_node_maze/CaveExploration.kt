package y2018.day22_node_maze

import y2018.day22_node_maze.Equipment.*
import util.grid.ScreenCoordinate
import util.grid.search.BreadthFirstSearchAlgorithm
import util.grid.search.Edge
import util.grid.search.ReadableZobraistKey
import util.grid.search.Vertex
import java.util.*
import kotlin.system.measureTimeMillis

class CaveExploration(private val cave: Cave) : BreadthFirstSearchAlgorithm<ReachedRegion, ActionInCave>(cave) {
    override fun exploreVertex(vertex: ReachedRegion, callback: (ActionInCave) -> Unit) {
        cave.possibleActions(vertex)
                .forEach {
                    run {
                        callback(it)
                    }
                }
    }

    override fun <ReachedRegion> buildQueue(): Queue<ReachedRegion> {
        return PriorityQueue(TimeNeededComparator()) as Queue<ReachedRegion>
    }

}


data class ReachedRegion(val region: Region, val equipedWith: Equipment, val timeNeeded: Int = 0) : Vertex<ReachedRegion>() {

    override fun buildZobristKey() = ReadableZobraistKey("${region.location} with ${equipedWith}")

    fun withEquipment(equipment: Equipment) = this.copy(equipedWith = equipment, timeNeeded = timeNeeded + 7)
    fun movedTo(region: Region): ReachedRegion = this.copy(region = region, timeNeeded = timeNeeded + 1)
}



class TimeNeededComparator : Comparator<ReachedRegion> {
    override fun compare(first: ReachedRegion, other: ReachedRegion): Int {
        val compareTo = first.timeNeeded.compareTo(other.timeNeeded)
        return compareTo
    }

}

class ActionInCave(origin: ReachedRegion, destination: ReachedRegion, distance: Int) : Edge<ReachedRegion>(origin, destination, distance)


class ProblemSolverDay22PartTwo {

    companion object {


        @JvmStatic
        fun main(args: Array<String>) {

            val target = ScreenCoordinate(7, 770)
            val timeInMillis = measureTimeMillis {
                val actualCave = Cave(10647, target)
                actualCave.exploreUntilTarget()

                val caveExploration = CaveExploration(actualCave)

                val origin = ReachedRegion(actualCave.caveMap.getValue(ScreenCoordinate(0, 0)), TORCH)
                val destination = ReachedRegion(actualCave.caveMap.getValue(target), TORCH)

                val path = caveExploration.findShortestPath(origin, destination)

                println("Getting to the man's friend takes at least ${path!!.calculateDistance()} minutes")

            }

            println("Solved part two in ${timeInMillis}ms")


        }
    }
}
