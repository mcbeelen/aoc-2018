package day04_sleeping_guards

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import org.junit.Test

class SneakInOpportunityFinderTest {

    @Test
    fun parseRecordsToShifts() {
        val opportunityFinder = SneakInOpportunityFinder()
        val records = opportunityFinder.parseInputIntoRecords(EXAMPLE_INPUT_DAY04)
        val shifts = opportunityFinder.extractShifts(records)

        assert.that(shifts, hasSize(equalTo(5)))

        assert.that(shifts[0].guard, equalTo(10))
        assert.that(shifts[1].guard, equalTo(99))
        assert.that(shifts[2].guard, equalTo(10))
        assert.that(shifts[3].guard, equalTo(99))
        assert.that(shifts[4].guard, equalTo(99))


    }

    @Test
    fun itShouldFindGuard10atMinute24AsTheBestOpportunity() {

        val (guard, minute) = SneakInOpportunityFinder().findBestMoment(EXAMPLE_INPUT_DAY04)

        assert.that(guard, equalTo(10))
        assert.that(minute, equalTo(24))

    }
}


