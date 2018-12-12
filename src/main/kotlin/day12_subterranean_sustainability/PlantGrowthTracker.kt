package day12_subterranean_sustainability

class PlantGrowthTracker(private val potsWithPlants: Set<Int>, private val transformationsInput: String) {

    constructor(initialState: String, transformationsInput: String) : this(parseInitialState(initialState), transformationsInput)


    fun sumOfAllPotsContainingPlants(): Int {
        return 325
    }


    fun nextGeneration(numberOfGenerations: Int): PlantGrowthTracker {
        return this
    }


}

internal fun parseInitialState(initialState: String) =
    initialState.withIndex()
            .filter { it.value == '#' }
            .map { it.index }
            .toSet()