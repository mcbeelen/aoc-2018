package day12_subterranean_sustainability

import kotlin.system.measureTimeMillis

data class PlantGrowthTracker(val potsWithPlants: Set<Int>, val transformation: Set<Transformation>) {

    constructor(initialState: String, transformationsInput: String) : this(parseInitialState(initialState), parseTransformations(transformationsInput))

    fun sumOfAllPotsContainingPlants() = potsWithPlants.map { it.toLong() }.sum()

    fun nextGeneration(numberOfGenerations: Long) = generateNextGenerations(this, numberOfGenerations)

    override fun toString(): String {
        return (0 .. 300)
                .map{ if (potsWithPlants.contains(it)) { '#' } else { '.' }}
                .joinToString("")
    }
}


val previousConfigurations : MutableMap<String, Long> = HashMap()

internal tailrec fun generateNextGenerations(currentGeneration: PlantGrowthTracker, numberOfGenerationsToGenerate: Long): PlantGrowthTracker {

    println("$numberOfGenerationsToGenerate  --> ${currentGeneration.sumOfAllPotsContainingPlants()}")

    if (numberOfGenerationsToGenerate == 0L) {
        return currentGeneration
    }

    val nextGeneration = generateNextGeneration(currentGeneration)

    var nextGenerationNumber = numberOfGenerationsToGenerate - 1
    val nextConfiguration = nextGeneration.potsWithPlants

    val representation = fromStart(nextConfiguration)
    if (previousConfigurations.containsKey(representation)) {
        //println("I already saw this! ${numberOfGenerationsToGenerate}")
    } else {
        previousConfigurations[representation] = numberOfGenerationsToGenerate
        if (numberOfGenerationsToGenerate.rem(10_000L) == 0L) {
            println("Currently knows about ${previousConfigurations.size} configurations")
        }
    }

    return generateNextGenerations(nextGeneration, nextGenerationNumber)

}

fun fromStart(nextConfiguration: Set<Int>) =
        (nextConfiguration.min()!! .. nextConfiguration.max()!!)
            .map{ if (nextConfiguration.contains(it)) { '#' } else { '.' }}
            .joinToString("")


fun generateNextGeneration(currentGeneration: PlantGrowthTracker): PlantGrowthTracker {
    val nextSetOfPotsWithPlants = (currentGeneration.potsWithPlants.min()!! - 2..currentGeneration.potsWithPlants.max()!! + 2)
            .filter { potIndex ->
                currentGeneration.transformation.any { transformation -> transformation.isApplicable(potIndex, currentGeneration.potsWithPlants) }
            }
            .toSet()

    return currentGeneration.copy(potsWithPlants = nextSetOfPotsWithPlants)
}


internal fun parseInitialState(initialState: String) =
        initialState.withIndex()
                .filter { it.value == '#' }
                .map { it.index }
                .toSet()




data class Transformation(val requiredOccupiedPots: Set<Int>, val forbiddenPots: Set<Int>) {
    fun isApplicable(potIndex: Int, potsWithPlants: Set<Int>): Boolean {

        return requiredOccupiedPots.all { potsWithPlants.contains(potIndex + it) } &&
                forbiddenPots.none { potsWithPlants.contains(potIndex + it) }


    }
}


fun parseTransformations(transformationsInput: String) = transformationsInput.trimIndent().lines()
        .filter { it.last() == '#' }
        .map { parseToTransformation(it) }
        .toSet()

fun parseToTransformation(transformationInput: String) = Transformation (findRequiredOccupiedPots(transformationInput), findForbiddenPots(transformationInput))

fun findForbiddenPots(transformationInput: String): Set<Int> {
    return findOffsets(transformationInput, '.')
}

private fun findRequiredOccupiedPots(transformationInput: String): Set<Int> {
    return findOffsets(transformationInput, '#')
}

private fun findOffsets(transformationInput: String, c: Char): Set<Int> {
    return transformationInput.substring(0..5)
            .withIndex()
            .filter { it.value == c }
            .map { it.index - 2}
            .toSet()
}

class Day12Solver() {
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            val fiftyBillion = 5_000L

            val tracker = PlantGrowthTracker(ACTUAL_INITIAL_STATE, ACTUAL_TRANSFORMATIONS)

            val timeForPartOne = measureTimeMillis {

                val twentiethGeneration = tracker.nextGeneration(fiftyBillion)
                println("Sum: ${twentiethGeneration.sumOfAllPotsContainingPlants()}")
            }

            println("Solved part one in ${timeForPartOne}ms.")
        }
    }
}



/*
At a certain point in time the configuration becomes stable, but 'moves' to the right.
Each iteration adds another 22 point
After 5000 iterations to total sum == 110475
For the number after 50000000000 = 110475 +	49999995000	* 22 = 1100000000475
 */