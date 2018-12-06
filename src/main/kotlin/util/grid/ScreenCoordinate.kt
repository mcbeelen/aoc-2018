package util.grid

import kotlin.math.abs

data class ScreenCoordinate(val left: Int = 0, val top: Int = 0) : Comparable<ScreenCoordinate> {

    override fun compareTo(other: ScreenCoordinate): Int {
        if (left == other.left) {
            return top - other.top
        }
        return left - other.left
    }

    fun distanceTo(point: ScreenCoordinate) = abs(left - point.left) + abs(top - point.top)



}


fun parseXcommaY(input: String) : ScreenCoordinate {
    val split = input.split(",")
    val left = split[0].trim().toInt()
    val top = split[1].trim().toInt()
    return ScreenCoordinate(left, top)
}