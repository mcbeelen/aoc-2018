package day15_beverage_bandits

import util.grid.ScreenCoordinate
import day15_beverage_bandits.CreatureType.*

class Battlefield(
        val numberOfCompletedRoundsOfBattle: Int = 0,
        val openSpaces: Set<ScreenCoordinate>,
        private val combatants: Set<Combatant>) {


    fun battleItOut(): Battlefield {
        return Battlefield(numberOfCompletedRoundsOfBattle = numberOfCompletedRoundsOfBattle + 1,
                openSpaces = emptySet(),
                combatants = emptySet())
    }

    fun sumOfHitPointsOfRemainingUnits() = combatants.map { it.hitPoints }.sum()
    fun findPositionOfAllElves() = findPositionOfAll(ELF)
    fun findPositionOfAllGoblins() = findPositionOfAll(GOBLIN)

    private fun findPositionOfAll(wantedType: CreatureType) =
            combatants.filter { it.type == wantedType }.map { it.position }.asIterable()

}



fun parseIntoBattleField(battlefieldInput: String): Battlefield {

    val openSpaces: MutableSet<ScreenCoordinate> = HashSet()
    val combatants: MutableSet<Combatant> = HashSet()

    battlefieldInput.trimIndent().lines().withIndex()
            .map { line ->
                val y = line.index

                line.value.withIndex().forEach {

                    val x = it.index
                    val coordinate = ScreenCoordinate(x, y)

                    when (it.value) {
                        '.' -> openSpaces.add(coordinate)
                        'G' -> {
                            combatants.add(Combatant(GOBLIN, coordinate))
                            openSpaces.add(coordinate)
                        }
                        'E' -> {
                            combatants.add(Combatant(ELF, coordinate))
                            openSpaces.add(coordinate)
                        }
                    }

                }
            }

    return Battlefield(openSpaces = openSpaces, combatants = combatants)

}

