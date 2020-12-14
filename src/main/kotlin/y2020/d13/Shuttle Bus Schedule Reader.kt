package y2020.d13

typealias BusId = Int
typealias Waittime = Int

fun findFirstDepartingBusAndWaittime(schedule: String): Int {
    val timestamp = schedule.lines()[0].toInt()
    val bussesInService = schedule.lines()[1]
        .split(',')
        .filter { it != "x" }
        .map { it.toInt() }


    val waittimePerBus = bussesInService
        .map { waittimePerBus(it, timestamp) }
        .sortedBy { it.second }

    val busId = waittimePerBus.first().first
    val waitTime = waittimePerBus.first().second

    return busId * waitTime
}

fun waittimePerBus(busId: BusId, timestamp: Int): Pair<BusId, Waittime> {
    val waittime = calculateWaitTimeForBus(busId, timestamp)
    return Pair(busId, waittime)
}

private fun calculateWaitTimeForBus(busId: BusId, timestamp: Int) = busId - (timestamp % busId)


fun findFirstTimestampForConsecutiveDepartures(busIDinput: String, jumpStart : Boolean = false): Long {
    val bussesWithOffset = readBusInput(busIDinput)
    val sordedBussesWithOffset = bussesWithOffset.sortedByDescending { it.first }

    val (base, offset) = sordedBussesWithOffset.reduce { acc, pair -> foldCombination(acc, pair) }
    return base - offset
}

fun foldCombination(accumulator: Pair<Long, Long>, additionalElement: Pair<Long, Long>): Pair<Long, Long> {
    var offset = accumulator.second
    while (offset % additionalElement.first != additionalElement.second % additionalElement.first) {
        offset += accumulator.first
    }

    val newBase = accumulator.first * additionalElement.first
    return Pair(newBase, offset % newBase)
}



private fun readBusInput(busIDinput: String) = busIDinput
    .split(',')
    .mapIndexed { index: Int, s: String -> Pair(s, index) }
    .filter { it.first != "x" }
    .map { Pair(it.first.toLong(), it.second.toLong())}


const val y2020d13input = """1000508
29,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,467,x,x,x,x,x,x,x,23,x,x,x,x,13,x,x,x,17,x,19,x,x,x,x,x,x,x,x,x,x,x,443,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,41"""
