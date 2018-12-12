package day12_subterranean_sustainability

import kotlin.system.measureTimeMillis

data class PlantGrowthTracker(val potsWithPlants: Set<Int>, val transformation: Set<Transformation>) {

    constructor(initialState: String, transformationsInput: String) : this(parseInitialState(initialState), parseTransformations(transformationsInput))

    fun sumOfAllPotsContainingPlants(): Long {
        return potsWithPlants.map { it.toLong() }.sum()
    }


    fun nextGeneration(numberOfGenerations: Long): PlantGrowthTracker {
        return generateNextGenerations(this, numberOfGenerations)

    }

    override fun toString(): String {
        return (0 .. 30)
                .map{ if (potsWithPlants.contains(it)) { '#' } else { '.' }}
                .joinToString("")
    }
}


internal tailrec fun generateNextGenerations(currentGeneration: PlantGrowthTracker, numberOfGenerations: Long): PlantGrowthTracker {
    if (numberOfGenerations == 0L) {
        return currentGeneration
    }
    if (numberOfGenerations.rem(1_000_000L) == 0L) {
        println("Still todo: ${numberOfGenerations}" )
    }

    return generateNextGenerations(generateNextGeneration(currentGeneration), numberOfGenerations - 1)

}


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
            val fiftyBillion = 50_000_000_000

            val tracker = PlantGrowthTracker(ACTUAL_INITIAL_STATE, ACTUAL_TRANSFORMATIONS)

            val timeForPartOne = measureTimeMillis {

                val twentiethGeneration = tracker.nextGeneration(fiftyBillion)
                println("Sum: ${twentiethGeneration.sumOfAllPotsContainingPlants()}")
            }

            println("Solved part one in ${timeForPartOne}ms.")
        }
    }
}



