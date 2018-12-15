package day15_beverage_bandits

import util.grid.ScreenCoordinate

const val ATTACK_POWER : Int = 3

data class Combatant(
        val type: CreatureType,
        val position: ScreenCoordinate,
        val hitPoints : Int = 200)

enum class CreatureType {
    ELF,
    GOBLIN
}
