package day15_beverage_bandits

import util.grid.ScreenCoordinate


class Battlefield(
        val numberOfCompletedRoundsOfBattle: Int = 0) {


    fun battleItOut(): Battlefield {
        return Battlefield(numberOfCompletedRoundsOfBattle = numberOfCompletedRoundsOfBattle + 1)
    }

    fun sumOfHitPointsOfRemainingUnits() = combatantMap.keys.map { it.hitPoints }.sum()


    val openSpace: MutableSet<ScreenCoordinate> = HashSet()

    val combatantMap: MutableMap<Combatant, ScreenCoordinate> = HashMap()


}


fun parseIntoBattleField(battlefieldInput: String): Battlefield {
    return Battlefield()
}