package util.grid

abstract class GridTracker<T> : GridWalker() {

    protected val grid = Grid<T>()

    fun plot(c: Char = '?') {
        plotGrid(grid, c) {
            charFor(it)
        }
    }


    abstract fun charFor(t: T): Char

}
