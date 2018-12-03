package util.grid

data class Square(val origin: ScreenCoordinate, val width: Int, val height: Int) {

    val right: Int = origin.left + width - 1
    val bottom: Int = origin.top + height - 1

    fun contains(point: ScreenCoordinate) =
        point.left in (origin.left .. right) &&
            point.top in (origin.top .. bottom)

}