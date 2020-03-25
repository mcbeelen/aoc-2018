package y2019.day18_maze_with_keys

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.HashMultimap
import com.google.common.collect.ListMultimap
import com.google.common.collect.Multimap
import util.grid.Direction
import util.grid.Grid
import util.grid.GridTracker
import util.grid.ScreenCoordinate
import util.grid.at
import util.grid.parseToGrid
import util.grid.plotGrid
import util.grid.search.Graph
import util.grid.search.Path
import util.grid.search.StepInGrid
import util.grid.search.VertexInGrid
import y2019.day18_maze_with_keys.MazeFields.DOOR
import y2019.day18_maze_with_keys.MazeFields.ENTRANCE
import y2019.day18_maze_with_keys.MazeFields.KEY
import y2019.day18_maze_with_keys.MazeFields.OPEN_PASSAGE
import y2019.day18_maze_with_keys.MazeFields.WALL
import java.util.ArrayDeque
import java.util.Queue


enum class MazeFields {
    DOOR,
    ENTRANCE,
    KEY,
    OPEN_PASSAGE,
    WALL,

}

class VaultCoordinate(coordinate: ScreenCoordinate, val path: Path<VaultCoordinate> = Path(emptyList())) : VertexInGrid<VaultCoordinate>(coordinate = coordinate)


fun parseToVault(maze: String) : Grid<MazeFields> {
    val gridOfVault = parseToGrid(maze) {
        when {
            it.isLetter() && it.isLowerCase() -> KEY
            it.isLetter() && it.isUpperCase() -> DOOR
            it == '#' -> WALL
            it == '@' -> ENTRANCE
            else -> OPEN_PASSAGE
        }
    }

    var deadEnds = findDeadEndInTheMaze(gridOfVault)

    while (deadEnds.isNotEmpty()) {
        deadEnds.forEach {
            gridOfVault[it] == WALL
        }
        deadEnds = findDeadEndInTheMaze(gridOfVault)
    }

    return gridOfVault

}

private fun findDeadEndInTheMaze(gridOfVault: Grid<MazeFields>): List<ScreenCoordinate> {
    return gridOfVault.keys
            .filter { gridOfVault.getValue(it) == OPEN_PASSAGE }
            .filter { isAdjacentToThreeWalls(it, gridOfVault) }
}

fun isAdjacentToThreeWalls(coordinate: ScreenCoordinate, gridOfVault: Grid<MazeFields>): Boolean {

    return countNumberOfAdjacentWalls(gridOfVault, coordinate)
            .count() == 3

}

private fun countNumberOfAdjacentWalls(gridOfVault: Grid<MazeFields>, coordinate: ScreenCoordinate) =
        Direction.values().map { gridOfVault.getValue(coordinate.next(it)) == WALL }

class UndergroundVault(private val maze: String) : Graph<VaultCoordinate, StepInGrid<VaultCoordinate>>() {
    private val grid: Grid<MazeFields> by lazy { parseToVault(maze) }


    private val keys: MutableMap<ScreenCoordinate, Char> = HashMap()
    private val doors: MutableMap<ScreenCoordinate, Char> = HashMap()

    val coordinateOfEntrance: ScreenCoordinate by lazy { grid.entries.first { it.value == ENTRANCE }.key }

    init {
        maze.trimIndent().lines().forEachIndexed { top, line ->
            line.forEachIndexed { left, c ->
                if (c.isLetter()) {
                    if (c.isLowerCase()) {
                        keys[at(left, top)] = c
                    } else {
                        doors[at(left, top)] = c
                    }
                }
            }
        }
    }

    override fun findNeighbours(vertex: VaultCoordinate): List<StepInGrid<VaultCoordinate>> {
        return Direction.values().map { vertex.coordinate.next(it) }
                .filter { grid[it] != WALL }
                .map { VaultCoordinate(it) }
                .map { StepInGrid(vertex, it) }
    }

    fun coordinatesOfKeys(): Set<ScreenCoordinate> = keys.keys
    fun coordinatesOfDoors(): Set<ScreenCoordinate> = doors.keys
    fun keyAt(keyCoordinate: ScreenCoordinate) = keys.getValue(keyCoordinate)
    fun doorAt(keyCoordinate: ScreenCoordinate) = doors.getValue(keyCoordinate)

}


