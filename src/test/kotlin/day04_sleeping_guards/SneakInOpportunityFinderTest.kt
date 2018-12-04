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

        assert.that(shifts[0].guard.value, equalTo(10))
        val naps = shifts[0].naps
        assert.that(naps, hasSize(equalTo(2)))
        assert.that(naps[0].first, equalTo(5))
        assert.that(naps[0].last, equalTo(24))

        assert.that(naps[1].first, equalTo(30))
        assert.that(naps[1].last, equalTo(54))

        assert.that(shifts[1].guard.value, equalTo(99))
        assert.that(shifts[2].guard.value, equalTo(10))
        assert.that(shifts[3].guard.value, equalTo(99))
        assert.that(shifts[4].guard.value, equalTo(99))


    }

    @Test
    fun itShouldFindGuard10atMinute24AsTheBestOpportunityByStrategyOne() {

        val (guard, minute) = SneakInOpportunityFinder().findBestMoment(EXAMPLE_INPUT_DAY04)

        assert.that(guard, equalTo(Guard(10)))
        assert.that(minute, equalTo(Minute(24)))

    }


    @Test
    fun itShouldFindGuard99atMinute45AsTheBestOpportunityByStrategyTwo() {

        val (guard, minute) = SneakInOpportunityFinder().findGuardByMostFrequentlyAsleepMinute(EXAMPLE_INPUT_DAY04)


        assert.that(guard, equalTo(Guard(99)))
        assert.that(minute, equalTo(Minute(45)))


        val (actualGuard, actualMinute) = SneakInOpportunityFinder().findGuardByMostFrequentlyAsleepMinute(DAY04_INPUT)


        assert.that(actualGuard.value * actualMinute.value, ! equalTo(123649))


    }
}


