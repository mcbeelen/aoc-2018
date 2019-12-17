package y2019.day15_intcode_maze_to_oxygen_system

import util.grid.Direction
import util.grid.ScreenCoordinate
import util.grid.at
import util.grid.plotGrid
import y2019.day15_intcode_maze_to_oxygen_system.Field.OXYGEN_SYSTEM
import y2019.day15_intcode_maze_to_oxygen_system.Field.SPACE

class OxygenSimulation(oxygenSystemMap: String) {
    val grid: MutableMap<ScreenCoordinate, Field> = HashMap()

    init {
        oxygenSystemMap.trimIndent().lines().forEachIndexed { topIndex, line ->
            line.forEachIndexed { leftIndex, c ->
                val coordinate = at(leftIndex, topIndex)
                val field = fieldFor(c, coordinate)
                grid[coordinate] = field
            }
        }
    }


    fun repair() : Int {
        var minute = 0
        var fieldsToExpandFrom = listOf(grid.keys.first { grid.getValue(it) == OXYGEN_SYSTEM })
        while (grid.any { entry -> entry.value == SPACE }) {
            val fieldJustFilledWithOxygen : MutableList<ScreenCoordinate> = ArrayList()


            fieldsToExpandFrom.forEach { fieldWithOxygen ->
                        Direction.values().forEach { direction ->
                            val coordinate = fieldWithOxygen.next(direction)
                            if (grid.getValue(coordinate) == SPACE) {
                                grid[coordinate] = OXYGEN_SYSTEM
                                fieldJustFilledWithOxygen.add(coordinate)
                            }
                        }
                    }
            fieldsToExpandFrom = fieldJustFilledWithOxygen
            minute++
        }
        return minute
    }
}


fun main() {
    val minutes = OxygenSimulation(MAP_OF_AREA_OXYGEN_AREA).repair()
    println(minutes)

}



const val MAP_OF_AREA_OXYGEN_AREA = """
#########################################
#.....#...........#.....#...#...........#
###.#.#######.###.#.###.#.#.###########.#
#...#.#.....#.#...#.#...#.#.........#...#
#.###.#.###.#.###.#.#.###.#########.#.#.#
#.#.#.#.#...#...#...#.#.....#.......#.#.#
#.#.#.#.#.#####.#####.#.#####.#######.#.#
#.#.....#.#.....#.....#.#.....#.......#.#
#.#######.#.#####.#####.#.#####.#######.#
#.......#...#...#...#...#...#...#.......#
#.#####.#####.#.###.#.#####.###.#.#######
#.....#.#.#...#...#.#.#.....#...#.#.....#
#######.#.#.#.#####.#.#.#####.###.#.###.#
#...#...#.#.#.......#.#.....#.#.#.#.#...#
#.#.#.###.#.#########.#####.#.#.#.#.#.###
#.#...#...#.#...#...#...#...#.#...#.#...#
#.#######.#.#.#.#.#####.#.###.#.#######.#
#...#...#...#.#...#.....#.#...#.#.....#.#
#.#.#.#.#.###.#.###.#####.###.#.#.###.#.#
#.#...#.#.#...#.#.......#.#...#...#.#.#.#
#######.#.#.###.#.#####.#.#.#######.#.#.#
#.......#.#...#.#...#.#.#.#.#...#...#.#.#
#.#######.###.#####.#.###.#.###.#.#.#.#.#
#...#...#...#.#.....#.#...#...#...#.#.#.#
#.#.###.###.#.#.#####.#.#####.#.###.#.#.#
#.#.#.....#...#.....#.#.#.....#.#.#...#.#
#.#.#.#.#####.#####.#.#.###.###.#.#####.#
#.#.#.#.#.....#.....#...#...#.#...#...#.#
#.#.#.###.#####.#########.###.###.#.#.#.#
#.#.#...#...#...#.....#.....#.....#.#.#.#
###.###.#.###.#####.#.###.#.#.#####.#.#.#
#...#...#.#...#.....#...#.#.#.#.....#.#.#
#.###.###.#.#####.#####.###.#.#.###.#.#.#
#.#O......#.....#.#...#.....#.#...#.#...#
#.#############.#.#.#.#######.###.#.###.#
#.#.......#...#.#...#.#.#...#.#...#...#.#
#.#.#####.#.#.#.#.###.#.#.#.#.#####.#.#.#
#.#.#...#.#.#.#.#.#...#...#...#...#.#.#.#
#.#.###.#.#.#.#.###.###.#######.#.###.#.#
#.......#...#.......#...........#.....#.#
#########################################"""