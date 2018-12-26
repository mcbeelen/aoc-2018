package day24_immune_system_simulator_20XX


data class Battlegroup(val size: Int,
                       val hitPoints : Int,
                       val attackDamage: Int,
                       val attackType: AttackType,
                       val weaknesses : Set<AttackType> = emptySet(),
                       val immunities: Set<AttackType> = emptySet(),
                       val initiative: Int) {

    val effectivePower = size * attackDamage

}

enum class AttackType {

    BLUDGEONING,
    COLD,
    FIRE,
    RADIATION,
    SLASHING


}
