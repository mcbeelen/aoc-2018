package y2018.day20_regular_map

import util.grid.search.BreadthFirstSearchAlgorithm

open class Pathfinder(private val facility: Facility) : BreadthFirstSearchAlgorithm<Room, Passage>(facility) {
    override fun exploreVertex(vertex: Room, callback: (Passage) -> Unit) {
        facility.findNeighbours(vertex)
                .forEach {
                    run {
                        callback(it)
                    }
                }
    }
}


class LimitedStepsPathfinder(facility: Facility, val maxNumberOfSteps : Int = 1_000) : Pathfinder(facility) {
    override fun stillNeedToProcessNodes(destination: Room) = peekAtClosestVertex().distanceFromSource < maxNumberOfSteps

}



