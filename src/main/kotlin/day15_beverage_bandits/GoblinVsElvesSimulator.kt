package day15_beverage_bandits

import day15_beverage_bandits.CreatureType.ELF
import day15_beverage_bandits.CreatureType.GOBLIN
import mu.KotlinLogging
import util.grid.ScreenCoordinate
import util.grid.search.Path

val combatantInBattleOrder = CombatantInBattleOrder()

class GoblinVsElvesSimulator(
        val numberOfCompletedRoundsOfBattle: Int = 0,
        val openSpaces: Set<ScreenCoordinate>,
        internal val combatants: Set<Combatant>,
        internal val activeCombatantIndex: Int = 0) {

    private val logger = KotlinLogging.logger { }

    val activeCombatant: Combatant by lazy {
        findActiveCombatant()
    }


    fun battleItOut(): GoblinVsElvesSimulator {
        return GoblinVsElvesSimulator(numberOfCompletedRoundsOfBattle = numberOfCompletedRoundsOfBattle + 1,
                openSpaces = emptySet(),
                combatants = emptySet())
    }

    fun sumOfHitPointsOfRemainingUnits() = combatants.map { it.hitPoints }.sum()
    fun findPositionOfAllElves() = findPositionOfAll(ELF)
    fun findPositionOfAllGoblins() = findPositionOfAll(GOBLIN)

    private fun findPositionOfAll(wantedType: CreatureType) =
            combatants.filter { it.type == wantedType }.map { it.position }.asIterable()

    fun getUnitsInOrderForTakingTurns(): List<Combatant> {

        return combatants.sortedWith(combatantInBattleOrder)
    }


    fun findActiveCombatant(): Combatant = getUnitsInOrderForTakingTurns()[activeCombatantIndex]

    fun identifyPossibleTargetsForActiveCombatant(): List<Combatant> {

        val enemies = findEnemiesOf(activeCombatant)

        return enemies.sortedWith(combatantInBattleOrder).toList()

    }

    private fun findEnemiesOf(combatant: Combatant): Iterable<Combatant> {
        return when (combatant.type) {
            ELF -> combatants.filter { it.type == GOBLIN }
            GOBLIN -> combatants.filter { it.type == ELF }
        }
    }

    fun findTargetForActiveCombatant(): Target {

        val possibleTargets = identifyPossibleTargetsForActiveCombatant()
                .filter { activeCombatantCanReach(it) }
                .map { Pair(it, pathTo(it)) }
                .sortedBy { it.second.calculateDistance() }


        logger.info("Found ${possibleTargets.size} possible targets")

        val shortestPath = possibleTargets.first().second.calculateDistance()

        val targetEnemy = possibleTargets
                .filter { it.second.calculateDistance() == shortestPath }
                .map { it.first }
                .sortedWith(combatantInBattleOrder)
                .first()



        return Target(targetEnemy, pathTo(targetEnemy))

    }

    private fun pathTo(enemy: Combatant) : Path<BattleCoordinate> {

        val caves = Battlefield(openSpaces, combatants, enemy)
        val enemyFinder = EnemyFinder(caves)
        return enemyFinder.findShortestPath(BattleCoordinate(activeCombatant.position), BattleCoordinate(enemy.position))
    }



    private fun activeCombatantCanReach(it: Combatant): Boolean {
        return true
    }

}







data class Target(val enemy: Combatant, val path: Path<BattleCoordinate>)


fun parseIntoBattleField(battlefieldInput: String): GoblinVsElvesSimulator {

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

    return GoblinVsElvesSimulator(openSpaces = openSpaces, combatants = combatants)

}

