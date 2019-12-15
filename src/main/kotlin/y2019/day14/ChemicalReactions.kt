package y2019.day14

const val ORE = "ORE"
const val FUEL = "FUEL"

typealias Quantity = Long
typealias Chemical = String
data class Ingredient(val quantity: Quantity, val chemical: Chemical) {
    override fun toString(): String {
        return "$quantity $chemical"
    }
}
data class Reaction(val input: List<Ingredient>, val output: Ingredient) {
    fun needs(chemical: Chemical) = input.any { it.chemical == chemical }
    override fun toString(): String {
        val ingredients = input.joinToString { it.toString() }
        return "$ingredients => $output"
    }
}



fun parseReactions(reactionDescriptions: String): Map<Chemical, Reaction> {
    val reactions = reactionDescriptions.trimIndent().lines().map { parseReaction(it) }.toMutableList()
    return reactions.map { Pair(it.output.chemical, it) }.toMap()
}

fun parseReaction(description: String): Reaction {
    val split = description.trim().split("=>")
    val inputDescription = split[0]
    val outputDescription = split[1]
    val ingredients = inputDescription.split(',')

    val input = ingredients.map { parseIngredient(it) }
    val output = parseIngredient(outputDescription)
    return Reaction(input, output)

}


fun parseIngredient(description: String): Ingredient {
    val split = description.trim().split(" ")
    return Ingredient(split[0].toLong(), split[1])
}



fun determineOrderInWhichChemicalsShouldBeProduced(parseReactions: Map<String, Reaction>): List<String> {
    val reactionsPerOutputChemical: MutableMap<String, Reaction> = HashMap(parseReactions).toMutableMap()

    val orderedListOfChemicals: MutableList<String> = ArrayList()
    while (reactionsPerOutputChemical.isNotEmpty()) {
        reactionsPerOutputChemical.keys.filter { isNoInputForAnotherChemical(it, reactionsPerOutputChemical) }
                .sorted()
                .forEach {
                    orderedListOfChemicals.add(it)
                    reactionsPerOutputChemical.remove(it)
                }

    }
    return orderedListOfChemicals
}

fun isNoInputForAnotherChemical(chemical: String, reactionsPerOutputChemical: Map<String, Reaction>) =
        reactionsPerOutputChemical.values.none {
            it.needs(chemical)
        }
