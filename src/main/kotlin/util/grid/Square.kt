package util.grid

import kotlin.math.min

data class Square(val origin: ScreenCoordinate, val width: Int, val height: Int) {

    val left = origin.left
    val top = origin.top
    val right: Int = left + width - 1
    val bottom: Int = top + height - 1

    fun contains(point: ScreenCoordinate) =
        point.left in (left .. right) &&
            point.top in (top .. bottom)

    fun overLapsWith(other: Square): Boolean {
        return ((left .. right) intersect (other.left .. other.right)).isNotEmpty() &&
                ((top .. bottom) intersect (other.top .. other.bottom)).isNotEmpty()

    }

    fun expandToInclude(coordinate: ScreenCoordinate): Square {
        val newLeft = min(coordinate.left, left)
        val newTop = min(coordinate.top, top)
        return copy(origin = ScreenCoordinate(newLeft, newTop),
                width = maxOf(width, coordinate.left - newLeft + 1, right - newLeft + 1),
                height = maxOf(height, coordinate.top - newTop + 1, bottom - newTop + 1))


    }

    fun allPoints() : Sequence<ScreenCoordinate> = sequence {
            for (x in left .. right) {
                for (y in top..bottom) {
                    yield(ScreenCoordinate(x, y))
                }
            }
        }

    fun isOnBorder(point: ScreenCoordinate): Boolean = point.left == left || point.left == right || point.top == top || point.top == bottom

}

fun buildSquareContainingAll(coordinates: List<ScreenCoordinate>): Square {
    var square = Square(coordinates.first(), 1, 1)
    coordinates.drop(1)
            .forEach { square = square.expandToInclude(it) }
    return square
}