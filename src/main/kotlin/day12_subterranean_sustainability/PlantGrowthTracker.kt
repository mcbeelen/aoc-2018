package day12_subterranean_sustainability

import kotlin.system.measureTimeMillis

data class PlantGrowthTracker(private val potsWithPlants: Set<Int>, private val transformation: Set<Transformation>) {

    constructor(initialState: String, transformationsInput: String) : this(parseInitialState(initialState), parseTransformations(transformationsInput))

    fun sumOfAllPotsContainingPlants(): Int {
        return potsWithPlants.sum()
    }


    fun nextGeneration(numberOfGenerations: Int): PlantGrowthTracker {
        if (numberOfGenerations == 0) {
            return this
        }
        return generateNextGeneration().nextGeneration(numberOfGenerations - 1)
    }

    fun generateNextGeneration(): PlantGrowthTracker {
        println(this.toString())
        val nextSetOfPotsWithPlants = (potsWithPlants.min()!! - 2..potsWithPlants.max()!! + 2)
                .filter { potIndex ->
                    transformation.any { transformation -> transformation.isApplicable(potIndex, potsWithPlants) }
                }
                .toSet()
        return this.copy(potsWithPlants = nextSetOfPotsWithPlants)
    }


    override fun toString(): String {
        return (0 .. 30)
                .map{ if (potsWithPlants.contains(it)) { '#' } else { '.' }}
                .joinToString("")
    }
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
            val tracker = PlantGrowthTracker(ACTUAL_INITIAL_STATE, ACTUAL_TRANSFORMATIONS)

            val timeForPartOne = measureTimeMillis {

                val twentiethGeneration = tracker.nextGeneration(20)
                println("Sum: ${twentiethGeneration.sumOfAllPotsContainingPlants()}")
            }

            println("Solved part one in ${timeForPartOne}ms.")
        }
    }
}



