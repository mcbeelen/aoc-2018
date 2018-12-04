package day04_sleeping_guards

import java.lang.IllegalStateException
import kotlin.Int.Companion.MIN_VALUE
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


            var measureTimeMillis = measureTimeMillis {
                val (guard, minute) = SneakInOpportunityFinder().findBestMoment(DAY04_INPUT)

                println("The best opportunity is ${guard} @ ${minute} == ${guard * minute}")

            }

            println("Solved the problem in ${measureTimeMillis}ms.")



            measureTimeMillis = measureTimeMillis {
                val (guard, minute) = SneakInOpportunityFinder().findGuardByMostFrequentlyAsleepMinute(DAY04_INPUT)

                println("The best opportunity by strategy two is ${guard} @ ${minute} == ${guard * minute}")

            }

            println("Solved the problem in ${measureTimeMillis}ms.")


        }
    }

    fun findGuardByMostFrequentlyAsleepMinute(recordInput: String): Pair<Guard, Minute> {
        val shiftsPerGuard = parseAndMapShiftsToGuards(recordInput)

        val minuteWithFrequencePerGuard = shiftsPerGuard
                .mapValues { findMinuteWithMostNaps(it.value) }
                .maxBy { it.component2().second }





        val guard = minuteWithFrequencePerGuard?.component1() ?: MIN_VALUE
        val minute = minuteWithFrequencePerGuard?.component2()?.first ?: MIN_VALUE
        return Pair(guard, minute)


    }

    private fun parseAndMapShiftsToGuards(recordInput: String) : Map<Guard, List<Shift>> {
        val records = parseInputIntoRecords(recordInput)
        val shifts: List<Shift> = extractShifts(records)

        // Map shifts to Guard
        // Sum number of minutes a sleep per shift
        // Find Guard that sleeps the most
        return shifts.groupBy { it.guard }
    }


    fun findBestMoment(recordInput: String): Pair<Guard, Minute> {

        val shiftsPerGuard = parseAndMapShiftsToGuards(recordInput)

        // Map shifts to Guard
        // Sum number of minutes a sleep per shift
        // Find Guard that sleeps the most
        val guardWhichSleepsTheMost = shiftsPerGuard
                .mapValues { sumMinutesAsleep(it.value) }
                .maxBy { it.value }?.key ?: MIN_VALUE


        // Find minute that guard is likely to be asleep
        val shiftsOfSleepyGuard = shiftsPerGuard[guardWhichSleepsTheMost] ?: throw IllegalArgumentException()
        val minute = findMinuteWithMostNaps(shiftsOfSleepyGuard)

        return Pair(guardWhichSleepsTheMost, minute.first)

    }

    private fun findMinuteWithMostNaps(shiftsOfSleepyGuard: List<Shift>, guard: Guard = MIN_VALUE): Pair<Minute, Int> {
        val maxBy = (0..59)
                .groupBy { it }
                .mapValues { countShiftsWhileAsleepAtMinute(shiftsOfSleepyGuard, it.key) }
                .maxBy { it.value }

        if (maxBy != null) {
            return Pair(maxBy.key, countShiftsWhileAsleepAtMinute(shiftsOfSleepyGuard, maxBy.key))
        } else {
            throw IllegalStateException("No minute with max number of naps")
        }
    }

    private fun countShiftsWhileAsleepAtMinute(shifs: List<Shift>, minute: Minute) =
        shifs.filter { it.wasSleepAt(minute) }.count()


    private fun sumMinutesAsleep(shifts: List<Shift>) = shifts.map { it.numberOfMinutesAsleep() }.sum()

    fun extractShifts(records: List<Record>): List<Shift> {

        val shifts : MutableList<Shift> = ArrayList()
        var napStartMinute : Minute = MIN_VALUE

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