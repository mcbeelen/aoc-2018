package y2018.day20_regular_map

import util.grid.*
import util.grid.search.Graph

data class Facility(val map: Map<ScreenCoordinate, Position>) : Graph<Room, Passage>() {

    override fun findNeighbours(vertex: Room): List<Passage> {

        return Bearing.values()
                .map { Pair(it, vertex.findLocation(it)) }
                .filter { map.getOrDefault(it.second, Wall(it.second)) is Door }
                .map { Passage(vertex, map[it.second.next(it.first.direction)] as Room) }


    }

    fun hasDoorToThe(room: Room, bearing: Bearing) : Boolean {
        val location = room.findLocation(bearing)
        return (map.getOrDefault(location, Wall(location)) is Door)
    }

    fun withDoor(currentLocation: ScreenCoordinate, bearing: Bearing): Facility {

        val currentRoom = findRoom(currentLocation)
        val theNewDoor = Door(currentRoom.findLocation(bearing))
        val theNextRoom = Room(theNewDoor.findLocation(bearing))

        return updateMap(theNewDoor, theNextRoom)
    }

    private fun findRoom(location: ScreenCoordinate) = map.getValue(location)

    private fun updateMap(vararg newFoundPositions: Position): Facility {

        val updatedMap = map.toMutableMap()

        newFoundPositions.forEach {
            updatedMap[it.location] = it
        }

        return copy(map = updatedMap)
    }

    fun isThereADoorAt(x: Int, y: Int) = map.getOrDefault(ScreenCoordinate(x, y), Wall(x, y)) is Door

    fun origin(): Room  = map[ScreenCoordinate(0, 0)] as Room


    val minX: Int by lazy { findMinX(map.keys) }
    val maxX: Int by lazy { findMaxX(map.keys)}
    val minY: Int by lazy { findMinY(map.keys) }
    val maxY: Int by lazy { findMaxY(map.keys) }



}



fun drawMap(regularDirections: String): Facility {

    val route : Route = parseRoute(regularDirections)

    val facility = buildFacilityToExplore()

    return explore(route, facility, origin())

}

fun explore(route: Route, facility: Facility, screenCoordinate: ScreenCoordinate) : Facility {

    var currentLocation = screenCoordinate

    var updatedFacility = facility

    route.getDirections().forEach { direction ->
        when (direction) {
            is Step -> {
                updatedFacility = updatedFacility.withDoor(currentLocation, direction.bearing)
                val direction = direction.bearing.direction
                currentLocation = currentLocation.next(direction).next(direction)
            }
            is Branch -> {
                direction.getRoutes().forEach { route ->
                    updatedFacility = explore(route, updatedFacility, currentLocation)
                }
            }
        }
    }


    return updatedFacility
}




private fun buildFacilityToExplore(): Facility {
    val origin = origin()
    val roomAtOrigin = Room(origin)
    val unexploredMap = mapOf(Pair(origin, roomAtOrigin))

    return Facility(unexploredMap)
}

private fun origin() = ScreenCoordinate(0, 0)

