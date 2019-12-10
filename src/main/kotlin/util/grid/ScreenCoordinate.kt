package util.grid

import com.google.common.math.IntMath.gcd
import util.grid.Direction.*
import kotlin.math.abs

val ORIGIN = ScreenCoordinate(0, 0)

fun at(left: Int, top: Int) = ScreenCoordinate(left, top)

data class Vector(val left: Int = 0, val top: Int = 0)

tailrec fun simplify(vector: Vector) : Vector {
    val left = vector.left
    val top = vector.top
    val absLeft = abs(left)
    val absTop = abs(top)
    return when (gcd(absLeft, absTop)) {
        0 -> vector
        1 -> vector
        else -> simplify(Vector(left / gcd(absLeft, absTop), top / gcd(absLeft, absTop)))
    }
}

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
            DOWN -> copy(top = top + 1)
            LEFT -> copy(left = left - 1)
        }

    }

    fun isAt(left: Int, top: Int): Boolean = this.left == left && this.top == top

    fun isAt(other: ScreenCoordinate) = isAt(other.left, other.top)

    override fun toString(): String {
        return "<$left, $top>"
    }

    fun isAdjacentTo(other: ScreenCoordinate) = distanceTo(other) == 1

    fun isAbove(other: ScreenCoordinate) = this.left == other.left && this.top == other.top - 1
    fun isToTheRightOf(other: ScreenCoordinate) = this.top == other.top && this.left == other.left + 1
    fun isToTheLeftOf(other: ScreenCoordinate) = this.top == other.top && this.left == other.left - 1

    fun vectorTo(other: ScreenCoordinate): Vector = Vector(other.left - this.left, other.top - this.top)
    fun transpose(vector: Vector, steps: Int) = at(this.left + vector.left * steps, this.top + vector.top * steps )


}

fun findMaxX(locations: Set<ScreenCoordinate>) = locations.map { it.left }.max() ?: Int.MIN_VALUE
fun findMaxY(locations: Set<ScreenCoordinate>) = locations.map { it.top }.max() ?: Int.MIN_VALUE


fun findMinX(locations: Set<ScreenCoordinate>) = locations.map { it.left }.min() ?: Int.MIN_VALUE
fun findMinY(locations: Set<ScreenCoordinate>) = locations.map { it.top }.min() ?: Int.MIN_VALUE


class CoordinatesInReadingOrder : Comparator<ScreenCoordinate> {
    override fun compare(any: ScreenCoordinate, other: ScreenCoordinate): Int {
        if (any.top == other.top) {
            return any.left - other.left
        }
        return any.top - other.top
    }

}


fun parseXcommaY(input: String): ScreenCoordinate {
    val split = input.split(",")
    val left = split[0].trim().toInt()
    val top = split[1].trim().toInt()
    return ScreenCoordinate(left, top)
}