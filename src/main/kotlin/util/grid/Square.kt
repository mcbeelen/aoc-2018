package util.grid

data class Square(val origin: ScreenCoordinate, val width: Int, val height: Int) {

    val left = origin.left
    var top = origin.top
    val right: Int = left + width - 1
    val bottom: Int = top + height - 1

    val corners : List<ScreenCoordinate> by lazy {
        listOf(
                ScreenCoordinate(left, top),
                ScreenCoordinate(right, top),
                ScreenCoordinate(right, bottom),
                ScreenCoordinate(left, bottom)
        )
    }


    fun contains(point: ScreenCoordinate) =
        point.left in (left .. right) &&
            point.top in (top .. bottom)

    fun overLapsWith(other: Square): Boolean {
        return containsOneOfTheCorners(other) || areOverlappingRectangles(other)

    }



    private fun containsOneOfTheCorners(other: Square) = other.corners.count { this.contains(it) } > 0 &&
            this.corners.count { other.contains(it) } > 0


    private fun areOverlappingRectangles(other: Square): Boolean {
        return (this.left > other.left && this.right < other.right && this.top <= other.top && this.bottom >= other.bottom) ||
                (this.left <= other.left && this.right >= other.right && this.top > other.top && this.bottom < other.bottom)




    }

    fun getAllCoordinates() {

    }

}