class VaultExplorer(private val vault: UndergroundVault) : GridTracker<MazeFields>() {
    constructor(vaultDefinition: String) : this(vault = UndergroundVault(vaultDefinition))


    fun explore(): Int {
        currentPosition = vault.coordinateOfEntrance
        grid[currentPosition] = ENTRANCE

        val pathsToCoordinate: Multimap<ScreenCoordinate, Path<VaultCoordinate>> = findPathsToEachCoordinate()
        val blockedKeys: ListMultimap<Char, Char> = analyzePathsToFigureOutDoorBlockingAccessToKeys(pathsToCoordinate)

        blockedKeys.keySet().forEach { key ->
            print("Path to key '$key' blocked by: ")
            blockedKeys.get(key).forEach {
                print(it)
            }
        }

        println("________________________________________")

        return 0

    }

    private fun analyzePathsToFigureOutDoorBlockingAccessToKeys(pathsToCoordinate: Multimap<ScreenCoordinate, Path<VaultCoordinate>>): ListMultimap<Char, Char> {
        val blockedKeys: ListMultimap<Char, Char> = ArrayListMultimap.create()
        val coordinatesOfDoors = vault.coordinatesOfDoors()
        vault.coordinatesOfKeys().forEach { keyCoordinate ->
            val key = vault.keyAt(keyCoordinate)


            val paths = pathsToCoordinate.get(keyCoordinate)
            val shortestPath = paths.minBy { it.vertices.size }!!
            shortestPath.vertices.reversed().forEach {
                if (coordinatesOfDoors.contains(it.coordinate)) {
                    blockedKeys.put(key, vault.doorAt(it.coordinate))
                }
            }
            println()
        }
        return blockedKeys
    }

    private fun findPathsToEachCoordinate(): Multimap<ScreenCoordinate, Path<VaultCoordinate>> {
        val unvisitedVertices: Queue<VaultCoordinate> = ArrayDeque()
        val entrance = VaultCoordinate(currentPosition)
        unvisitedVertices.add(entrance)

        val pathsToCoordinate: Multimap<ScreenCoordinate, Path<VaultCoordinate>> = HashMultimap.create()
        while (unvisitedVertices.isNotEmpty()) {

            val coordinateToExplore = unvisitedVertices.poll()
            val foundNeighbours = vault.findNeighbours(coordinateToExplore)

            foundNeighbours.forEach {
                val destination = it.destination
                val path = coordinateToExplore.path.withAdditionalStep(destination)
                if (!pathsToCoordinate.containsKey(destination.coordinate)) {
                    val vaultCoordinate = VaultCoordinate(destination.coordinate, path)
                    unvisitedVertices.add(vaultCoordinate)
                }
                pathsToCoordinate.put(destination.coordinate, path)
            }
        }
        return pathsToCoordinate
    }

    override fun charFor(t: MazeFields) = toPlottableChar(t)


}

fun toPlottableChar(t: MazeFields): Char {
    return when (t) {
        DOOR -> ']'
        ENTRANCE -> '@'
        KEY -> '+'
        OPEN_PASSAGE -> ' '
        WALL -> ' '
    }
}


fun main() {
    val vault = UndergroundVault(MAZE_WITH_CLOSED_DOORS)

    val explorer = VaultExplorer(vault)
    explorer.explore()

}


