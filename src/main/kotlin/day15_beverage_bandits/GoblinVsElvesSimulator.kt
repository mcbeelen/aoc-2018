package day15_beverage_bandits

import day15_beverage_bandits.CreatureType.ELF
import day15_beverage_bandits.CreatureType.GOBLIN
import mu.KotlinLogging
import util.grid.ScreenCoordinate
import util.grid.search.Path

val combatantInBattleOrder = CombatantInBattleOrder()
val combatantWithPathOrder = CombatantWithPathInBattleOrder()

val logger = KotlinLogging.logger { }

data class GoblinVsElvesSimulator(
        val numberOfCompletedRoundsOfBattle: Int = 0,
        val openSpaces: Set<ScreenCoordinate>,
        internal val combatants: Set<Combatant>,
        internal val activeCombatantIndex: Int = 0) {


    val activeCombatant: Combatant by lazy {
        findActiveCombatant()
    }

    fun sumOfHitPointsOfRemainingUnits() = combatants.map { it.hitPoints }.sum()


    fun findPositionOfAllElves() = findPositionOfAll(ELF)
    fun findPositionOfAllGoblins() = findPositionOfAll(GOBLIN)

    private fun findPositionOfAll(wantedType: CreatureType) =
            combatants.filter { it.type == wantedType }.map { it.position }.asIterable()

    fun getUnitsInOrderForTakingTurns() = combatants.sortedWith(combatantInBattleOrder)


    private fun findActiveCombatant(): Combatant = getUnitsInOrderForTakingTurns()[activeCombatantIndex]

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

    fun findTargetForActiveCombatant(): Target? {

        val possibleTargets = identifyPossibleTargetsForActiveCombatant()
                .map { activeCombatantCanReach(it) }
                .filter { it.first }
                .map { Pair(it.second, it.third) }
                .sortedBy { it.second!!.calculateDistance() }

        if (possibleTargets.isEmpty()) {
            return null
        }
        val shortestPath = possibleTargets.first().second!!.calculateDistance()

        val targetEnemy = possibleTargets
                .filter { it.second!!.calculateDistance() == shortestPath }
                .sortedWith(combatantWithPathOrder)
                .first()

        return Target(targetEnemy.first, targetEnemy.second!!)

    }

    private fun activeCombatantCanReach(potentialEnemy: Combatant): Triple<Boolean, Combatant, Path<BattleCoordinate>?> {
        val pathTo = pathTo(potentialEnemy) ?: return Triple(false, potentialEnemy, null)
        return Triple(true, potentialEnemy, pathTo)
    }

    private fun pathTo(enemy: Combatant): Path<BattleCoordinate>? {

        val caves = Battlefield(openSpaces, combatants.minus(enemy))
        val enemyFinder = EnemyFinder(caves)
        return enemyFinder.findShortestPath(BattleCoordinate(activeCombatant.position), BattleCoordinate(enemy.position))
    }


    fun someCombatantMayTakeItsTurnThisRound(): Boolean = this.theBattleIsntOver()
            && this.activeCombatantIndex < combatants.size

    fun theBattleIsntOver(): Boolean = combatants.map { it.type }.toSet().size > 1

    fun withNextCombatantActive(): GoblinVsElvesSimulator = copy(activeCombatantIndex = activeCombatantIndex + 1)
}


fun battleItOut(originalSituation: GoblinVsElvesSimulator): GoblinVsElvesSimulator {

    var updatedSituation = originalSituation

    while (updatedSituation.theBattleIsntOver()) {
        while (updatedSituation.someCombatantMayTakeItsTurnThisRound()) {
            updatedSituation = playOneTurn(updatedSituation)
            updatedSituation = updatedSituation.withNextCombatantActive()
        }

        val numberOfCompletedRoundsOfBattle = updatedSituation.numberOfCompletedRoundsOfBattle + 1
        logger.info("Starting next round (${numberOfCompletedRoundsOfBattle})")
        updatedSituation = updatedSituation.copy(
                numberOfCompletedRoundsOfBattle = numberOfCompletedRoundsOfBattle,
                activeCombatantIndex = 0)

        plot(updatedSituation)

    }

    return updatedSituation
}

