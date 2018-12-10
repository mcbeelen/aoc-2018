package day10_stars_align

class Sky(private val stars: List<Star>) {

    fun hasStarAt(position: Position)= stars.any { it.isAt(position) }

    fun moveStars(): Sky = Sky(stars.map { it.move() })

}

