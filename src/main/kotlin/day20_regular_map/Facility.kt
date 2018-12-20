package day20_regular_map

import util.grid.ScreenCoordinate

data class Facility(val map: Map<ScreenCoordinate, Position>) {

    fun hasDoorToThe(room: Room, bearing: Bearing) : Boolean {
        val location = room.findLocation(bearing)
        return (map.getOrDefault(location, Wall(location)) is Door)
    }

    fun findLengthOfShortestPathToFurthestRoom(): Int {
        return 0
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


    val minX: Int by lazy { map.map { it.key.left }.min() ?: Int.MIN_VALUE }
    val maxX: Int by lazy { map.map { it.key.left }.max() ?: Int.MIN_VALUE }

    val minY: Int by lazy { map.map { it.key.top }.min() ?: Int.MIN_VALUE }
    val maxY: Int by lazy { map.map { it.key.top }.max() ?: Int.MIN_VALUE }



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

