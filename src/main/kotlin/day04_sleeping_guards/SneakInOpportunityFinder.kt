package day04_sleeping_guards

import kotlin.system.measureTimeMillis


const val EXAMPLE_INPUT_DAY04 = """
[1518-11-01 00:00] Guard #10 begins shift
[1518-11-01 00:05] falls asleep
[1518-11-01 00:25] wakes up
[1518-11-01 00:30] falls asleep
[1518-11-01 00:55] wakes up
[1518-11-01 23:58] Guard #99 begins shift
[1518-11-02 00:40] falls asleep
[1518-11-02 00:50] wakes up
[1518-11-03 00:05] Guard #10 begins shift
[1518-11-03 00:24] falls asleep
[1518-11-03 00:29] wakes up
[1518-11-04 00:02] Guard #99 begins shift
[1518-11-04 00:36] falls asleep
[1518-11-04 00:46] wakes up
[1518-11-05 00:03] Guard #99 begins shift
[1518-11-05 00:45] falls asleep
[1518-11-05 00:55] wakes up
"""

typealias Guard = Int
typealias Minute = Int

class SneakInOpportunityFinder {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {


            measureTimeMillis {
                val (guard, minute) = SneakInOpportunityFinder().findBestMoment(EXAMPLE_INPUT_DAY04)

                println("The best opportunity is ${guard} @ ${minute} == ${guard * minute}")

            }




        }
    }

    fun findBestMoment(recordInput: String): Pair<Guard, Minute> {
        val records = parseInputIntoRecords(recordInput)
        val shifts : List<Shift> = extractShifts(records)

        // Extract shifts from records

        // Map shifts to Guard
        // Sum number of minutes a sleep per shift
        // Find Guard that sleeps the most

        // Find minute that guard is likely to be asleep


        return Pair(0, 0)

    }

    fun extractShifts(records: List<Record>): List<Shift> {

        val shifts : MutableList<Shift> = ArrayList()
        var napStartMinute : Minute = Int.MIN_VALUE

        records.forEach {
            when {
                indicatesNewShift(it) -> shifts.add(Shift(extractGuard(it)))
                indicatesStartNap(it) -> napStartMinute = it.minute
                indicatesEndNap(it) -> shifts.last().addNap(Nap(napStartMinute, it.minute - 1))
            }

        }

        return shifts

    }

    private fun indicatesNewShift(it: Record) = it.action.startsWith("Guard")
    private fun indicatesStartNap(it: Record) = it.action.equals("falls asleep")
    private fun indicatesEndNap(it: Record) = it.action.equals("wakes up")

    /**
     * //Action: `Guard #99 begins shift`
     */
    private fun extractGuard(record: Record): Guard = record.action.substringAfter("#").substringBefore(" ").toInt()

    fun parseInputIntoRecords(recordInput: String) = recordInput.trimIndent().lines().sorted()
            .map { fromInput(it) }

}