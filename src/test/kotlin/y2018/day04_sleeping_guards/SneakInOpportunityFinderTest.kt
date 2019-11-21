package y2018.day04_sleeping_guards

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import org.junit.Test

class SneakInOpportunityFinderTest {

    @Test
    fun parseRecordsToShifts() {
        val opportunityFinder = SneakInOpportunityFinder()
        val records = opportunityFinder.parseInputIntoRecords(EXAMPLE_INPUT_DAY04)
        val shifts = opportunityFinder.extractShifts(records)

        assertThat(shifts, hasSize(equalTo(5)))

        assertThat(shifts[0].guard.value, equalTo(10))
        val naps = shifts[0].naps
        assertThat(naps, hasSize(equalTo(2)))
        assertThat(naps[0].first, equalTo(5))
        assertThat(naps[0].last, equalTo(24))

        assertThat(naps[1].first, equalTo(30))
        assertThat(naps[1].last, equalTo(54))

        assertThat(shifts[1].guard.value, equalTo(99))
        assertThat(shifts[2].guard.value, equalTo(10))
        assertThat(shifts[3].guard.value, equalTo(99))
        assertThat(shifts[4].guard.value, equalTo(99))


    }

    @Test
    fun itShouldFindGuard10atMinute24AsTheBestOpportunityByStrategyOne() {

        val (guard, minute) = SneakInOpportunityFinder().findBestMoment(EXAMPLE_INPUT_DAY04)

        assertThat(guard, equalTo(Guard(10)))
        assertThat(minute, equalTo(Minute(24)))

    }


    @Test
    fun itShouldFindGuard99atMinute45AsTheBestOpportunityByStrategyTwo() {

        val (guard, minute) = SneakInOpportunityFinder().findGuardByMostFrequentlyAsleepMinute(EXAMPLE_INPUT_DAY04)


        assertThat(guard, equalTo(Guard(99)))
        assertThat(minute, equalTo(Minute(45)))


        val (actualGuard, actualMinute) = SneakInOpportunityFinder().findGuardByMostFrequentlyAsleepMinute(DAY04_INPUT)


        assertThat(actualGuard.value * actualMinute.value, ! equalTo(123649))


    }
}


