package day20_regular_map

import day20_regular_map.Bearing.*
import util.grid.Direction
import util.grid.Direction.*
import util.grid.ScreenCoordinate

data class Facility(val map: Map<ScreenCoordinate, Position>) {

    fun hasDoorToThe(room: Room, bearing: Bearing) : Boolean {
        val location = room.findLocation(bearing)
        return (map.getOrDefault(location, Wall(location)) is Door)
    }

    fun findLengthOfShortestPathToFurthestRoom(): Int {
        return 0
    }

    fun withDoor(currentRoom: Room, bearing: Bearing): Facility {

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

}



fun drawMap(regularDirections: String): Facility {

    val route : Route = parseRoute(regularDirections)

    val facility = buildFacilityToExplore()
    explore(route, facility)

    return facility
}

fun explore(route: Route, facility: Facility) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
}


private fun buildFacilityToExplore(): Facility {
    val origin = ScreenCoordinate(0, 0)
    val roomAtOrigin = Room(origin)
    val unexploredMap = mapOf(Pair(origin, roomAtOrigin))

    var facility = Facility(unexploredMap)
    return facility
}

