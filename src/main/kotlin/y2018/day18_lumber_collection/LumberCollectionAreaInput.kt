package y2018.day18_lumber_collection

import y2018.day18_lumber_collection.Acre.*
import util.grid.ScreenCoordinate

fun parseLumberCollectionArea(scanOfTheArea: String): LumberCollectionArea {

    val areaMap: MutableMap<ScreenCoordinate, Acre> = HashMap()

    scanOfTheArea.trimIndent().lines().withIndex()
            .forEach { line ->
                line.value.withIndex().forEach { acre ->
                    areaMap[ScreenCoordinate(acre.index, line.index)] = acreFrom(acre.value)
                }
            }


    return LumberCollectionArea(areaMap)

}

fun acreFrom(char: Char) = when (char) {
    '|' -> TREES
    '#' -> LUMBERYARD
    else -> OPEN
}


const val LUMBER_COLLECTION_AREA_INPUT = """
|.....#.....||...#.|###..#..|#...........|...|..|#
||..|.......|.|.|..|.|....#.|#..|..|.|#.....|.|..|
.#........#.|#.|.#.#.|..|...|#........||.#|....|##
....#.#.|.##..|..#.......|##.......|#.#.|..#.##...
..#|...|.|.........||..|........#...##...|.#..||..
....#.....|.#|...#....#.##..#....|#|.|.||.||#|.##.
.#.|..#..####.#|...#.####.|..........|.....|.#|.#|
|||.#|...#|.|..#.#........#....|..##|||.....#.#.#.
#.#....|..#.|##..|..#.#|..|.|.|..|.|#..|...|..|.#.
||.##.|..||.#..#.|.....|.#|.|#....##....|#...#.|#|
#|.|.###.|...|...#.....#||...|.####..#|....#|...#.
.|..#||..#....###|.|..#..#..#..#|#|#.....|...|....
|||#.|.|........|..|#.|..##.#.#..||#..#...#.#.#.|.
|..|..||.#.|##.|#|||..|.#........#..#|.|..#.|..#.|
.#|.#.|.|.#.|.##.....||#....|...#.||||.|......|.#.
..#|.....#..#|.||.|.#|.#..|.........|.#.|..#.|...#
|#....|#.|..#.....#.|||.||......#.........##|.#||#
##.##....|..#.|.||#.|..|...#.##......|...|.#|..#..
....|##......#|##.#|||.|.#.....|.|...|#..|.##.|...
.......##..#|###||....#...||#...#.#...|......#.##.
#|...#..#.|||#..#.......|..|..|.|.|..|.#|#..||....
.#.|.#.......|.....#|.||...|..##|...###.||......##
##..#..|.||.#...#.....#.......|....#..##.#...#..|.
|.#.||...|#.##|#.#.#.||....|##...|#.|.|..##.|..##.
|||....#|#.|##....#.|.....||......|###...#.|.|.#..
.|..#|#......##.|...........||.||.|#.#..#.......#.
#|.....##|#..#...|..#.|.......|#||.#|#.###.....|..
....|.#.|#..|.||.|.#.||#.#.||...|......||.#.....#.
##|.#.|...||.|..#..#|#..#|...#|...#....||##...#...
.|.|#|.#..##|#...#|..#|.....|......#|.............
.|.##....#.##....#.....|.|...##.....#.#||.#.||.|..
.#|#......#..#.|..#..|#.|###........#...#....#||||
|..........#|.|..#|.##..#.|#.|.||.#....||#...#|||.
.#...##.........#.........#.#..#.|##..#.|#.#.#...|
|#...|........|#.....#.||...|.#...#|..|....|##|||.
.|#.|....##.|##.|.|.........|..##.||.#.#...#.#.#|.
.|#..||#.#|#....##......|...|..#...#..|........||#
....||.....#.#..#.|.|#|..|..||..#.|.|.|#...#|..||.
|...||#....|.#.#..#.##...#...#.##|...#|||#|...#...
....#.||...###...|#...|.|####|.|......|.#|..#.#..#
|...|#.#.#.|.##..##........|..|.#.....|#.|.#.||.##
...|..#...|||.#.....|..##..|.#..|......|...||##.#.
||##.|||#.|...#|..||.#.###.#|#...##|#.|.|#..|...||
.|..|..|..|.......|...##.#..##.|.#|...#.|....##|..
#..##|.#.#.##.#|#|...|###..#|..|...#....#.#|..#|.|
.|.##..|...|...#..|...#|......#|.##......#|##.####
........|..||.......||.....#.###|.#...#.#.....|...
|.|.||#|..|..#.....|.#..#..|..|..#|.|.##..|.|..|#.
|.|#........|.#.#..|.|.#|...##.#...|.#|.#..|...#.|
|#.....#...###...##....#|.|#..|.....||......|.#..."""
