package day05

import kotlin.system.measureTimeMillis


inline class Unit(val char: Char) {
    fun isSameTypeButOppositePolarity(other: Unit) =
            isSameType(other) && !isSamePolarity(other)


    private fun isSameType(other: Unit) = this.char.equals(other.char, true)
    private fun isSamePolarity(other: Unit) = this.char.equals(other.char, false)


}


class Reactor {
    fun react(polymer: String): String {

        val units = polymer.map { Unit(it) }

        return this.applyReaction(units)


    }

    private fun applyReaction(units: List<Unit>): String {

        val remainingUnits = doReact(units)

        if (remainingUnits == units) {
            return remainingUnits.map { it.char }.joinToString("")
        }
        return applyReaction(remainingUnits)

    }

    private fun doReact(units: List<Unit>): List<Unit> {

        val remainingPolymer: MutableList<Unit> = ArrayList()
        var index = 0

        while (index < units.size) {
            if ((index == units.size - 1) ||
                    !units[index].isSameTypeButOppositePolarity(units[index + 1])) {
                remainingPolymer.add(units[index])
                index++
            } else {
                index += 2
            }

        }

        return remainingPolymer


    }

    fun reduce(polymer: String): Pair<Char, String> {

        val minBy = ('a'..'z')
                .map { it to removeUnitsOfSameType(polymer, it) }.toMap()
                .mapValues { react(it.value) }
                .minBy { it.value.length }
        return minBy?.toPair() ?: Pair('_', "")

    }

    fun removeUnitsOfSameType(polymer: String, candidate: Char) : String = polymer.filterNot { it.equals(candidate, true) }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val millis = measureTimeMillis {
                val reactor = Reactor()
                val remainingPolymer = reactor.react(DAY05_INPUT)

                println("Remaining polymer '${remainingPolymer}' has lenght: ${remainingPolymer.length}")


            }

            println("Solved it in ${millis}ms.")



        }
    }


}
