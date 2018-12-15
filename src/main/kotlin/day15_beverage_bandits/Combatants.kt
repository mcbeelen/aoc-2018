package day15_beverage_bandits

import util.grid.CoordinatesInReadingOrder
import util.grid.ScreenCoordinate

const val ATTACK_POWER : Int = 3

data class Combatant(
        val type: CreatureType,
        val position: ScreenCoordinate,
        val hitPoints : Int = 200)


class CombatantInBattleOrder : Comparator<Combatant> {

    private val coordinatesInReadingOrder = CoordinatesInReadingOrder()

    override fun compare(any: Combatant, other: Combatant) = coordinatesInReadingOrder.compare(any.position, other.position)
}



enum class CreatureType {
    ELF,
    GOBLIN;

}
