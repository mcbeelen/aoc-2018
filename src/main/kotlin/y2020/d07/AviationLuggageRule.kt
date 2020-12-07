package y2020.d07

import util.collections.Queue
import util.input.loadLines


typealias Quantity = Long
typealias ColorCodedBag = String

data class AviationLuggageRule(val colorCodedBag: ColorCodedBag, val allowedContents: List<AllowedContent>)
data class AllowedContent(val quantity: Quantity, val colorCodedBag: ColorCodedBag)

const val REQUIRED_TYPE_OF_LUGGAGE = "shiny gold"

fun parseAviationLuggageRule(ruleText: String): AviationLuggageRule {
    val colorCodedBag = ruleText.substringBefore(" bags contain ")
    val contentDescription = ruleText.substringAfter(" bags contain ")

    return AviationLuggageRule(colorCodedBag, allowedContents = parseContentDescriptions(contentDescription))
}

fun parseContentDescriptions(contentDescription: String): List<AllowedContent> {
    if (contentDescription == "no other bags.") { return emptyList() }
    return contentDescription.split(", ")
        .map { parseContentDescription(it) }
}

fun parseContentDescription(contentDescription: String) : AllowedContent {
    val quantity = contentDescription.substringBefore(" ").toLong()
    val colorCodedBag = contentDescription.substringAfter(" ").substringBefore(" bag")
    return AllowedContent(quantity, colorCodedBag)
}


fun solveIt(fileName: String): Int {

    val bagColorsWhichCanContainRequiredTypeOfLuggage : MutableSet<ColorCodedBag> = HashSet()
    val luggageRules = loadLines(fileName).map { parseAviationLuggageRule(it) }

    val accommodatingColorCodedBags = Queue<ColorCodedBag>()
    accommodatingColorCodedBags.enqueue(REQUIRED_TYPE_OF_LUGGAGE)

    while (! accommodatingColorCodedBags.isEmpty()) {
        val suitableContainers = findContainers(luggageRules, accommodatingColorCodedBags.dequeue())
        suitableContainers
            .filter { ! bagColorsWhichCanContainRequiredTypeOfLuggage.contains(it) }
            .filter { ! accommodatingColorCodedBags.contains(it) }
            .forEach {
                bagColorsWhichCanContainRequiredTypeOfLuggage.add(it)
                accommodatingColorCodedBags.enqueue(it)
            }
    }

    return bagColorsWhichCanContainRequiredTypeOfLuggage.size
}

private fun findContainers(luggageRules: List<AviationLuggageRule>, colorCodedBag: ColorCodedBag): List<ColorCodedBag> {
    return luggageRules
        .filter { it.allowedContents.any { it.colorCodedBag == colorCodedBag } }
        .map { it.colorCodedBag }

}
