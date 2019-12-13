package y2019.computer

import util.grid.ScreenCoordinate

interface ScreenOutputStream {
    fun paint(coordinate: ScreenCoordinate, char: Char)
    fun stopScreen() {}
}

class NoOpScreenOutputScreen : ScreenOutputStream {
    override fun paint(coordinate: ScreenCoordinate, char: Char) {
        // Do nothing
    }

}

class InmemoryScreenOutputStream : ScreenOutputStream {

    private val buffer: MutableMap<ScreenCoordinate, Char> = HashMap()

    override fun paint(coordinate: ScreenCoordinate, char: Char) {
        buffer[coordinate] = char
    }

    fun calculateNumberOfTiles(char: Char): Int = buffer.values.count { it == char }

}