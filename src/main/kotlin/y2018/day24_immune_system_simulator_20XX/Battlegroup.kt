package y2018.day24_immune_system_simulator_20XX

import java.util.*


data class Battlegroup(val fighter: Fighter,
                       val size: Int,
                       val hitPoints: Int,
                       val attackDamage: Int,
                       val attackType: AttackType,
                       val initiative: Int,
                       val weaknesses: Set<AttackType> = emptySet(),
                       val immunities: Set<AttackType> = emptySet(),
                       val uuid: String = UUID.randomUUID().toString()) {

    val effectivePower = size * attackDamage

    fun isImmuneTo(attackType: AttackType) = immunities.contains(attackType)
    fun hasWeaknessFor(attackType: AttackType) = weaknesses.contains(attackType)

    fun reducedBy(unitsLost: Int) = copy(size = size - unitsLost)


    override fun equals(other: Any?): Boolean {
        if (other == null) {
            return false
        }
        if (other is Battlegroup) {
            return this.uuid == other.uuid && this.fighter == other.fighter
        }
        return false
    }

    override fun hashCode(): Int {
        return uuid.hashCode()
    }

    override fun toString(): String {
        return "${fighter}(uuid='$uuid': $size units with $hitPoints)"
    }


}

enum class Fighter {
    IMMUNE_SYSTEM,
    INFECTION
}


val BATTLEGROUP_REGEX = Regex("(\\d+) units each with (\\d+) hit points (\\(.*\\) )*with an attack that does (\\d+) (bludgeoning|cold|fire|radiation|slashing) damage at initiative (\\d+)")


fun parseBattlegroups(fighter: Fighter, battleGroupsDefinition: String, boost: Int = 0): List<Battlegroup> {
    return battleGroupsDefinition.trimIndent().lines().withIndex().map { parseBattlegroup(fighter, it.index, it.value, boost) }
}

fun parseBattlegroup(fighter: Fighter, index: Int, battleGroupDefinition: String, boost: Int = 0): Battlegroup {
    if (!BATTLEGROUP_REGEX.matches(battleGroupDefinition)) {
        throw IllegalArgumentException("Definition does not match the Regex: ${battleGroupDefinition}")
    }
    val matchResult = BATTLEGROUP_REGEX.matchEntire(battleGroupDefinition)

    if (matchResult == null) {
        throw IllegalArgumentException("Definition does not match the Regex: ${battleGroupDefinition}")
    } else {
        val groupValues = matchResult.groupValues
        val size = groupValues[1].toInt()
        val hitPoints = groupValues[2].toInt()
        val attackDamage = groupValues[4].toInt() + boost
        val attackType = AttackType.valueOf(groupValues[5].toUpperCase())
        val initiative = groupValues[6].toInt()

        val (weaknesses, immunities) = parseSpecialties(groupValues[3])

        return Battlegroup(fighter, size, hitPoints, attackDamage, attackType, initiative, weaknesses, immunities,
                uuid = (index + 1).toString())
    }

}

//(immune to radiation, slashing, cold; weak to fire)
fun parseSpecialties(specialtiesDefinition: String): Pair<Set<AttackType>, Set<AttackType>> {

    val specialties = specialtiesDefinition
    if (specialties.isBlank()) {
        return Pair(emptySet(), emptySet())
    }

    val weaknesses = parseSpecialties(specialtiesDefinition, "weak to")
    val immunities = parseSpecialties(specialtiesDefinition, "immune to")
    return Pair(weaknesses, immunities)

}

fun parseSpecialties(specialtiesDefinition: String, indicator: String): Set<AttackType> {
    if (specialtiesDefinition.contains(indicator)) {
        var weakTo = specialtiesDefinition.substringAfter(indicator)
        if (weakTo.contains(";")) {
            weakTo = weakTo.substringBefore(";")
        } else {
            weakTo = weakTo.substringBefore(")")
        }
        return weakTo.split(",").map { AttackType.valueOf(it.trimIndent().toUpperCase()) }.toSet()
    }
    return emptySet()
}

/*
935 units each with 10231 hit points (immune to slashing, cold) with an attack that does 103 bludgeoning damage at initiative 12
4557 units each with 7860 hit points (immune to slashing) with an attack that does 15 slashing damage at initiative 11
510 units each with 7363 hit points (weak to fire, radiation) with an attack that does 143 fire damage at initiative 5
 */

enum class AttackType {

    BLUDGEONING,
    COLD,
    FIRE,
    RADIATION,
    SLASHING


}


