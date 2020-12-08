package y2020.d07

import util.collections.Queue


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



fun countBagColorWhichMayContainShinyGoldBag(input: List<String>): Int {
    val bagColorsWhichCanContainRequiredTypeOfLuggage: MutableSet<ColorCodedBag> = HashSet()
    val luggageRules = input.map { parseAviationLuggageRule(it) }

    val accommodatingColorCodedBags = Queue<ColorCodedBag>()
    accommodatingColorCodedBags.enqueue(REQUIRED_TYPE_OF_LUGGAGE)

    while (!accommodatingColorCodedBags.isEmpty()) {
        val suitableContainers = findContainersWhichMayContainBag(luggageRules, accommodatingColorCodedBags.dequeue())
        suitableContainers
            .filter { !bagColorsWhichCanContainRequiredTypeOfLuggage.contains(it) }
            .filter { !accommodatingColorCodedBags.contains(it) }
            .forEach {
                bagColorsWhichCanContainRequiredTypeOfLuggage.add(it)
                accommodatingColorCodedBags.enqueue(it)
            }
    }

    return bagColorsWhichCanContainRequiredTypeOfLuggage.size
}

private fun findContainersWhichMayContainBag(luggageRules: List<AviationLuggageRule>, colorCodedBag: ColorCodedBag): List<ColorCodedBag> {
    return luggageRules
        .filter { it.allowedContents.any { it.colorCodedBag == colorCodedBag } }
        .map { it.colorCodedBag }
}

fun countRequiredAmountOfBagsWithin(input: List<String>, colorCodedBag: String): Long {
    val luggageRules = input.map { parseAviationLuggageRule(it) }
    return calculateRequiredAmountOfBagsWithin(colorCodedBag, luggageRules);
}

private fun calculateRequiredAmountOfBagsWithin(
    colorCodedBag: ColorCodedBag,
    luggageRules: List<AviationLuggageRule>): Long {
    val ruleForWantedColor = luggageRules.single { it.colorCodedBag == colorCodedBag }
    return if (ruleForWantedColor.allowedContents.isEmpty()) {
        0L;
    } else {
        val nestedBags = ruleForWantedColor
            .allowedContents.map {
                it.quantity * (1 + calculateRequiredAmountOfBagsWithin(it.colorCodedBag, luggageRules))
            }
        nestedBags.reduce { sum, value -> sum + value }
    }
}


