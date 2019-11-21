package y2018.day14_hot_chocolate_recipes

import java.util.concurrent.atomic.AtomicInteger

class RecipeScoreBoard(val scoresOnTheBoard: MutableList<Int> = ArrayList(200_000_000), private var indexOfFirstElf: Int = 0, private var indexOfSecondElf: Int = 1) {

    val counter = AtomicInteger(0)

    init {
        scoresOnTheBoard.addAll(listOf(3, 7))
    }


    fun makeNewRecipes() {
        calculateNewScores()
        moveElves()
        counter.incrementAndGet()
    }

    private fun moveElves() {
        indexOfFirstElf = (indexOfFirstElf + 1 + scoreOfFirstElf()).rem(scoresOnTheBoard.size)
        indexOfSecondElf = (indexOfSecondElf + 1 + scoreOfSecondElf()).rem(scoresOnTheBoard.size)
    }

    private fun calculateNewScores() {
        val newRecipeScore = scoreOfFirstElf() + scoreOfSecondElf()
        when {
            newRecipeScore < 10 -> scoresOnTheBoard.add(newRecipeScore)
            else -> {
                scoresOnTheBoard.add(1)
                scoresOnTheBoard.add(newRecipeScore - 10)
            }
        }
    }

    fun scoreOfFirstElf() = scoresOnTheBoard[indexOfFirstElf]
    fun scoreOfSecondElf() = scoresOnTheBoard[indexOfSecondElf]

    fun getTenScoresAfter(numberOfRecipes: Int): String {

        if (numberOfRecipes + 10 < scoresOnTheBoard.size) {

            return scoresOnTheBoard.subList(numberOfRecipes, numberOfRecipes + 10).joinToString("")

        } else {
            val numberOfScoreAtTheEnd = scoresOnTheBoard.size - numberOfRecipes
            return (scoresOnTheBoard.takeLast(numberOfScoreAtTheEnd) + scoresOnTheBoard.take(10 - numberOfScoreAtTheEnd)).joinToString("")

        }
    }


    fun generateRecipesUntilScoreSequence(expectedScore: List<Int>) {
        while (scoresOnTheBoard.takeLast(expectedScore.size) != expectedScore
                && scoresOnTheBoard.takeLast(expectedScore.size + 1).take(6) != expectedScore) {
            makeNewRecipes()
        }
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            val scoreboard = RecipeScoreBoard()

            // val expectedScore = listOf(0, 1, 2, 4, 5) :  5
            // val expectedScore = listOf(5, 9, 4, 1, 4 ) : 2018
            val expectedScore = listOf(5, 5, 6, 0, 6, 1)
            val size = expectedScore.size
            scoreboard.generateRecipesUntilScoreSequence(expectedScore)
//            while (scoreboard.scoresOnTheBoard.takeLast(6) != expectedScore) {
//                scoreboard.makeNewRecipes()
//            }

            val expectedString = expectedScore.joinToString("")
            println("Found expectedScore after ${scoreboard.counter.get()} iterations")
            println("Scoreboard contains ${scoreboard.scoresOnTheBoard.size} in total and thus ${scoreboard.scoresOnTheBoard.size - size} before ${expectedString}")
            // 141307385
            // 184218733

        }

    }
}
