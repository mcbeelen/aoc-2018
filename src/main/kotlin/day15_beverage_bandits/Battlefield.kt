package day15_beverage_bandits

import util.grid.ScreenCoordinate


class Battlefield(
        val numberOfCompletedRoundsOfBattle: Int = 0,
        val openSpaces: Set<ScreenCoordinate>) {


    fun battleItOut(): Battlefield {
        return Battlefield(numberOfCompletedRoundsOfBattle = numberOfCompletedRoundsOfBattle + 1,
                openSpaces = emptySet())
    }

    fun sumOfHitPointsOfRemainingUnits() = combatantMap.keys.map { it.hitPoints }.sum()


    val combatantMap: MutableMap<Combatant, ScreenCoordinate> = HashMap()


}


fun parseIntoBattleField(battlefieldInput: String): Battlefield {

    val openSpaces: MutableSet<ScreenCoordinate> = HashSet()

    battlefieldInput.trimIndent().lines().withIndex()
            .map { line ->
                val y = line.index

                line.value.withIndex().forEach {

                    val x = it.index

                    when (it.value) {
                       in ".GE" -> openSpaces.add(ScreenCoordinate(x, y))


                    }
                }

            }

    return Battlefield(openSpaces = openSpaces)
}