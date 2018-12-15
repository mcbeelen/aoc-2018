package day15_beverage_bandits

import util.grid.Direction
import util.grid.ScreenCoordinate
import util.grid.search.BreadthFirstSearchAlgorithm
import util.grid.search.Graph

class EnemyFinder(private val battlefield: Battlefield) : BreadthFirstSearchAlgorithm<BattleCoordinate, Move>(battlefield) {
    override fun exploreVertex(vertex: BattleCoordinate, callback: (Move) -> Unit) {
        battlefield.findNeighbours(vertex)
                .forEach {
                    run {
                        callback(it)
                    }
                }
    }
}
class Battlefield(
        private val openSpaces: Set<ScreenCoordinate>,
        private val combatants: Set<Combatant>) : Graph<BattleCoordinate, Move>() {
    override fun findNeighbours(vertex: BattleCoordinate): List<Move> {

        val map = Direction.values()
                .map { vertex.coordinate.next(it) }

        return map
                .filter { openSpaces.contains(it) }
                .filter { screenCoordinate -> combatants.none { combatant -> combatant.position.isAt(screenCoordinate) } }
                .map { Move(vertex, BattleCoordinate(it)) }
                .toList()
    }

}