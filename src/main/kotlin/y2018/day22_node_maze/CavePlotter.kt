package y2018.day22_node_maze

import y2018.day22_node_maze.RegionType.*
import util.console.*
import util.grid.ScreenCoordinate
import util.grid.findMaxY
import util.grid.search.Path

fun plotCave(cave: Cave, path: Path<ReachedRegion>) {

    val caveMap = cave.caveMap

    val maxX = path.vertices.map { it.region.location.left }.max() ?: 40

    for (y in 0..findMaxY(caveMap.keys)) {
        for (x in 0..maxX) {
            if (path.vertices.any { it.region.location.isAt(x, y) }) {
                print('X')
            } else {
                when (caveMap.getValue(ScreenCoordinate(x, y)).regionType) {

                    ROCKY -> print("$ANSI_BLACK_BACKGROUND.$ANSI_RESET")
                    WET -> print("$ANSI_WHITE$ANSI_BLUE_BACKGROUND~$ANSI_RESET")
                    NARROW -> print("$ANSI_GREEN_BACKGROUND=$ANSI_RESET")
                }

            }
        }
        println()
    }


}
