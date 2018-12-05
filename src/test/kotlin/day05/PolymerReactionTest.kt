package day05

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test


const val EXAMPLE_INPUT = "dabAcCaCBAcCcaDA"

class PolymerReactionTest {


    @Test
    fun itShouldSolveTheExample() {

        val reactor = Reactor()
        val remainingPolymer = reactor.react(EXAMPLE_INPUT)
        assertThat(remainingPolymer, equalTo("dabCBAcaDA"))
    }
}