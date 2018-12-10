package day10_stars_align

class Sky(private val stars: List<Star>) {

    fun hasStarAt(position: Position)= stars.any { it.isAt(position) }

    fun moveStars(): Sky = Sky(stars.map { it.move() })


    fun height() : Height {
        val yCoordinates = stars.map { it.position.y }
        return Height(yCoordinates.min()!! .. yCoordinates.max()!!)

    }

    fun width() : Width {
        val xCoordinates = stars.map { it.position.x }
        return Width(xCoordinates.min()!! .. xCoordinates.max()!!)
    }




}

tailrec fun shirkToMinimumHeight(current: Sky): Sky {
    val next = current.moveStars()
    if (next.height().size() > current.height().size()) {
        return current
    }
    return shirkToMinimumHeight(next)

}


data class Width(val value: IntRange)
data class Height(val value: IntRange) {
    fun size() = 1 + value.last - value.first
}


