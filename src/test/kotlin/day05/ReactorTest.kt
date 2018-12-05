package day05

import org.junit.Assert.*
import org.junit.Test

class ReactorTest {


    @Test
    fun itShouldConsiderTypeAndPolarity() {

        assertTrue("a vs A", Unit('a').isSameTypeButOppositePolarity(Unit('A')))
        assertFalse("A vs A", Unit('A').isSameTypeButOppositePolarity(Unit('A')))
        assertFalse("a vs B", Unit('a').isSameTypeButOppositePolarity(Unit('B')))
    }
}