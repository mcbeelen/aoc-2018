package y2018.day14_hot_chocolate_recipes

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test


class HotChocolateRecipesTest {

    @Test
    fun theElvesShouldGenerateNewRecipes() {

        val scoreboard = RecipeScoreBoard()

        assertThat(scoreboard.scoreOfFirstElf(), equalTo(3))
        assertThat(scoreboard.scoreOfSecondElf(), equalTo(7))

        scoreboard.makeNewRecipes() // 1
        assertThat(scoreboard.scoreOfFirstElf(), equalTo(3))
        assertThat(scoreboard.scoreOfSecondElf(), equalTo(7))
        assertThat(scoreboard.scoresOnTheBoard, equalTo(listOf(3, 7, 1, 0)))


        scoreboard.makeNewRecipes() // 2
        assertThat(scoreboard.scoreOfFirstElf(), equalTo(1))
        assertThat(scoreboard.scoreOfSecondElf(), equalTo(0))
        assertThat(scoreboard.scoresOnTheBoard, equalTo(listOf(3, 7, 1, 0, 1, 0)))


        for (i in 3..5) {
            scoreboard.makeNewRecipes()
        }
        assertThat(scoreboard.scoreOfFirstElf(), equalTo(1))
        assertThat(scoreboard.scoreOfSecondElf(), equalTo(4))

        for (i in 6..9) {
            scoreboard.makeNewRecipes()
        }
        assertThat(scoreboard.scoreOfFirstElf(), equalTo(5))
        assertThat(scoreboard.scoreOfSecondElf(), equalTo(4))


        while (scoreboard.scoresOnTheBoard.size < 19) {
            scoreboard.makeNewRecipes()
        }
        assertThat(scoreboard.getTenScoresAfter(9), equalTo("5158916779"))


        while (scoreboard.scoresOnTheBoard.size < 2018 + 10) {
            scoreboard.makeNewRecipes()
        }
        assertThat(scoreboard.getTenScoresAfter(2018), equalTo("5941429882"))


        while (scoreboard.scoresOnTheBoard.size < 556061 + 10) {
            scoreboard.makeNewRecipes()
        }
        assertThat(scoreboard.getTenScoresAfter(556061), equalTo("2107929416"))


    }


    @Test
    fun itShouldFindExpectedScoreSequenceAfterCertainAmountOfRecipes() {

        val scoreboard = RecipeScoreBoard()

        scoreboard.generateRecipesUntilScoreSequence(listOf(0, 1, 2, 4, 5))
        assertThat(scoreboard.scoresOnTheBoard.size - 5, equalTo(5))

        scoreboard.generateRecipesUntilScoreSequence(listOf(5, 1, 5, 8, 9))
        assertThat(scoreboard.scoresOnTheBoard.size - 5, equalTo(9))

        scoreboard.generateRecipesUntilScoreSequence(listOf(9, 2, 5, 1, 0))
        assertThat(scoreboard.scoresOnTheBoard.size - 5, equalTo(18))

        scoreboard.generateRecipesUntilScoreSequence(listOf(5, 9, 4, 1, 4))
        assertThat(scoreboard.scoresOnTheBoard.size - 5, equalTo(2018))


        val actualExpectedScoreSequence = listOf(5, 5, 6, 0, 6, 1)
        scoreboard.generateRecipesUntilScoreSequence(actualExpectedScoreSequence)
        scoreboard.makeNewRecipes()

        if (scoreboard.scoresOnTheBoard.takeLast(actualExpectedScoreSequence.size) == actualExpectedScoreSequence) {
            assertThat(scoreboard.scoresOnTheBoard.size - (actualExpectedScoreSequence.size), equalTo(20307395))
        } else {
            assertThat(scoreboard.scoresOnTheBoard.size - 1 - actualExpectedScoreSequence.size, equalTo(20307395))

        }

        //     20307394


        // with ArrayList(200_000_000_ it takes 35.287s


    }
}