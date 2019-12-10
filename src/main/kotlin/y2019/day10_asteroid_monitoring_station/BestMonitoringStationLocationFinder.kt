package y2019.day10_asteroid_monitoring_station

import util.grid.ScreenCoordinate
import util.grid.at
import util.grid.simplify
import kotlin.math.max

typealias NumberOfVisibleAsteroids = Int
typealias AsteroidMonitoringLocation = Pair<ScreenCoordinate, NumberOfVisibleAsteroids>
// typealias AsteroidMap = Map<ScreenCoordinate, Boolean>


data class AsteroidMap(val asteroids: Set<ScreenCoordinate>,
                       val width: Int,
                       val height: Int)

fun findBestMonitoringStationLocation(fieldDefinition: String): AsteroidMonitoringLocation {

    val asteroidMap = convertFieldToMap(fieldDefinition.trimIndent())
    val map = asteroidMap.asteroids.map { toAsteroidMonitoringLocation(it, asteroidMap) }
    return map.maxBy { it.second }!!
}

fun toAsteroidMonitoringLocation(coordinate: ScreenCoordinate, asteroidMap: AsteroidMap): AsteroidMonitoringLocation {
    val numberOfVisibleAsteroids = findNumberOfVisibleAsteroids(coordinate, asteroidMap)
    return AsteroidMonitoringLocation(coordinate, numberOfVisibleAsteroids)

}

fun findNumberOfVisibleAsteroids(coordinate: ScreenCoordinate, asteroidMap: AsteroidMap): NumberOfVisibleAsteroids {
    val visibleAsteroids : MutableSet<ScreenCoordinate> = HashSet(asteroidMap.asteroids)
    val maxNumberOfSteps = max(asteroidMap.height, asteroidMap.width)
    visibleAsteroids.remove(coordinate)
    asteroidMap.asteroids
            .filter{ ! it.isAt(coordinate)}
            .forEach {
        val vector = simplify(coordinate.vectorTo(it))
        for (step in 1 .. maxNumberOfSteps) {
            val hiddenFromSight = it.transpose(vector, step)
            visibleAsteroids.remove(hiddenFromSight)
        }
    }
    return visibleAsteroids.size
}

fun convertFieldToMap(fieldDefinition: String): AsteroidMap {

    val asteroids: MutableSet<ScreenCoordinate> = HashSet()

    val lines = fieldDefinition.lines()
    val height = lines.count()
    val width = lines.first().length
    lines.forEachIndexed { top, line ->

        line.forEachIndexed() { left, c ->
            if ('#' == c) {
                asteroids.add(at(left, top))
            }
        }

    }

    return AsteroidMap(asteroids, width, height)

}

fun main() {
    partOne()
}

private fun partOne() {
    val bestMonitoringStationLocation = findBestMonitoringStationLocation(ASTEROID_FIELD)
    println("${bestMonitoringStationLocation.second} asteroid are visible from ${bestMonitoringStationLocation.first}")
}


private const val ASTEROID_FIELD = """
#.#................#..............#......#......
.......##..#..#....#.#.....##...#.........#.#...
.#...............#....#.##......................
......#..####.........#....#.......#..#.....#...
.....#............#......#................#.#...
....##...#.#.#.#.............#..#.#.......#.....
..#.#.........#....#..#.#.........####..........
....#...#.#...####..#..#..#.....#...............
.............#......#..........#...........#....
......#.#.........#...............#.............
..#......#..#.....##...##.....#....#.#......#...
...#.......##.........#.#..#......#........#.#..
#.............#..........#....#.#.....#.........
#......#.#................#.......#..#.#........
#..#.#.....#.....###..#.................#..#....
...............................#..........#.....
###.#.....#.....#.............#.......#....#....
.#.....#.........#.....#....#...................
........#....................#..#...............
.....#...#.##......#............#......#.....#..
..#..#..............#..#..#.##........#.........
..#.#...#.......#....##...#........#...#.#....#.
.....#.#..####...........#.##....#....#......#..
.....#..#..##...............................#...
.#....#..#......#.#............#........##...#..
.......#.....................#..#....#.....#....
#......#..###...........#.#....#......#.........
..............#..#.#...#.......#..#.#...#......#
.......#...........#.....#...#.............#.#..
..##..##.............#........#........#........
......#.............##..#.........#...#.#.#.....
#........#.........#...#.....#................#.
...#.#...........#.....#.........#......##......
..#..#...........#..........#...................
.........#..#.......................#.#.........
......#.#.#.....#...........#...............#...
......#.##...........#....#............#........
#...........##.#.#........##...........##.......
......#....#..#.......#.....#.#.......#.##......
.#....#......#..............#.......#...........
......##.#..........#..................#........
......##.##...#..#........#............#........
..#.....#.................###...#.....###.#..#..
....##...............#....#..................#..
.....#................#.#.#.......#..........#..
#........................#.##..........#....##..
.#.........#.#.#...#...#....#........#..#.......
...#..#.#......................#...............#"""
