package y2018.day15_beverage_bandits

import util.grid.ScreenCoordinate
import util.grid.search.BreadthFirstSearchAlgorithm
import util.grid.search.Graph
import util.grid.search.Path

class EnemyFinder(private val battlefield: Battlefield) : BreadthFirstSearchAlgorithm<BattleCoordinate, Move>(battlefield) {
    override fun exploreVertex(vertex: BattleCoordinate, callback: (Move) -> Unit) {
        battlefield.findNeighbours(vertex)
                .forEach {
                    run {
                        callback(it)
                    }
                }
    }

    fun findShortestPath(origin: ScreenCoordinate, destination: ScreenCoordinate): Path<BattleCoordinate>? {
        return findShortestPath(BattleCoordinate(origin), BattleCoordinate(destination))
    }

}
class Battlefield(
        private val openSpaces: Set<ScreenCoordinate>,
        private val combatants: Collection<Combatant>) : Graph<BattleCoordinate, Move>() {
    override fun findNeighbours(vertex: BattleCoordinate): List<Move> {

        val map = DIRECTION_IN_READING_ORDER
                .map { vertex.coordinate.next(it) }

        val possibleMoves = map
                .filter { openSpaces.contains(it) }
                .filter { screenCoordinate -> combatants.none { combatant -> combatant.position.isAt(screenCoordinate) } }
                .map { Move(vertex, BattleCoordinate(it)) }
                .toList()
        return possibleMoves
    }

}