package y2020.d06

import util.distinctChars
import util.input.blocks
import util.withoutWhitespace

fun sumOfCountAnyAnswers(input: String) = input.blocks()
    .map { numberOfQuestionsAnybodyAnsweredWithYes(it) }
    .sum()

fun numberOfQuestionsAnybodyAnsweredWithYes(block: String) =
    block
        .withoutWhitespace()
        .distinctChars()
        .size



fun sumOfCountAllAnswers(input: String) = input.blocks()
    .map { numberOfQuestionsEverybodyAnsweredWithYes(it) }
    .sum()

fun numberOfQuestionsEverybodyAnsweredWithYes(block: String) =
    block.lines().first().count {
            question -> block.lines().all { it.contains(question) }
    }