internal const val MAZE_WITH_CLOSED_DOORS = """
#################################################################################
#...#.......#.....#.......#...#.........#.......#.......#..q............#...#...#
#.#.#.#.###.###.#.#.#####.#.#.#.#######.#####.#.###.###Y###.#######.#####I#.#.#.#
#.#.#.#...#.....#.#.....#.#.#...#...#...#.....#.....#.#.....#.......#...#.#...#.#
#.#.#####.#######.#####.#.#.#####.#.#.#.#.#####.#####.###.#######.###N#.#.#####.#
#.#....f#.....#.#.#.#...#.#...#...#...#.#...#...#...#...#.#.....#.#...#.#...#...#
#.#####.###.#.#.#.#.#.###.#####.###########.#####.#.#.###.#.###.###.###.###.#.###
#.....#...#.#.#.#...#...#.....#.#.......#...#...#.#.#.....#.#.#.......#.....#.F.#
#####.###.###.#.###.###.#####.#.#.#####.#.###.#.#.#.#######.#.#############.###.#
#...#.#.#.#...#.....#.....#...#.#...#...#...#.#.#.#.......#.#.....#.......#.#a..#
#.#.#.#.#.#.###.#####.#####.###.#.###.#.#.#.#.#.#.#######.#.#.#.###.#####.###.###
#.#...#.#...#.#...#...#.....#...#.#...#.#.#...#.#.....#...#.#.#...#.....#.....#.#
#.#####.#####.#.###.###.#######.#.#.###.#######.#.#####.#.#.#####.#####.#######.#
#...#.........#.#...#.#.......#...#.#.#.#.....#...#...#.#.#...........#.#.......#
###.#########.#.#.###.#######.#.###.#.#.#.###.#####.#.#.###########.###.#.#####.#
#...#.....#...#.#.......#...#.#.#...#.#.#.#.....#...#...#...#.......#...#...#...#
#.#.#.###.#.###.#######.#.###.#.#.###.#.#.#####.#.#####.#.#.#.#######.#####.#####
#.#.#...#.#.....#.#.......#...#.#.#.#...#...#.....#.....#.#.......#...#...#.....#
#.#####.#.###.###.#.#######.#####.#.#.###.#.#########.###.#########.###.#.#####.#
#.......#...#.....#.#.......#.....#...#.#.#.....#...#.#.#...#.....#.#...#.......#
#.#######.#.#######.#.#########.###.###.#.#####.#.#.#.#.###.#.###.#.#.#.#######.#
#...#...#.#.......#.#.#...L...#.#.#...#.#...#.#...#.#.....#.#.#.#.#.#.#...#...#.#
#.###.#.#####.###.#.#######.#.#.#.###.#.###.#.#####.#####.#.###.#.#.#####.#.#.###
#.#...#.....#...#.#.......#.#...#.......#.#.......#...#...#.#...#...#.....#.#...#
#.#.#######.###.#.#######.#.###########.#.#######.###.#.###.#.#.#####.#.###.###.#
#.#.#.#.....#...#...#.....#.........#...#.#...#.....#.#...#.#.#.....#.#.#...#.#.#
###.#.#.#########.###.###########.#.###.#.#.#.#.#####.###.#.#####.#.#.###.###.#.#
#...#...J.#.......#...#.........#.#...#.#...#...#.....#.#.#.#.....#...#...#s..#.#
#.#######.#.#####S#.#######.###.#.###.###.#######.#####.#.#.#.#####.###.#####.#.#
#.#.....#...#...#.#.#.....#.#.#.#...#...#...#.#...#.......#.#...#...#...#.....#.#
#.#T###.#####.#.###.###.#.#.#.#.#.#####.###.#.#.#########.#.###.#####.###.#####.#
#.#.#...#.....#.#...#...#...#...#.#...#.#.#.#.#.#.......#.#...#.......#.....#..w#
#.#.#.###.#####.#.###.#######.#####.#.#.#.#.#.#.#.#####.#####.#.###########.#.#.#
#...#.#.B.#...#.#.#.#...#.#...#.....#.#.#.#.#.#.#.#r..#.....#.#.#...#.......#.#.#
#.###.#.###.#.#.#.#.#.#.#.#.###.#####.#.#.#.#.#.#.#.#######.#.#.#.#.#.###.###.#.#
#...#.#...#.#.#...#...#.#.#.#.......#...#.#...#...#.......#.#.#.#.#.#.#...#...#.#
###.#.###.###.#########.#.#.#.#####.#####.###.#####.#####.#.#.#.#.#.#.#####.###.#
#...#...#...#.....W...#.#.#.#c#...#.....#...#.....#.#.....#...#...#...#...#...#.#
#.###.#####.###.#####.#.#.#.###.#.#####.#.#.#####.###.###.#############.#.###.#.#
#o..#...........#.......#.......#.........#...........#.................#.....#.#
#######################################.@.#######################################
#...#.....#...#.........#.............#...........#.....#............j....#...#.#
#.#.#P###.###.#.###.###.#.###.#######.#.#.#####.###.#.#.###.#.###.#######.#.#.#.#
#.#...#.#...#.#.#.#...#.#...#.#.....#.#.#.#...#.....#.#...#.#.#...#..t..#...#...#
#G#####.###.#.#.#.###.#.#.###.#.#.#.#.#.#.###.#######.###C###.#.###.###.###.#####
#.#...#...#.#.#.....#.#.#.#...#.#.#.#.#.#...#.....#...#.#...#.#.#...#...#...#...#
#.#.###.#.#.#.#####.#.#.###.#####.###.#.###.#.#####.###.###.#.###.###.#######.#.#
#.#.....#.#.#...#...#.#...#.....#...#.#.#.....#...#.#.....#.#.......#.#.......#.#
#.#######.#.#.###.###.###.#####.###.#.#.#.#####.#.#.#.#####.#.#####.#.#.#######.#
#.......#...#...#.#.#.#.#.....#...#...#.#...#...#.#.#.....#.#.#...#.#...#.......#
#.#####.#.#####.#.#.#.#.#####.###.#.###.#.###.###.#.#####.#.###.#.#######.#####.#
#.#...#.#.....#...#.#.#.....#.....#.#...#.#...#.#.#.....#...#...#.........#.....#
#.#.#.#.#####.#.###.#.#####.#######.#.#.#.#.###.#.#.###.#.###.#############.#####
#...#.#.#.....#.....#.....#...#.....#.#.#.#.#.....#.#.#.#.#...#...#.O.#...#b..#.#
#####.#.###.###.#########.###.#.#####.#.#.#.#.#####.#.#.#.#.###.#.###.#.#.###.#.#
#...#.#...#...#.#.........#...#...#...#.#.#.#...#.....#.#...#...#..d#.#.#...#.#.#
#.###.###.###.#.#.#########.#.###.#.###.###.###.#######.###.#.#####.#.#.#.###.#.#
#.#.X.#.#.#...#.#...#.#.....#...#.#...#.#...#.....#.....#...#.#...#.#...#...#...#
#.#.###.#.#.###.###.#.#.###.###.#.#.#.#.#.#.#####.#.#####.###.###.#.#######.###.#
#.......#.#.#.#...#.#...#.#.#...#.#.#.#.#.#.#...#.#.#...#...#...#k#.......#...#.#
#.#####.#.#.#.#.###.###.#.#.#####.#.#.###.###.#.#.#.#.#.#######.#.#######.#.#.#.#
#.#...#.#.#.#...#...#.#...#.#...#.#.#...#.#...#.#...#.#.....U.#.#.....#...#.#...#
#.#.#.###.#.#.###.#.#.###.#.#.#.#.#####.#.#.###.###.#.#########.#.#####.#########
#.#.#.....#.#.#...#.#.#...#...#...#...#.#.....#...#.#...#......h#.......#.......#
#.#.#######.###.###.#.#######.#####.#.#.#########.#.###.###.#####.#######.#####.#
#.#.#...#.....#.#...#u......#...#...#.R.#.......#.#.#...#...#...#........x#...#.#
#.#.#.#.#####.#.#.#########.###.#.#.###.#.#####.#.###.###.###.#Z###########.#.#.#
#.#...#...#.#...#...#...#.E.#.#.#.#.#...#...#...#...#.#.......#.#.....#....g#.#.#
#K#######.#.#####.###.#.#.###.#.#.#.#.#####.#.#####.#.#####.###.#.###.###.#####.#
#.#z....#.#.......#..v#.#...#.#.#.#.#...#...#.....#.#.#..e#...#.#...#...#.......#
#.#.###.#.#########.###.###.#.#.#.#.###.#.#######.#.#.#.#.###.#.#.#####.#.#######
#.#.#...#.......#...#.#.....#.#.#.#.#...#...#.......#...#...#.#...#...M.#.......#
#.###H#.#######.#.###.#######.#.###.#.###.#.###############V#######.###########.#
#...#.#.#.....#.#.....#.........#...#l#.#.#...#.........Q.#.........#.......#...#
###.#.###.#.###.#####.#.#####.###.###.#.#####.#.#######.#############.#####.#.###
#.#.#.....#.#.....#.#.#.#...#...#.#.....#.....#.#...#...#.......#.........#.#..p#
#.#.#######.#.###.#.#.###.#.###.#.#####.#.###.#.###.#.###.###.#.#.#######.#.###.#
#.#...D.....#.#.....#m....#.#...#...#.#.#y#...#.#...#n..#...#i#.#.#.......#.#...#
#.#########.#.#############.#.#####.#.#.#.#####.#.#.###.###.#.###.#.#########.###
#...........#...............#.......#...#.........#...#.....#.....#.......A.....#
#################################################################################    
"""