package y2018.day20_regular_map

import y2018.day20_regular_map.Bearing.*
import util.grid.Direction
import util.grid.Direction.*
import java.util.*

class Route() {
    private val sections: MutableList<Section> = ArrayList()

    fun addSection(section: Section) {
        sections.add(section)
    }

    fun getDirections() : List<Section> = sections

}

interface Section

class Step(val bearing: Bearing) : Section

class Branch() : Section {
    private val options: MutableList<Route> = ArrayList()

    fun addOption(route: Route) = options.add(route)

    fun getRoutes() : List<Route> = options
}


class ParserState() {

    val routeStack = Stack<Route>()
    val branchStack = Stack<Branch>()

    var buildRoute : Route? = null

    fun startSection() {
        routeStack.push(Route())
    }

    fun startNewBranch() {
        routeStack.push(Route())
        branchStack.push(Branch())
    }


    fun startNewOptions() {
        val route = routeStack.pop()
        branchStack.peek()!!.addOption(route)

        startSection()

    }

    fun endBranch() {

        val route = routeStack.pop()
        val activeBranch = branchStack.pop()
        activeBranch.addOption(route)

        routeStack.peek()!!.addSection(activeBranch)

    }

    fun endRoute() {

        buildRoute = routeStack.pop()
    }

    fun addStep(bearing: Bearing) = routeStack.peek()!!.addSection(Step(bearing))
}


fun parseRoute(regularDirections: String): Route {

    val parserState = ParserState()

    regularDirections.forEachIndexed { index, char ->
        when (char) {
            START_ROUTE -> parserState.startSection()

            START_BRANCH -> parserState.startNewBranch()

            OPTION_SEPARATOR -> parserState.startNewOptions()

            END_BRANCH -> parserState.endBranch()


            END_ROUTE -> parserState.endRoute()

            else -> parserState.addStep(parseBearing(char))

        }
    }

    return parserState.buildRoute!!
}



enum class Bearing(val direction: Direction) {
    NORTH(UP),
    EAST(RIGHT),
    SOUTH(DOWN),
    WEST(LEFT)
}

const val START_ROUTE = '^'
const val END_ROUTE = '$'
const val START_BRANCH = '('
const val END_BRANCH = ')'
const val OPTION_SEPARATOR = '|'

fun parseBearing(char: Char): Bearing {
    return when (char) {
        'N' -> NORTH
        'E' -> EAST
        'S' -> SOUTH
        'W' -> WEST
        else -> throw IllegalArgumentException("${char} does not indicate a valid direction")

    }
}

