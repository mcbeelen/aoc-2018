package day15_beverage_bandits

import day15_beverage_bandits.CreatureType.ELF
import day15_beverage_bandits.CreatureType.GOBLIN
import util.grid.CoordinatesInReadingOrder
import util.grid.Direction.*
import util.grid.ScreenCoordinate
import util.grid.search.Path

val coordinatesInReadingOrder = CoordinatesInReadingOrder()
val combatantInBattleOrder = CombatantInBattleOrder()

val DIRECTION_IN_READING_ORDER = listOf(UP, LEFT, RIGHT, DOWN)


data class GoblinVsElvesSimulator(
        val numberOfCompletedRoundsOfBattle: Int = 0,
        val openSpaces: Set<ScreenCoordinate>,
        val listCombatantsThisRound: List<Combatant>,
        internal val activeCombatantIndex: Int = 0) {


    val activeCombatant: Combatant by lazy {
        listCombatantsThisRound[activeCombatantIndex]
    }

    fun sumOfHitPointsOfRemainingUnits() = listCombatantsThisRound.map { it.hitPoints }.sum()


    fun findPositionOfAllElves() = findPositionOfAll(ELF)
    fun findPositionOfAllGoblins() = findPositionOfAll(GOBLIN)

    private fun findPositionOfAll(wantedType: CreatureType) =
            listCombatantsThisRound.filter { it.type == wantedType }.map { it.position }.asIterable()


    private fun findEnemiesOf(combatant: Combatant): Iterable<Combatant> {
        return when (combatant.type) {
            ELF -> listCombatantsThisRound.filter { it.type == GOBLIN }
            GOBLIN -> listCombatantsThisRound.filter { it.type == ELF }
        }
    }

    fun findSquaresInRangeOfEnemiesOfActiveCompatant(): List<ScreenCoordinate> {

        val enemies = findEnemiesOf(activeCombatant)

        val squaresInRangeOfAnEnemy = mutableListOf<ScreenCoordinate>()
        for (enemy in enemies) {
            for (direction in DIRECTION_IN_READING_ORDER) {

                val potentialTargetSquare = enemy.position.next(direction)
                if (openSpaces.contains(potentialTargetSquare) && listCombatantsThisRound.none { it.isAt(potentialTargetSquare) }) {
                    squaresInRangeOfAnEnemy.add(potentialTargetSquare)
                }
            }
        }
        return squaresInRangeOfAnEnemy.sortedWith(coordinatesInReadingOrder)


    }


    private fun shortestPathTo(destination: ScreenCoordinate): Path<BattleCoordinate>? {
        val battlefield = Battlefield(openSpaces, listCombatantsThisRound)
        val enemyFinder = EnemyFinder(battlefield)
        return enemyFinder.findShortestPath(activeCombatant.position, destination)
    }


    fun someCombatantMayTakeItsTurnThisRound(): Boolean = this.activeCombatantIndex < listCombatantsThisRound.size

    fun theBattleIsntOver(): Boolean = listCombatantsThisRound.map { it.type }.toSet().size > 1

    fun withNextCombatantActive(): GoblinVsElvesSimulator = copy(activeCombatantIndex = activeCombatantIndex + 1)
    fun isActiveCombatantStillAlive() =
            listCombatantsThisRound.any { it.type == activeCombatant.type && it.position == activeCombatant.position }


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

    fun withActiveCombatantMovedTo(nextPosition: ScreenCoordinate): GoblinVsElvesSimulator {

        println("${activeCombatant.type} at ${activeCombatant.position} moves to ${nextPosition}")
        val movedCombatant = activeCombatant.copy(position = nextPosition)

        val updatedListCombatantsThisRound = listCombatantsThisRound.toMutableList()
        updatedListCombatantsThisRound[activeCombatantIndex] = movedCombatant
        return copy(
                listCombatantsThisRound = updatedListCombatantsThisRound)


    }

    fun isActiveCombatantAlreadyInRangeToAttack() =
            findEnemiesOf(activeCombatant)
                    .any { it.isAdjacentTo(activeCombatant) }

    fun findAdjacentEnemyWithFewestHitPoints(): Combatant {
        val adjacentEnemies = listCombatantsThisRound
                .filter { it.type != activeCombatant.type }
                .filter { it.isAdjacentTo(activeCombatant) }

        val fewestNumberOfHitPoints = adjacentEnemies.map { it.hitPoints }.min()!!

        return adjacentEnemies
                .filter { it.hitPoints == fewestNumberOfHitPoints }
                .sortedWith(combatantInBattleOrder)
                .first()

    }

    fun withoutDiedCombatant(diedCombatant: Combatant): GoblinVsElvesSimulator {

        val indexOfDiedCombatant = listCombatantsThisRound.indexOf(diedCombatant)
        val updatedListOfCombatantsThisRound = listCombatantsThisRound.toMutableList()
        updatedListOfCombatantsThisRound.remove(diedCombatant)

        val newActiveCombatantIndex = if (indexOfDiedCombatant < activeCombatantIndex) activeCombatantIndex - 1 else activeCombatantIndex

        return copy(
                    listCombatantsThisRound = updatedListOfCombatantsThisRound,
                    activeCombatantIndex = newActiveCombatantIndex)

    }

    fun withCombatantTakenHit(enemy: Combatant): GoblinVsElvesSimulator {

        // Register hit
        val updatedEnemy = enemy.copy(hitPoints = enemy.hitPoints - ATTACK_POWER)
        println("              --> Hit power reduced to ${updatedEnemy.hitPoints}")
        val updatedListOfCombatantsThisRound = listCombatantsThisRound.toMutableList()
        val indexOf = listCombatantsThisRound.indexOf(enemy)
        updatedListOfCombatantsThisRound[indexOf] = updatedEnemy

        return copy(listCombatantsThisRound = updatedListOfCombatantsThisRound)

    }

    fun whilePreparedToStartTheNextRound(): GoblinVsElvesSimulator {
        val numberOfCompletedRoundsOfBattle = numberOfCompletedRoundsOfBattle + 1
        return copy(
                numberOfCompletedRoundsOfBattle = numberOfCompletedRoundsOfBattle,
                listCombatantsThisRound = listCombatantsThisRound.sortedWith(combatantInBattleOrder),
                activeCombatantIndex = 0)
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
            updatedSituation = updatedSituation.whilePreparedToStartTheNextRound()

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


    return if (originalSituation.isActiveCombatantAlreadyInRangeToAttack()) {
        performAttack(originalSituation)
    } else {
        val situationWithMovedPlayer = tryToMove(originalSituation)
        if (situationWithMovedPlayer.isActiveCombatantAlreadyInRangeToAttack()) {
            performAttack(situationWithMovedPlayer)
        } else {
            situationWithMovedPlayer
        }
    }

}


private fun tryToMove(originalSituation: GoblinVsElvesSimulator): GoblinVsElvesSimulator {
    var updatedSituation = originalSituation
    val closestTarget = originalSituation.findSquareToMoveToAlongShortestPathToClosestTarget()
    if (closestTarget != null) {
        updatedSituation = originalSituation.withActiveCombatantMovedTo(closestTarget)
    }
    return updatedSituation
}


fun performAttack(currentSituation: GoblinVsElvesSimulator): GoblinVsElvesSimulator {

    val enemy = currentSituation.findAdjacentEnemyWithFewestHitPoints()
    println("${currentSituation.activeCombatant.type} at ${currentSituation.activeCombatant.position} is going to hit ${enemy.type} at ${enemy.position} with ${enemy.hitPoints}")
    return if (enemy.hitPoints > ATTACK_POWER) {
        currentSituation.withCombatantTakenHit(enemy)
    } else {
        println("   ${enemy.type} at ${enemy.position} dies in round: ${currentSituation.numberOfCompletedRoundsOfBattle}")
        currentSituation.withoutDiedCombatant(enemy)
    }
}


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
            listCombatantsThisRound = combatants.sortedWith(combatantInBattleOrder))

}

