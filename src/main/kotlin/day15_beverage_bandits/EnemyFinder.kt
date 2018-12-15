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
        private val combatants: Set<Combatant>,
        private val potentialEnemy: Combatant) : Graph<BattleCoordinate, Move>() {
    override fun findNeighbours(vertex: BattleCoordinate): List<Move> {

        val map = Direction.values()
                .map { vertex.coordinate.next(it) }

        if (map.contains(potentialEnemy.position)) {
            return listOf(Move(vertex, BattleCoordinate(potentialEnemy.position)))
        }
        return map
                .filter { openSpaces.contains(it) }
                .map { Move(vertex, BattleCoordinate(it)) }
                .toList()
    }

}