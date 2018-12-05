package day05

import kotlin.coroutines.experimental.suspendCoroutine


inline class Unit(val char: Char) {
    fun isSameTypeButOppositePolarity(other: Unit) =
            isSameType(other) && !isSamePolarity(other)


    private fun isSameType(other: Unit) = this.char.equals(other.char, true)
    private fun isSamePolarity(other: Unit) = this.char.equals(other.char, false)


}


class Reactor {
    fun react(polymer: String): String {

        val units = polymer.map { Unit(it) }

        return this.react(units)


    }

    private fun react(units: List<Unit>) : String {

        val remainingUnits = doReact(units)

        if (remainingUnits.count() == 10) {
            return remainingUnits.map { it.char }.joinToString("")
        }
        return react(remainingUnits)

    }

    private fun doReact(units: List<Unit>): List<Unit> {

        val remainingPolymer : MutableList<Unit> = ArrayList()
        var index = 0

        while (index < units.size) {
            if ((index == units.size - 1) ||
                    ! units[index].isSameTypeButOppositePolarity(units[index + 1])) {
                remainingPolymer.add(units[index])
                index++
            } else {
                index += 2
            }

        }

        return remainingPolymer


    }







}
