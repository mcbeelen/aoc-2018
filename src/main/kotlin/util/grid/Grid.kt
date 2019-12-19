package util.grid

class Grid<T> : HashMap<ScreenCoordinate, T>()


fun <T> plotGrid(grid: Grid<T>, defaultCharForUnknown: Char = '?', charFor: (T) -> Char) {
    val findMinY = findMinY(grid.keys)
    val findMaxY = findMaxY(grid.keys)
    val findMinX = findMinX(grid.keys)
    val findMaxX = findMaxX(grid.keys)



    for (y in findMinY..findMaxY) {
        print("${y.toString().padStart(8)}: ")
        for (x in findMinX..findMaxX) {
            val objectAtXY = grid[at(x, y)]

            if (objectAtXY == null) {
                print('?')
            } else {
                print(charFor(objectAtXY))
            }
        }
        println()
    }

}

fun <T> parseToGrid(gridDefinition: String, fromSymbol: (Char) -> T) : Grid<T> {
    val grid = Grid<T>()
    gridDefinition.trimIndent().lines().forEachIndexed { topIndex, line ->
        line.forEachIndexed { leftIndex, c ->
            val coordinate = at(leftIndex, topIndex)
            grid[coordinate] = fromSymbol(c)
        }
    }
    return grid
}