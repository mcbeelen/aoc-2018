package y2018.day15_beverage_bandits

import util.grid.ScreenCoordinate
import util.grid.search.Path

data class Combatant(
        val type: CreatureType,
        val position: ScreenCoordinate,
        val hitPoints : Int = 200) {

    fun isAdjacentTo(enemy: Combatant) = position.isAdjacentTo(enemy.position)
    fun isAt(coordinate: ScreenCoordinate) = position == coordinate

}


class CombatantInBattleOrder : Comparator<Combatant> {
    override fun compare(any: Combatant, other: Combatant) = coordinatesInReadingOrder.compare(any.position, other.position)
}

class CombatantWithPathInBattleOrder : Comparator<Pair<Combatant, Path<BattleCoordinate>?>> {
    private val combatantInBattleOrder = CombatantInBattleOrder()
    override fun compare(any: Pair<Combatant, Path<BattleCoordinate>?>, other: Pair<Combatant, Path<BattleCoordinate>?>): Int {
        return combatantInBattleOrder.compare(any.first, other.first)
    }

}



enum class CreatureType(val attackPower: Int) {
    ELF(17),
    GOBLIN(3);



}
