package day15_beverage_bandits

import util.grid.ScreenCoordinate


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

private fun printHealthInfo(x: Int, y: Int, combatants: Set<Combatant>) {
    val combatant = findCombatantAt(combatants, x, y)
    print(message = " ${combatant.type.name[0]}(${combatant.hitPoints})")
}

private fun printOccupant(x: Int, y: Int, combatants: Set<Combatant>) {
    print(findCombatantAt(combatants, x, y).type.name[0])
}

private fun findCombatantAt(combatants: Set<Combatant>, x: Int, y: Int) =
        combatants.single { it.position.isAt(x, y) }

private fun isOccupied(x: Int, y: Int, combatants: Set<Combatant>) = combatants.any { it.position.isAt(x, y) }
