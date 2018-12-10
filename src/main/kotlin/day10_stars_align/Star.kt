package day10_stars_align

data class Star(val position: Position, val velocity: Velocity) {

    fun isAt(expectedPosition: Position) = this.position == expectedPosition

    fun move(): Star = copy(position = position.move(velocity))
}



data class Position(val x: Int, val y: Int) {
    fun move(velocity: Velocity) = Position(this.x + velocity.x, this.y + velocity.y)
}

data class Velocity(val x: Int, val y: Int)