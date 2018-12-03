package util.grid

data class Square(val origin: ScreenCoordinate, val width: Int, val height: Int) {

    fun contains(point: ScreenCoordinate) =
        point.left in (origin.left until origin.left + width) &&
            point.top in (origin.top until origin.top + height)

}