package y2018.day20_regular_map

import util.grid.ScreenCoordinate
import util.grid.search.Edge
import util.grid.search.ReadableZobraistKey
import util.grid.search.Vertex


data class Passage(val from: Room, val to: Room) : Edge<Room>(from, to)


interface Position {
    val location: ScreenCoordinate

    fun findLocation(bearing: Bearing) = location.next(bearing.direction)
}

data class Door(override val location: ScreenCoordinate) : Position {}

data class Wall(override val location: ScreenCoordinate) : Position {
    constructor(x: Int, y: Int) : this(ScreenCoordinate(x, y))
}


data class Room(override val location: ScreenCoordinate) : Position, Vertex<Room>() {

    constructor(x: Int, y: Int) : this(ScreenCoordinate(x, y))

    override fun buildZobristKey() = ReadableZobraistKey(location.toString())

    override fun toString(): String {
        return "Room($location)"
    }




}