package y2018.day24_immune_system_simulator_20XX

import y2018.day24_immune_system_simulator_20XX.Fighter.IMMUNE_SYSTEM
import y2018.day24_immune_system_simulator_20XX.Fighter.INFECTION

val effectivePowerComparator = compareByDescending<Battlegroup> { it.effectivePower }
val targetSelectionOrder = effectivePowerComparator.thenByDescending { it.initiative }

data class ImmuneBattleSituation(val immuneSystems: List<Battlegroup>, val infections: List<Battlegroup>) {

    val biggestArmy: Pair<Fighter, Int> by lazy {
        val numberOfImmuneSystems = immuneSystems.map { it.size }.sum()
        val numberOfInfections = infections.map { it.size }.sum()
        if (numberOfImmuneSystems > numberOfInfections) {
            Pair(IMMUNE_SYSTEM, numberOfImmuneSystems)
        } else {
            Pair(INFECTION, numberOfInfections)
        }
    }

    fun areThereUnitsInBothGroups() = immuneSystems.isNotEmpty() && infections.isNotEmpty()


    fun performTargetSelection(): Map<Battlegroup, Battlegroup> {

        val targetsForImmuneSystems = selectTargets(immuneSystems, infections.toMutableList())
        val targetsForInfections = selectTargets(infections, immuneSystems.toMutableList())

        return targetsForImmuneSystems + targetsForInfections

    }

    private fun selectTargets(attackers: List<Battlegroup>, enemies: MutableList<Battlegroup>): MutableMap<Battlegroup, Battlegroup> {
        val targetSelection: MutableMap<Battlegroup, Battlegroup> = HashMap()

        attackers.sortedWith(targetSelectionOrder)
                .forEach {
                    val candidateTargets = findCandidateTargets(it, enemies)
                    if (candidateTargets.isNotEmpty()) {
                        val targetedEnemy = candidateTargets.sortedWith(targetSelectionOrder).first()
                        targetSelection[it] = targetedEnemy
                        enemies.remove(targetedEnemy)
                    }
                }

        return targetSelection
    }

    fun findCandidateTargets(battlegroup: Battlegroup, enemies: List<Battlegroup>): List<Battlegroup> {
        val vulnerableTargets = enemies.filter { !it.isImmuneTo(battlegroup.attackType) }
        if (vulnerableTargets.isEmpty()) {
            return emptyList()
        }
        val preferredTargets = vulnerableTargets.filter { it.hasWeaknessFor(battlegroup.attackType) }
        if (preferredTargets.isNotEmpty()) {
            return preferredTargets
        } else {
            return vulnerableTargets
        }

    }

    fun isGroupStillAlive(group: Battlegroup) = immuneSystems.contains(group) || infections.contains(group)

    fun withDamageDone(battlegroup: Battlegroup,
                       attackType: AttackType,
                       effectivePower: Int): ImmuneBattleSituation {

        if (battlegroup.isImmuneTo(attackType)) {
            return this
        }

        val damageDone = if (battlegroup.hasWeaknessFor(attackType)) effectivePower * 2 else effectivePower

        val unitsLost = damageDone / battlegroup.hitPoints
        if (unitsLost >= battlegroup.size) {
            return this.without(battlegroup)
        } else {
            return this.withBattleGroupReducedBy(battlegroup, unitsLost)
        }
    }

    private fun withBattleGroupReducedBy(battlegroup: Battlegroup, unitsLost: Int): ImmuneBattleSituation {
        if (infections.contains(battlegroup)) {
            return this.withReducedInfection(battlegroup, unitsLost)
        } else {
            return this.withReducedImmuneSystem(battlegroup, unitsLost)
        }

    }

    private fun withReducedImmuneSystem(battlegroup: Battlegroup, unitsLost: Int): ImmuneBattleSituation {
        return copy(immuneSystems = immuneSystems.minus(battlegroup).plus(battlegroup.reducedBy(unitsLost)))
    }

    private fun withReducedInfection(battlegroup: Battlegroup, unitsLost: Int): ImmuneBattleSituation {
        val updatedInfection = battlegroup.reducedBy(unitsLost)
        val updatedInfections = infections.minus(battlegroup).plus(updatedInfection)
        return copy(infections = updatedInfections)
    }

    private fun without(battlegroup: Battlegroup): ImmuneBattleSituation {
        return when(battlegroup.fighter) {
            IMMUNE_SYSTEM -> this.withoutImmuneSystem(battlegroup)
            INFECTION -> this.withoutInfection(battlegroup)
        }
    }

    private fun withoutImmuneSystem(battlegroup: Battlegroup): ImmuneBattleSituation {
        println("${battlegroup} was killed")

        val updatedImmuneSystems = immuneSystems.filter { it != battlegroup }
        return copy(immuneSystems = updatedImmuneSystems)
    }

    private fun withoutInfection(battlegroup: Battlegroup): ImmuneBattleSituation {
        println("${battlegroup} was killed")
        val updatedInfections = infections.filter { it != battlegroup }
        return copy(infections = updatedInfections)
    }

    fun getEffectivePower(battleGroup: Battlegroup): Int {
        return when(battleGroup.fighter) {

            IMMUNE_SYSTEM -> immuneSystems.filter { it == battleGroup }.single().effectivePower
            INFECTION -> infections.filter { it == battleGroup }.single().effectivePower
        }


    }
}

class ImmuneSystemSimulator() {



    fun simulateBattle(initialSituation: ImmuneBattleSituation): ImmuneBattleSituation {

        var currentSituation = initialSituation

        while (currentSituation.areThereUnitsInBothGroups()) {
            currentSituation = simulateFight(currentSituation)
        }

        return currentSituation
    }

    private fun simulateFight(currentSituation: ImmuneBattleSituation): ImmuneBattleSituation {

        val attackersWithTheirTargets = currentSituation.performTargetSelection()

        return executeAttackPhase(attackersWithTheirTargets, currentSituation)


    }

    private fun executeAttackPhase(groupsWithTheirTargets: Map<Battlegroup, Battlegroup>, initialSituation: ImmuneBattleSituation): ImmuneBattleSituation {

        var currentSituation = initialSituation

        val groupsInAttackingOrder = groupsWithTheirTargets.keys.sortedByDescending { it.initiative }

        groupsInAttackingOrder.forEach {
            if (currentSituation.isGroupStillAlive(it)) {
                val defender = groupsWithTheirTargets[it]!!
                val effectivePower = currentSituation.getEffectivePower(it)

                currentSituation = currentSituation.withDamageDone(defender, it.attackType, effectivePower)
            }

        }

        return currentSituation

    }




    companion object {


        @JvmStatic
        fun main(args: Array<String>) {

            val immuneSystems = parseBattlegroups(IMMUNE_SYSTEM, IMMUNE_SYSTEM_DEFINITION, 39)
            val infections = parseBattlegroups(INFECTION, INFECTION_DEFINITION)

            val initialSituation = ImmuneBattleSituation(immuneSystems, infections)

            val simulator = ImmuneSystemSimulator()

            val finalSituation: ImmuneBattleSituation = simulator.simulateBattle(initialSituation)


            println("${finalSituation.biggestArmy}")
            // With boost: 0 --> 10723 is the answer to part one.
            // With boost: 39 --> 5120 is the answer to part two.



        }
    }


}