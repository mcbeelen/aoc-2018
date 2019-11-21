package y2018.day17_reservoir_research

import y2018.day17_reservoir_research.FlowState.*
import util.grid.Direction
import util.grid.Direction.*
import util.grid.ScreenCoordinate


sealed class Tile(val location: ScreenCoordinate) {

    abstract fun isSpring() : Boolean

    abstract val linkToSource : Link

    private val flows : MutableMap<Direction, FlowState> = HashMap()

    fun markAsBlockedFrom(direction: Direction) {
        flows[direction] = BLOCKED
    }

    fun markAsOverflowingTowards(direction: Direction) {
        flows[direction] = OVERFLOWING

        if (this is TileOfSoil) {
            this.linkToSource.markAsOverflowing()
        }
    }

    fun isOverflowing() = flows.any { it.value == OVERFLOWING }

    fun isOnBlockSideOfOverflowingBucket(): Boolean {
        if (! isSourceOnSameLine() ) {
            return linkToSource.from.isOverflowing()
        }

        return linkToSource.from.isOnBlockSideOfOverflowingBucket()
    }

    private fun isSourceOnSameLine() = linkToSource.from.location.top == location.top


    fun canNotFlowSideways() = canNotFlow(LEFT) && canNotFlow(RIGHT)

    private fun canNotFlow(direction: Direction) = flows.containsKey(direction) && flows.get(direction) == BLOCKED

    fun getPrint(): Char {
        if (flows.any{ it.value == OVERFLOWING}) {
            return when (linkToSource.direction) {
                DOWN -> '↓'
                RIGHT -> '>'
                LEFT -> '<'
                UP -> '^'
            }
        } else if (flows.all { it.value == BLOCKED }) {
            return '~'
        } else return '?'

    }

    override fun toString(): String {
        return "${location}"
    }



}

class TileOfSoil(location: ScreenCoordinate, from: Tile, direction: Direction) : Tile(location) {

    override val linkToSource = Link(from, this, direction, FLOWING)

    override fun isSpring() = false


}

class SpringOfWater(location: ScreenCoordinate) : Tile(location) {

    override val linkToSource = Link(this, this, DOWN, FLOWING)

    override fun isSpring() = true
}


data class DirectionToExplore(val from: Tile, val direction: Direction) {

    fun nextLocation() = from.location.next(direction)

    fun markAsBlocked() {
        from.markAsBlockedFrom(direction)
    }

    fun markAsOverflowing() {
        from.markAsOverflowingTowards(direction)
    }

}

fun downwardsFrom(from: Tile) = DirectionToExplore(from, DOWN)
fun leftwardsFrom(from: Tile) = DirectionToExplore(from, LEFT)
fun rightwardsFrom(from: Tile) = DirectionToExplore(from, RIGHT)

class Link(val from: Tile, val to: Tile, val direction: Direction, var state: FlowState) {

    fun markAsOverflowing() {
        this.state = OVERFLOWING
        this.from.markAsOverflowingTowards(direction)
    }
}


enum class FlowState {
    FLOWING,
    BLOCKED,
    OVERFLOWING
}