fun plot(situation: GoblinVsElvesSimulator) {

    println("Situation after ${situation.numberOfCompletedRoundsOfBattle}:")
    println()

    for (y in 0..6) {
        for (x in 0..6) {
            when {
                isOccupied(x, y, situation.combatants) -> printOccupant(x, y, situation.combatants)
                situation.openSpaces.contains(ScreenCoordinate(x, y)) -> print('.')
                else -> print('#')
            }
        }

        for (x in 0..6) {
            if (isOccupied(x, y, situation.combatants)) {
                printHealthInfo(x, y, situation.combatants)
            }
        }


        println()
    }
    println()
    println()
    println()


}

fun printHealthInfo(x: Int, y: Int, combatants: Set<Combatant>) {
    val combatant = findCombatantAt(combatants, x, y)
    print(message = " ${combatant.type.name[0]}(${combatant.hitPoints})")
}

fun printOccupant(x: Int, y: Int, combatants: Set<Combatant>) {
    print(findCombatantAt(combatants, x, y).type.name[0])
}

private fun findCombatantAt(combatants: Set<Combatant>, x: Int, y: Int) =
        combatants.single { it.position.isAt(x, y) }

fun isOccupied(x: Int, y: Int, combatants: Set<Combatant>) = combatants.any { it.position.isAt(x, y) }


private fun playOneTurn(originalSituation: GoblinVsElvesSimulator): GoblinVsElvesSimulator {
    var updatedSituation = originalSituation
    val target = originalSituation.findTargetForActiveCombatant()

    if (target != null) {
        var (enemy, path) = target
        var activeCombatant = updatedSituation.activeCombatant
        if (!activeCombatant.isAdjacentTo(enemy)) {
            val pair = moveActiveCombatant(activeCombatant, path, updatedSituation)
            activeCombatant = pair.first
            updatedSituation = pair.second
        }


        if (activeCombatant.isAdjacentTo(enemy)) {

            enemy = findAdjacentEnemyWithFewestHitPoints(updatedSituation)

            val combatants = updatedSituation.combatants
            println("${activeCombatant.type} at ${activeCombatant.position} is going to hit ${enemy.type} at ${enemy.position} with ${enemy.hitPoints}")
            if (enemy.hitPoints > ATTACK_POWER) {
                // Register hit
                val updatedEnemy = enemy.copy(hitPoints = enemy.hitPoints - ATTACK_POWER)
                println("              --> Hit power reduced to ${updatedEnemy.hitPoints}")
                val updatedCombatants = combatants.minus(enemy).plus(updatedEnemy)

                updatedSituation = updatedSituation.copy(combatants = updatedCombatants)

            } else {
                // Target enemy dies
                println("   ${enemy.type} at ${enemy.position} dies in round: ${originalSituation.numberOfCompletedRoundsOfBattle}")
                updatedSituation = updatedSituation.copy(combatants = combatants.minus(enemy))
            }
        }
        return updatedSituation
    }

    return updatedSituation

}

fun findAdjacentEnemyWithFewestHitPoints(situation: GoblinVsElvesSimulator): Combatant {

    return situation.combatants
            .filter { it.type != situation.activeCombatant.type }
            .filter { it.isAdjacentTo(situation.activeCombatant) }
            .minBy { it.hitPoints }!!

}

private fun moveActiveCombatant(activeCombatant: Combatant, path: Path<BattleCoordinate>, updatedSituation: GoblinVsElvesSimulator): Pair<Combatant, GoblinVsElvesSimulator> {
    // move
    var activeCombatant1 = activeCombatant
    var updatedSituation1 = updatedSituation
    val movedCombatant = activeCombatant1.copy(position = path.vertices[1].coordinate)

    updatedSituation1 = updatedSituation1.copy(combatants = updatedSituation1.combatants.minus(activeCombatant1).plus(movedCombatant))
    activeCombatant1 = movedCombatant
    return Pair(activeCombatant1, updatedSituation1)
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

