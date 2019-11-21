package y2018.day05_alchemical_reduction

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

const val EXAMPLE_INPUT = "dabAcCaCBAcCcaDA"

class ReactorTest {


    @Test
    fun itShouldConsiderTypeAndPolarity() {

        assertTrue("a vs A", Unit('a').isSameTypeButOppositePolarity(Unit('A')))
        assertFalse("A vs A", Unit('A').isSameTypeButOppositePolarity(Unit('A')))
        assertFalse("a vs B", Unit('a').isSameTypeButOppositePolarity(Unit('B')))
    }



    @Test
    fun itShouldSolveTheExample() {
        assertThat(react(EXAMPLE_INPUT), equalTo("dabCBAcaDA"))
        assertThat(react("lDdhGghHHLAXxaKkFfDdsSKkeyYEwWDdlBbLIisGcCvOVQqvo"), equalTo("sGv"))
    }

    @Test
    fun itShouldProperlyRemoveChars() {
        assertThat(removeUnitsOfSameType(EXAMPLE_INPUT, 'a'), equalTo("dbcCCBcCcD"))
    }

    @Test
    fun itShouldReduceToFourWithoutOneType() {
        val actual = reduce(EXAMPLE_INPUT)
        println(actual.first)
        assertThat(actual.second, equalTo("daDA"))
    }
}