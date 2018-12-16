package day15_beverage_bandits

import day15_beverage_bandits.CreatureType.ELF
import day15_beverage_bandits.CreatureType.GOBLIN
import util.grid.CoordinatesInReadingOrder
import util.grid.Direction.*
import util.grid.ScreenCoordinate
import util.grid.search.Path

val coordinatesInReadingOrder = CoordinatesInReadingOrder()
val combatantInBattleOrder = CombatantInBattleOrder()
val combatantWithPathOrder = CombatantWithPathInBattleOrder()

val DIRECTION_IN_READING_ORDER = listOf(UP, LEFT, RIGHT, DOWN)


data class GoblinVsElvesSimulator(
        val numberOfCompletedRoundsOfBattle: Int = 0,
        val openSpaces: Set<ScreenCoordinate>,
        internal val combatants: Set<Combatant>,
        val listCombatantsThisRound: List<Combatant>,
        internal val activeCombatantIndex: Int = 0) {


    val activeCombatant: Combatant by lazy {
        listCombatantsThisRound[activeCombatantIndex]
    }

    fun sumOfHitPointsOfRemainingUnits() = combatants.map { it.hitPoints }.sum()


    fun findPositionOfAllElves() = findPositionOfAll(ELF)
    fun findPositionOfAllGoblins() = findPositionOfAll(GOBLIN)

    private fun findPositionOfAll(wantedType: CreatureType) =
            combatants.filter { it.type == wantedType }.map { it.position }.asIterable()

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

    fun findSquaresInRangeOfEnemiesOfActiveCompatant(): List<ScreenCoordinate> {

        val enemies = findEnemiesOf(activeCombatant)

        val squaresInRangeOfAnEnemy = mutableListOf<ScreenCoordinate>()
        for (enemy in enemies) {
            for (direction in DIRECTION_IN_READING_ORDER) {

                val potentialTargetSquare = enemy.position.next(direction)
                if (openSpaces.contains(potentialTargetSquare) && combatants.none { it.isAt(potentialTargetSquare) }) {
                    squaresInRangeOfAnEnemy.add(potentialTargetSquare)
                }
            }
        }
        return squaresInRangeOfAnEnemy.sortedWith(coordinatesInReadingOrder)


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

        val battlefield = Battlefield(openSpaces, combatants.minus(enemy))
        val enemyFinder = EnemyFinder(battlefield)
        return enemyFinder.findShortestPath(BattleCoordinate(activeCombatant.position), BattleCoordinate(enemy.position))
    }


    private fun shortestPathTo(destination: ScreenCoordinate): Path<BattleCoordinate>? {
        val battlefield = Battlefield(openSpaces, combatants)
        val enemyFinder = EnemyFinder(battlefield)
        return enemyFinder.findShortestPath(activeCombatant.position, destination)
    }


    fun someCombatantMayTakeItsTurnThisRound(): Boolean = this.activeCombatantIndex < listCombatantsThisRound.size

    fun theBattleIsntOver(): Boolean = combatants.map { it.type }.toSet().size > 1

    fun withNextCombatantActive(): GoblinVsElvesSimulator = copy(activeCombatantIndex = activeCombatantIndex + 1)
    fun isActiveCombatantStillAlive() =
            combatants.any { it.type == activeCombatant.type && it.position == activeCombatant.position }

    fun findSquareToMoveToAlongShortestPathToClosestTarget(): ScreenCoordinate? {

        val squaresInRangeOfEnemies = findSquaresInRangeOfEnemiesOfActiveCompatant()
        if (squaresInRangeOfEnemies.isEmpty()) {
            return null
        }


        val reachableSquaresWithShortestPath = squaresInRangeOfEnemies
                .associateWith { shortestPathTo(it) }
                .filter { it.value != null }

        if (reachableSquaresWithShortestPath.isEmpty()) {
            return null
        }


        val numberOfStepsToClosestTargetSquare = reachableSquaresWithShortestPath.values
                .map { it!!.calculateDistance() }
                .min()

        val targetSquareReachableWithFewestNumberOfSteps = reachableSquaresWithShortestPath
                .filter { it.value!!.calculateDistance() == numberOfStepsToClosestTargetSquare }


        val chosenTargetSquare = targetSquareReachableWithFewestNumberOfSteps
                .toSortedMap(coordinatesInReadingOrder)
                .firstKey()

        return targetSquareReachableWithFewestNumberOfSteps[chosenTargetSquare]!!.vertices[1].coordinate


    }



}


fun battleItOut(originalSituation: GoblinVsElvesSimulator): GoblinVsElvesSimulator {

    var updatedSituation = originalSituation

    while (updatedSituation.theBattleIsntOver()) {
        while (updatedSituation.someCombatantMayTakeItsTurnThisRound() && updatedSituation.theBattleIsntOver()) {

            if (updatedSituation.isActiveCombatantStillAlive()) {
                updatedSituation = playOneTurn(updatedSituation).withNextCombatantActive()
            } else {
                // skip the dead player
                updatedSituation = updatedSituation.withNextCombatantActive()

            }
        }

        if (updatedSituation.theBattleIsntOver()) {
            val numberOfCompletedRoundsOfBattle = updatedSituation.numberOfCompletedRoundsOfBattle + 1
            updatedSituation = updatedSituation.copy(
                    numberOfCompletedRoundsOfBattle = numberOfCompletedRoundsOfBattle,
                    activeCombatantIndex = 0,
                    listCombatantsThisRound = updatedSituation.combatants.sortedWith(combatantInBattleOrder))
        } else {
            if (!updatedSituation.someCombatantMayTakeItsTurnThisRound()) {
                val numberOfCompletedRoundsOfBattle = updatedSituation.numberOfCompletedRoundsOfBattle + 1
                updatedSituation = updatedSituation.copy(numberOfCompletedRoundsOfBattle = numberOfCompletedRoundsOfBattle)
            }
        }

        plot(updatedSituation)


    }

    return updatedSituation
}


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

            enemy = findAdjacentEnemyWithFewestHitPoints(updatedSituation, activeCombatant)

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

fun findAdjacentEnemyWithFewestHitPoints(situation: GoblinVsElvesSimulator, activeCombatant: Combatant): Combatant {

    val adjacentEnemies = situation.combatants
            .filter { it.type != activeCombatant.type }
            .filter { it.isAdjacentTo(activeCombatant) }

    val fewestNumberOfHitPoints = adjacentEnemies.map { it.hitPoints }.min()!!

    return adjacentEnemies
            .filter { it.hitPoints == fewestNumberOfHitPoints }
            .sortedWith(combatantInBattleOrder)
            .first()

}

private fun moveActiveCombatant(currentCombatant: Combatant, path: Path<BattleCoordinate>, currentSituation: GoblinVsElvesSimulator): Pair<Combatant, GoblinVsElvesSimulator> {
    // move
    var updatableCombatant = currentCombatant
    val nextPosition = path.vertices[1].coordinate
    println("${currentCombatant.type} at ${currentCombatant.position} moves to ${nextPosition}")
    val movedCombatant = currentCombatant.copy(position = nextPosition)

    val updatedCombatants = currentSituation.combatants.minus(updatableCombatant).plus(movedCombatant)
    val updatedSituation = currentSituation.copy(combatants = updatedCombatants)

    return Pair(movedCombatant, updatedSituation)
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

    return GoblinVsElvesSimulator(
            openSpaces = openSpaces,
            combatants = combatants,
            listCombatantsThisRound = combatants.sortedWith(combatantInBattleOrder))

}

