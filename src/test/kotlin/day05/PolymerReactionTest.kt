package day05

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test


const val EXAMPLE_INPUT = "dabAcCaCBAcCcaDA"

class PolymerReactionTest {


    @Test
    fun itShouldSolveTheExample() {
        assertThat(Reactor().react(EXAMPLE_INPUT), equalTo("dabCBAcaDA"))
        assertThat(Reactor().react("lDdhGghHHLAXxaKkFfDdsSKkeyYEwWDdlBbLIisGcCvOVQqvo"), equalTo("sGv"))
    }




}