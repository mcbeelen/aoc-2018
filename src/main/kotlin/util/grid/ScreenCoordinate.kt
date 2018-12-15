package util.grid

import util.grid.Direction.*
import kotlin.math.abs

data class ScreenCoordinate(val left: Int = 0, val top: Int = 0) : Comparable<ScreenCoordinate> {

    override fun compareTo(other: ScreenCoordinate): Int {
        if (left == other.left) {
            return top - other.top
        }
        return left - other.left
    }

    fun distanceTo(point: ScreenCoordinate) = abs(left - point.left) + abs(top - point.top)
    fun next(direction: Direction): ScreenCoordinate {
        return when (direction) {

            UP -> copy(top = top - 1)
            RIGHT -> copy(left = left + 1)
            DOWN -> copy(top =  top + 1)
            LEFT -> copy(left = left - 1)
        }

    }

    override fun toString(): String {
        return "<$left, $top>"
    }


}


fun parseXcommaY(input: String) : ScreenCoordinate {
    val split = input.split(",")
    val left = split[0].trim().toInt()
    val top = split[1].trim().toInt()
    return ScreenCoordinate(left, top)
}