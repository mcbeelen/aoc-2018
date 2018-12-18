package day17_reservoir_research

import day17_reservoir_research.FlowState.*
import util.grid.Direction
import util.grid.Direction.*
import util.grid.ScreenCoordinate


sealed class Tile(val location: ScreenCoordinate) {
    abstract fun isSpring() : Boolean

    private val flows : MutableMap<Direction, FlowState> = HashMap()

    fun markAsBlocked(direction: Direction) {
        flows[direction] = BLOCKED
    }

    fun markAsOverflowing(direction: Direction) {
        flows[direction] = OVERFLOWING

        if (this is TileOfSoil) {
            this.linkToSource.markAsOverflowing()
        }

    }

    override fun toString(): String {
        return "${location}"
    }


}

class TileOfSoil(location: ScreenCoordinate, from: Tile, direction: Direction) : Tile(location) {

    val linkToSource = Link(from, this, direction, FLOWING)

    override fun isSpring() = false
}

class SpringOfWater(location: ScreenCoordinate) : Tile(location) {
    override fun isSpring() = true
}


data class DirectionToExplore(val from: Tile, val direction: Direction) {

    fun nextLocation() = from.location.next(direction)

    fun markAsBlocked() {
        from.markAsBlocked(direction)
    }

    fun markAsOverflowing() {
        from.markAsOverflowing(direction)
    }

}

fun downwardsFrom(from: Tile) = DirectionToExplore(from, DOWN)
fun leftwardsFrom(from: Tile) = DirectionToExplore(from, LEFT)
fun rightwardsFrom(from: Tile) = DirectionToExplore(from, RIGHT)

class Link(val from: Tile, val to: Tile, val direction: Direction, var state: FlowState) {
    fun markAsOverflowing() {
        this.state = OVERFLOWING
        this.from.markAsOverflowing(direction)
    }
}


enum class FlowState {
    FLOWING,
    BLOCKED,
    OVERFLOWING
}