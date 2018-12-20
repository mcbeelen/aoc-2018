package day20_regular_map

import util.grid.Direction
import util.grid.Direction.*

class RegularMapOfCompound {

    fun hasDoorToThe(room: Room, direction: CardinalDirection) : Boolean {
        return false
    }


    fun findLengthOfShortestPathToFurthestRoom(): Int {
        return 0
    }
}

fun drawMap(regularDirections: String): RegularMapOfCompound {
    return RegularMapOfCompound()
}


enum class CardinalDirection(direction: Direction) {
    NORTH(UP),
    EAST(RIGHT),
    SOUTH(DOWN),
    WEST(LEFT)

}

