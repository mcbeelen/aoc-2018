package day20_regular_map

import util.grid.search.BreadthFirstSearchAlgorithm

class Pathfinder(private val facility: Facility) : BreadthFirstSearchAlgorithm<Room, Passage>(facility) {
    override fun exploreVertex(vertex: Room, callback: (Passage) -> Unit) {
        facility.findNeighbours(vertex)
                .forEach {
                    run {
                        callback(it)
                    }
                }
    }
}


fun findLengthOfShortestPathToFurthestRoom(facility: Facility) : Int {
    val origin = facility.origin()
    val furthestFoundRoom = facility.map
            .filter { it.value is Room }
            .map { Pair(it.value, Pathfinder(facility).findShortestPath(origin, it.value as Room)!!.calculateDistance()) }
            .maxBy { it.second }

    println("Found the furthest room to be at: ${furthestFoundRoom!!.first} with a distance of ${furthestFoundRoom.second} ")
    return furthestFoundRoom.second


}



