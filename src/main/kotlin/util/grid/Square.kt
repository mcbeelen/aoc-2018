package util.grid

data class Square(val origin: ScreenCoordinate, val width: Int, val height: Int) {

    val right: Int = origin.left + width - 1
    val bottom: Int = origin.top + height - 1

    val corners : List<ScreenCoordinate> by lazy {
        listOf(
                ScreenCoordinate(origin.left, origin.top),
                ScreenCoordinate(right, origin.top),
                ScreenCoordinate(right, bottom),
                ScreenCoordinate(origin.left, bottom)
        )
    }


    fun contains(point: ScreenCoordinate) =
        point.left in (origin.left .. right) &&
            point.top in (origin.top .. bottom)

    fun overLapsWith(other: Square): Boolean {
        return other.corners.count { this.contains(it) } > 0 &&
                this.corners.count { other.contains(it)} > 0

    }

}