package y2018.day07_assemble_sleigh

import com.google.common.collect.HashBasedTable
import com.google.common.collect.Table
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.system.measureTimeMillis


fun minimumTimeNeededToAssemble(assemblyInstructions: String, numberOfWorkers: Int): Int {

    val assembleInstructions = assemblyInstructions.lines().map { parseToAssembleOrder(it) }
    val allSteps: MutableSet<Step> = findAllSteps(assembleInstructions)

    val stepToTaskMap = extractStepToTaskMap(assembleInstructions)

    return buildEntireSchedule(numberOfWorkers, allSteps, stepToTaskMap)

}

private fun buildEntireSchedule(numberOfWorkers: Int, allSteps: MutableSet<Step>, stepToTaskMap: MutableMap<Step, Task>): Int {
    val schedule = Schedule(numberOfWorkers)
    var second = Second(0)

    val done: MutableList<Step> = ArrayList()

    val currentAssignmentMap = buildAssignmentMap(numberOfWorkers)

    while (allSteps.isNotEmpty()) {
        val availableWorkers = getAvailableWorkers(currentAssignmentMap)
        val stepReadyToBeWorkedOn = getAvailableTasks(stepToTaskMap)
                .filter { step ->
                    currentAssignmentMap.values
                            .none { it is Work && it.step == step }

                }


        availableWorkers.zip(stepReadyToBeWorkedOn)
                .forEach {
                    val worker = it.first
                    val nextStep = it.second

                    if (!currentAssignmentMap.containsValue(Work(nextStep))) {
                        // Assign Step to Worker
                        println("Worker ${worker.name} started to work on ${nextStep} at timestamp : ${second.timestamp}")
                        currentAssignmentMap[worker] = Work(nextStep)
                        schedule.registerAssignment(worker, nextStep, second)
                    }
                }

        currentAssignmentMap.filter { it.value is Idle }
                .forEach {
                    schedule.registerAssignment(it.key, second, it.value)
                }

        printStatus(second, schedule, done)

        second = second.next()


        currentAssignmentMap.keys.forEach { worker ->
            if (schedule.finishedWork(worker, second)) {
                val work = currentAssignmentMap.getValue(worker)

                if (work is Work) {
                    val finishedStep = work.step

                    // println("Worker ${worker.name} finished step '${work.describe()}' at ${second.timestamp}")
                    done.add(finishedStep)

                    currentAssignmentMap[worker] = Idle()
                    unblockAll(stepToTaskMap, finishedStep)
                    allSteps.remove(finishedStep)
                    stepToTaskMap.remove(finishedStep)

                }
            }
        }
    }

    printStatus(second, schedule, done)

    println()

    schedule.printIt(second)

    return second.timestamp
}

fun printStatus(second: Second, schedule: Schedule, done: MutableList<Step>) {
    val doneText = done.map { it.name }.joinToString("")
    print("${second.timestamp}   ")
    print(schedule.printReport(second))
    println("   ${doneText}")

}

fun unblockAll(stepToTaskMap: MutableMap<Step, Task>, step: Step) {
    stepToTaskMap.getValue(step).blocking
            .forEach { blockedStep ->
                stepToTaskMap[blockedStep] = stepToTaskMap.getValue(blockedStep).unblock(step)
            }

}

private fun getAvailableTasks(stepToTaskMap: MutableMap<Step, Task>): List<Step> {
    return stepToTaskMap
            .filter { it.value.blockers.isEmpty() }
            .map { it.key }
            .sortedBy { it.name }
}

private fun getAvailableWorkers(currentAssignmentMap: MutableMap<Worker, Activity>): Iterable<Worker> {
    return currentAssignmentMap.filter { it.value.isAvailable() }.map { it.key }

}


private fun buildAssignmentMap(numberOfWorkers: Int): MutableMap<Worker, Activity> {
    val currentAssignmentMap: MutableMap<Worker, Activity> = HashMap()
    for (worker in 1..numberOfWorkers) {
        currentAssignmentMap[Worker(worker)] = Idle()
    }
    return currentAssignmentMap
}


internal fun extractStepToTaskMap(assembleInstructions: List<AssemblyInstruction>): MutableMap<Step, Task> {
    val stepToTaskMap: MutableMap<Step, Task> = HashMap()

    assembleInstructions.forEach {
        if (stepToTaskMap.containsKey(it.before)) {
            stepToTaskMap[it.before] = stepToTaskMap.getValue(it.before).blocks(it.after)
        } else {
            stepToTaskMap[it.before] = Task(it.before, listOf(it.after))
        }

        if (stepToTaskMap.containsKey(it.after)) {
            stepToTaskMap[it.after] = stepToTaskMap.getValue(it.after).blockedBy(it.before)
        } else {
            stepToTaskMap[it.after] = Task(it.after, emptyList(), listOf(it.before))
        }
    }



    return stepToTaskMap
}


class Schedule(private val numberOfWorkers: Int) {
    private val schedule: Table<Worker, Second, Activity> = HashBasedTable.create()

    fun registerAssignment(worker: Worker, nextStep: Step, second: Second) {

        val work = Work(nextStep)
        for (s in second.timestamp until second.timestamp + nextStep.duration()) {
            schedule.put(worker, Second(s), work)
        }

    }

    fun registerAssignment(worker: Worker, second: Second, activity: Activity) = schedule.put(worker, second, activity)


    fun finishedWork(worker: Worker, second: Second): Boolean = !schedule.contains(worker, second)


    fun printReport(second: Second): String {
        val report = StringBuilder()

        for (w in 1 .. numberOfWorkers) {
            val worker = Worker(w)
            if (schedule.contains(worker, second)) {
                report.append("  " + schedule.get(worker,second).describe() + "  ")
            } else {
                report.append("  .  ")
            }
        }
        return report.toString()

    }

    fun printIt(second: Second) {
        print("time: ")
        for (s in 0 .. second.timestamp) {
            if (s.rem(50) == 0) print('|')
            print(s.rem(10))

        }
        println()

        for (w in 1 .. numberOfWorkers) {
            print("${w} :   ")
            val worker = Worker(w)
            for (s in 0 .. second.timestamp) {
                val timestamp = Second(s)
                if (s.rem(50) == 0) print('|')
                print(schedule.get(worker, timestamp)?.describe() ?: '.')

            }
            println()
        }
    }


}

interface Activity {
    fun describe(): Char
    fun isAvailable(): Boolean

}

class Idle : Activity {
    override fun isAvailable() = true

    override fun describe() = '.'
}

class Work(val step: Step) : Activity {
    override fun isAvailable() = false
    override fun describe() = step.name
}

inline class Worker(val name: Int)

inline class Second(val timestamp: Int) {
    fun next(): Second = Second(timestamp + 1)
}


data class Task(val step: Step, val blocking: List<Step> = emptyList(), val blockers: List<Step> = emptyList()) {
    fun blocks(blockedStep: Step) = copy(blocking = blocking + blockedStep)

    fun blockedBy(blocker: Step) = copy(blockers = blockers + blocker)
    fun unblock(done: Step): Task = copy(blockers = blockers - done)

}


class AssemblyStepExecutionScheduler {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val time = measureTimeMillis {

                val timeNeededToAssemble = minimumTimeNeededToAssemble(SLEIGH_ASSEMBLY_INSTRUCTIONS.trimIndent(), 5)

                println("Time needed to assemble is ${timeNeededToAssemble}")
            }


            println("Part TWO solved in ${time}ms.")
        }
    }

}