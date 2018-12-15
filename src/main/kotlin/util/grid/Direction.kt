package util.grid

import day13_cart_madness.Turn

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

}