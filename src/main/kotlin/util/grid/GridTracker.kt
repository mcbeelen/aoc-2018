package util.grid

abstract class GridTracker<T> : GridWalker() {

    protected val grid: MutableMap<ScreenCoordinate, T> = HashMap()

    fun plot() {
        val findMinY = findMinY(grid.keys)
        val findMaxY = findMaxY(grid.keys)
        val findMinX = findMinX(grid.keys)
        val findMaxX = findMaxX(grid.keys)

        for (y in findMinY..findMaxY) {
            for (x in findMinX..findMaxX) {
                val objectAtXY = grid[at(x, y)]
                if (objectAtXY == null) {
                    print(' ')
                } else {
                    print(charFor(objectAtXY))
                }
            }
            println()
        }

    }


    abstract fun charFor(t: T): Char

}