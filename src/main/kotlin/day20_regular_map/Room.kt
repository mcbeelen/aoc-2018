package day20_regular_map

import util.grid.ScreenCoordinate

class Room(val location: ScreenCoordinate) {
    constructor(x: Int, y: Int) : this(ScreenCoordinate(x, y))

    override fun toString(): String {
        return "Room($location)"
    }


}