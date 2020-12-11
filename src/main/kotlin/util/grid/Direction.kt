package util.grid

enum class Direction {

    UP,
    RIGHT,
    DOWN,
    LEFT;

    fun turn(turn: Turn): Direction {
        return when(turn) {

            Turn.LEFT -> this.turnLeft()
            Turn.STRAIGHT -> this
            Turn.RIGHT -> this.turnRight()
        }
    }



    private fun turnLeft(): Direction {
        return when(this) {

            UP -> LEFT
            RIGHT -> UP
            DOWN -> RIGHT
            LEFT -> DOWN
        }

    }

    private fun turnRight(): Direction {
        return when(this) {

            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
    }

    fun reverse(): Direction = when (this) {
        UP -> DOWN
        RIGHT -> LEFT
        DOWN -> UP
        LEFT -> RIGHT
    }

}

fun parseDirection(c: Char): Direction {
    return when (c) {
        'D' -> Direction.DOWN
        'L' -> Direction.LEFT
        'R' -> Direction.RIGHT
        'U' -> Direction.UP
        else -> throw IllegalArgumentException()
    }

}


val EIGHT_COMPASS_DIRECTIONS = listOf(
    Vector(-1, -1),
    Vector(-0, -1),
    Vector(+1, -1),
    Vector(-1, -0),

    Vector(+1, -0),
    Vector(-1, +1),
    Vector(-0, +1),
    Vector(+1, +1),
)
