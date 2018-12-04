package day04_sleeping_guards

typealias Nap = IntRange

class Shift(val guard: Guard) {

    val naps : MutableList<Nap> = ArrayList()

    fun addNap(nap: Nap) = naps.add(nap)

}