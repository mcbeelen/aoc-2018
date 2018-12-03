package util.grid

data class Square(val origin: ScreenCoordinate, val width: Int, val height: Int) {

    private val left = origin.left
    private val top = origin.top
    val right: Int = left + width - 1
    val bottom: Int = top + height - 1

    fun contains(point: ScreenCoordinate) =
        point.left in (left .. right) &&
            point.top in (top .. bottom)

    fun overLapsWith(other: Square): Boolean {
        return ((left .. right) intersect (other.left .. other.right)).isNotEmpty() &&
                ((top .. bottom) intersect (other.top .. other.bottom)).isNotEmpty()

    }




}