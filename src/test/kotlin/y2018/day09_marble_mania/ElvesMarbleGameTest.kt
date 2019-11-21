package y2018.day09_marble_mania

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class ElvesMarbleGameTest {

    @Test
    fun itShouldSolvePartOneOfTheProblem() {
        assertThat("Part One", ElvesMarbleGame(493, 71863).scoreOfWinningElf().score, equalTo(367802L))
        // 44ms
    }

    @Test
    fun itShouldSolvePartTwoOfTheProblem() {
        assertThat("Part Two", ElvesMarbleGame(493, 7186300).scoreOfWinningElf().score, equalTo(2996043280L))
        // 10x --> 77
        // 100 --> 1_728ms
        // 1_000 --> 39_587ms
    }

    @Test
    fun firstOfTheExampleGames() {

        assertThat("FirstExample", ElvesMarbleGame(9, 23).scoreOfWinningElf().score, equalTo(32L))
        assertThat("Two rounds with", ElvesMarbleGame(23, 46).scoreOfWinningElf().score, equalTo(95L))


        assertThat("Second Example", ElvesMarbleGame(10, 1618).scoreOfWinningElf().score, equalTo(8317L))


        assertThat("Third Example", ElvesMarbleGame(13, 7999).scoreOfWinningElf().score, equalTo(146373L))
        assertThat("Fourth Example", ElvesMarbleGame(17, 1104).scoreOfWinningElf().score, equalTo(2764L))
        assertThat("Fifth Example", ElvesMarbleGame(21, 6111).scoreOfWinningElf().score, equalTo(54718L))
        assertThat("Sixth Example", ElvesMarbleGame(30, 5807).scoreOfWinningElf().score, equalTo(37305L))


    }
}