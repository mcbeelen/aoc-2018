package y2020.d13

typealias BusId = Int
typealias Waittime = Int

fun solveIt(schedule: String): Int {
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


const val y2020d13input = """1000508
29,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,37,x,x,x,x,x,467,x,x,x,x,x,x,x,23,x,x,x,x,13,x,x,x,17,x,19,x,x,x,x,x,x,x,x,x,x,x,443,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,41"""
