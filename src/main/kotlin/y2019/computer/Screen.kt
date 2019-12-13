package y2019.computer

import util.grid.ScreenCoordinate

class Screen {

    private val buffer: MutableMap<ScreenCoordinate, Char> = HashMap()

    fun paint(coordinate: ScreenCoordinate, char: Char) {
        buffer[coordinate] = char
    }

    fun calculateNumberOfTiles(char: Char): Int = buffer.values.count { it == char }

}