package y2018.day09_marble_mania

import java.util.function.BiFunction

class ElvesMarbleGame(private val numberOfPlayer: Int, private val lastMarble: Int) {

    private val scoreCard : MutableMap<Player, Score> = HashMap(numberOfPlayer)

    lateinit var currentMarble : MarbleInCircle

    init {
        initializeScoreCard(numberOfPlayer)
        buildCircle()
    }


    private fun initializeScoreCard(numberOfPlayer: Int) {
        for (p in 0L until numberOfPlayer) {
            scoreCard[Player(p)] = Score(0)
        }
    }


    private fun buildCircle() {

        currentMarble = MarbleInCircle(Marble(0))

        for (marble in 1L .. lastMarble) {
            playMarble(Marble(marble))
        }

    }

    private fun playMarble(marble: Marble) {
        if (marble.value.rem(23) == 0L) {
            scoreMarbleToCurrentPlayer(marble)
        } else {
            placeMarbleInCircle(marble)
        }

    }

    private fun scoreMarbleToCurrentPlayer(marble: Marble) {
        val currentPlayer = Player(marble.value.rem(numberOfPlayer))

        addToScore(currentPlayer, marble)

        val marbleToRemove = findMarbleToRemove()
        removeMarbleFromCircle(marbleToRemove)

        addToScore(currentPlayer, marbleToRemove.self)

        currentMarble = marbleToRemove.next


    }

    private fun addToScore(currentPlayer: Player, scoredMarble: Marble) {
        scoreCard.merge(currentPlayer, Score(scoredMarble.value),  BiFunction { oldTotal: Score, additionalScore: Score ->
            Score(oldTotal.score + additionalScore.score)
        })
    }

    private fun removeMarbleFromCircle(marbleToRemove: MarbleInCircle) {
        marbleToRemove.previous.next = marbleToRemove.next
        marbleToRemove.next.previous = marbleToRemove.previous
    }

    private fun findMarbleToRemove(): MarbleInCircle {
        var marbleToRemove = currentMarble
        for (i in 0 until 7)
            run {
                marbleToRemove = marbleToRemove.previous

            }
        return marbleToRemove
    }


    private fun placeMarbleInCircle(marble: Marble) {
        val placeAfter = currentMarble.next
        val placeBefore = placeAfter.next

        val newMarbleInCicle = MarbleInCircle(marble, placeBefore, placeAfter)
        placeAfter.next = newMarbleInCicle
        placeBefore.previous = newMarbleInCicle

        currentMarble = newMarbleInCicle
    }


    fun scoreOfWinningElf(): Score {


        return scoreCard.entries.sortedByDescending { it.value }.first().value


    }

}



class MarbleInCircle(val self: Marble) {

    var next: MarbleInCircle = this
    var previous: MarbleInCircle = this

    constructor(self: Marble, next: MarbleInCircle, previous: MarbleInCircle) : this(self) {
        this.next = next
        this.previous = previous
    }

}


data class Marble(val value: Long)


inline class Player(val number: Long)

inline class Score(val score: Long) : Comparable<Score> {
    override fun compareTo(other: Score): Int {
        return score.compareTo(other.score)
    }

}