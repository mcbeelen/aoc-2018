package day04_sleeping_guards

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class SneakInOpportunityFinderTest {
    @Test
    fun itShouldFindGuard10atMinute24AsTheBestOpportunity() {

        val (guard, minute) = SneakInOpportunityFinder().findBestMoment(EXAMPLE_INPUT_DAY04)

        assert.that(guard, equalTo(10))
        assert.that(minute, equalTo(24))

    }
}


