package day15_beverage_bandits

import util.grid.CoordinatesInReadingOrder
import util.grid.ScreenCoordinate
import util.grid.search.Path

const val ATTACK_POWER : Int = 3

data class Combatant(
        val type: CreatureType,
        val position: ScreenCoordinate,
        val hitPoints : Int = 200) {

    fun isAdjacentTo(enemy: Combatant) = position.isAdjacentTo(enemy.position)


}


class CombatantInBattleOrder : Comparator<Combatant> {
    private val coordinatesInReadingOrder = CoordinatesInReadingOrder()
    override fun compare(any: Combatant, other: Combatant) = coordinatesInReadingOrder.compare(any.position, other.position)
}

class CombatantWithPathInBattleOrder : Comparator<Pair<Combatant, Path<BattleCoordinate>?>> {
    private val combatantInBattleOrder = CombatantInBattleOrder()
    override fun compare(any: Pair<Combatant, Path<BattleCoordinate>?>, other: Pair<Combatant, Path<BattleCoordinate>?>): Int {
        return combatantInBattleOrder.compare(any.first, other.first)
    }

}



enum class CreatureType {
    ELF,
    GOBLIN;

}
