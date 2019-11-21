package y2018.day04_sleeping_guards

typealias Nap = IntRange

class Shift(val guard: Guard) {

    val naps: MutableList<Nap> = ArrayList()

    fun addNap(nap: Nap) = naps.add(nap)

    fun numberOfMinutesAsleep() = naps
            .map { it.last - it.first }
            .sum()

    fun wasSleepAt(minute: Minute) = naps.any { it.contains(minute.value) }

}

