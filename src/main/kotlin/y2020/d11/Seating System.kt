package y2020.d11

import arrow.core.Option
import arrow.core.Option.Companion.empty
import arrow.core.Option.Companion.just
import arrow.core.extensions.list.monadFilter.filterMap
import arrow.syntax.function.memoize
import util.grid.*
import y2020.d11.Position.*
import kotlin.system.measureTimeMillis

fun evolveUntilStableAndCountOccupiedSeats(input: String): Int {
    return evolveUntilStableAndCountOccupiedSeats(input) {
        partOne(it)
    }
}

fun countOccupiedSeatsInStableWaitingAreaPartTwo(input: String): Int {
    return evolveUntilStableAndCountOccupiedSeats(input) {
        partTwo(it)
    }
}

fun evolveUntilStableAndCountOccupiedSeats(input: String, tick: (WaitingArea) -> WaitingArea): Int {
    var waitingArea = WaitingArea(parseWaitingArea(input))
    var nextGen = tick(waitingArea)

    while (!nextGen.equals(waitingArea)) {
        waitingArea = nextGen
        nextGen = tick(nextGen)
    }

    return nextGen.grid.filter { it.value == OccupiedSeat }.count()
}

fun partOne(waitingArea: WaitingArea): WaitingArea {
    return evolveOnce(waitingArea) {
        nextStatePartOne(it, waitingArea)
    }
}

fun evolveOnce(waitingArea: WaitingArea, nextStateGenerator: (sc: ScreenCoordinate) -> Position): WaitingArea {
    val nextGenGrid = Grid<Position>()

    waitingArea.grid.forEach { sc, _ ->
        run {
            nextGenGrid[sc] = nextStateGenerator(sc)
        }
    }

    return WaitingArea(nextGenGrid)
}


fun partTwo(waitingArea: WaitingArea): WaitingArea {
    return evolveOnce(waitingArea) {
        nextStatePartTwo(it, waitingArea)
    }
}

fun nextStatePartOne(sc: ScreenCoordinate, waitingArea: WaitingArea): Position {
    return when (waitingArea.grid.getValue(sc)) {
        Floor -> Floor
        EmptySeat -> nextStateForEmptySeat(waitingArea, sc)
        OccupiedSeat -> nextStateForOccupiedSeat(waitingArea, sc)
    }
}

fun nextStateForOccupiedSeat(waitingArea: WaitingArea, sc: ScreenCoordinate): Position {
    val adjacentSeats = waitingArea.getAdjacentSeats(sc)
    val numberOfOccupiedAdjacentSeats = adjacentSeats.filter { it == OccupiedSeat }.count()
    return if (numberOfOccupiedAdjacentSeats >= 4) {
        EmptySeat
    } else {
        OccupiedSeat
    }
}

private fun nextStateForEmptySeat(waitingArea: WaitingArea, sc: ScreenCoordinate): Position {
    val adjacentSeats = waitingArea.getAdjacentSeats(sc)
    return if (adjacentSeats.all { it == EmptySeat || it == Floor }) {
        OccupiedSeat
    } else {
        EmptySeat
    }
}


fun nextStatePartTwo(screenCoordinate: ScreenCoordinate, waitingArea: WaitingArea): Position {
    return when (waitingArea.grid.getValue(screenCoordinate)) {
        Floor -> Floor
        EmptySeat -> nextStateForEmptySeatInPartTwo(waitingArea, screenCoordinate)
        OccupiedSeat -> nextStateForOccupiedSeatInPartTwo(waitingArea, screenCoordinate)
    }
}

fun nextStateForEmptySeatInPartTwo(waitingArea: WaitingArea, screenCoordinate: ScreenCoordinate): Position {
    val adjacentSeatsInLineOfSight = findAdjacentSeatsInLineOfSight()(waitingArea, screenCoordinate)
    return if (adjacentSeatsInLineOfSight.all {
            val occupancyOfSeat = waitingArea.grid.getValue(it)
            occupancyOfSeat == EmptySeat || occupancyOfSeat == Floor }) {
        OccupiedSeat
    } else {
        EmptySeat
    }
}

fun nextStateForOccupiedSeatInPartTwo(waitingArea: WaitingArea, screenCoordinate: ScreenCoordinate): Position {
    val adjacentSeatsInLineOfSight = findAdjacentSeatsInLineOfSight()(waitingArea, screenCoordinate)
    val numberOfOccupiedSeatsInLineOfSight = waitingArea.grid.filter { adjacentSeatsInLineOfSight.contains(it.key) }
        .filter { it.value == OccupiedSeat }
        .count()

    return if (numberOfOccupiedSeatsInLineOfSight >= 5) {
        EmptySeat
    } else {
        OccupiedSeat
    }
}

fun findAdjacentSeatsInLineOfSight(): (wa: WaitingArea, sc: ScreenCoordinate) -> List<ScreenCoordinate> {
    return { wa: WaitingArea, sc: ScreenCoordinate -> wa.findSeatsInLineOfSight(sc) }.memoize()
}




data class WaitingArea(val grid: Grid<Position>) {
    override fun equals(other: Any?): Boolean {
        if (other != null && other is WaitingArea) {
            return occupanceIstheSame(other)
        }
        return false
    }

    private fun occupanceIstheSame(other: WaitingArea) = grid.all {
        it.value == other.grid[it.key]
    }

    override fun hashCode(): Int {
        return grid.filter { it.value == OccupiedSeat }.count()
    }

    fun getAdjacentSeats(sc: ScreenCoordinate): List<Position> {
        val adjacentCoordinates = sc.allAdjacentCoordinates()
        return this.grid.filter { adjacentCoordinates.contains(it.key) }
            .map { it.value }
    }

    fun findSeatsInLineOfSight(screenCoordinate: ScreenCoordinate): List<ScreenCoordinate> {
        return EIGHT_COMPASS_DIRECTIONS
                .map {  findFirstSeat(screenCoordinate, it)}
                .filterMap { it }
    }

    private tailrec fun findFirstSeat(screenCoordinate: ScreenCoordinate, vector: Vector): Option<ScreenCoordinate> {
        val nextCoordinate = screenCoordinate.next(vector)
        if (!grid.containsKey(nextCoordinate)) {
            return empty()
        }
        if (grid.getValue(nextCoordinate) != Position.Floor) {
            return just(nextCoordinate)
        }
        return findFirstSeat(nextCoordinate, vector)
    }
}

private fun parseWaitingArea(input: String) = parseToGrid(input) {
    when (it) {
        '.' -> Floor
        'L' -> EmptySeat
        else -> OccupiedSeat
    }
}

enum class Position(val c: Char) {
    Floor('.'),
    EmptySeat('L'),
    OccupiedSeat('#')
}


fun main() {
    val ms = measureTimeMillis {
        println(evolveUntilStableAndCountOccupiedSeats(y2020d11waitingAreaInput))
    }
    println("Solved in ${ms}ms")
}
