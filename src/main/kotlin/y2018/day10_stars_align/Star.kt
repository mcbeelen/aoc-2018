package y2018.day10_stars_align

data class Star(val position: Position, val velocity: Velocity) {

    fun isAt(expectedPosition: Position) = this.position == expectedPosition

    fun move(): Star = copy(position = position.move(velocity))
}

/**
 * position=<3, 9> velocity=<1, -2>
 */
internal fun parseInputToStar(input: String): Star {
    val positionInput = input.substringAfter("<").substringBefore(">")
    val velocityInput = input.substringAfterLast("<").substringBeforeLast(">")
    return Star(parsePosition(positionInput), parseVelocity(velocityInput))
}




data class Position(val x: Int, val y: Int) {
    fun move(velocity: Velocity) = Position(this.x + velocity.x, this.y + velocity.y)
}

internal fun parsePosition(input: String) : Position {
    val split = input.split(",")
    return Position(split[0].trim().toInt(), split[1].trim().toInt())
}



data class Velocity(val x: Int, val y: Int)


internal fun parseVelocity(input: String) : Velocity {
    val split = input.split(",")
    return Velocity(split[0].trim().toInt(), split[1].trim().toInt())
}