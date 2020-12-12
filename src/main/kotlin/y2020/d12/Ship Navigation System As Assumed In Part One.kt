package y2020.d12

import util.grid.*
import util.input.parseInput
import java.lang.IllegalStateException


val vectorPrefixes = listOf('N', 'E', 'S', 'W')
val turnPrefixes = listOf('L', 'R')

fun trackInstructuctionAndReportDistance(input: String): Int {
    val gridWalker = GridWalker()
    gridWalker.direction = Direction.RIGHT

    val moves = parseInput(input) {
        when {
            vectorPrefixes.contains(it[0]) -> gridWalker.move(it.toVector() )
            turnPrefixes.contains(it[0]) -> gridWalker.turn(it.toTurns())
            else -> gridWalker.move(it.substring(1).toInt())
        }
    }
    return gridWalker.currentPosition.distanceTo(ORIGIN)
}



private fun String.toTurns(): Iterable<Turn> {
    val turns : MutableList<Turn> = emptyList<Turn>().toMutableList()
    for (i in 1 .. this.substring(1).toInt() / 90) {
        when (this[0]) {
            'L' -> turns.add(Turn.LEFT)
            'R' -> turns.add(Turn.RIGHT)
        }
    }
    return turns

}

fun String.toVector(): Vector {
    return when (this[0]) {
        'N' -> Vector(0, this.toDistance().unaryMinus())
        'E' -> Vector(this.toDistance(), 0)
        'S' -> Vector(0, this.toDistance())
        'W' -> Vector(this.toDistance().unaryMinus(), 0)
        else -> throw IllegalStateException("${this} does not represent a Vector")
    }
}

fun String.toDistance() = this.substring(1).toInt()
