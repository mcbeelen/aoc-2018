package day17_reservoir_research

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.greaterThan
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Ignore
import org.junit.Test
import kotlin.Int.Companion.MIN_VALUE

class ReservoirResearchTest {



    @Test
    fun itShouldParseInputIntoSliceOfLand() {


        val sliceOfLand = parseScanResults(EXAMPLE_VEINS_OF_CLAY_INPUT)


        assertFalse(sliceOfLand.isThereClayAt(495, 1))
        assertTrue(sliceOfLand.isThereClayAt(495, 2))
        assertTrue(sliceOfLand.isThereClayAt(495, 3))

        assertFalse(sliceOfLand.isThereClayAt(494, 2))
        assertFalse(sliceOfLand.isThereClayAt(496, 2))

        assertTrue(sliceOfLand.isThereClayAt(495, 7))
        assertFalse(sliceOfLand.isThereClayAt(495, 8))

        assertFalse(sliceOfLand.isThereClayAt(494, 7))
        assertTrue(sliceOfLand.isThereClayAt(495, 7))
        assertTrue(sliceOfLand.isThereClayAt(496, 7))
        assertTrue(sliceOfLand.isThereClayAt(500, 7))
        assertTrue(sliceOfLand.isThereClayAt(501, 7))
        assertFalse(sliceOfLand.isThereClayAt(502, 7))

    }


    @Test
    fun itShouldPassAlongMissedBucket() {

        val sliceOfLand = parseScanResults("""
            x=499, y=1..2
            x=503, y=1..2
        """.trimMargin())
        sliceOfLand.exploreToFindAllTilesOfWater()
        assertThat(sliceOfLand.countTilesWithWater(), equalTo(2))

    }


    @Test
    fun itShouldFlowAroundSingleDot() {

        val sliceOfLand = parseScanResults("""
            x=500, y=2..2
            x=510, y=1..2
            """)
        sliceOfLand.exploreToFindAllTilesOfWater()
        assertThat(sliceOfLand.countTilesWithWater(), equalTo(5))

    }

    @Test
    fun itShouldFlowAroundSmallStructure() {

        val sliceOfLand = parseScanResults("""
            x=500, y=2..2
            x=510, y=1..2
            y=3, x=499..501
            """)
        sliceOfLand.exploreToFindAllTilesOfWater()
        assertThat(sliceOfLand.countTilesWithWater(), equalTo(9))

    }

    @Test
    fun itShouldFlowFromSingleEndedPlateau() {

        val sliceOfLand = parseScanResults("""
            y=2, x=503..503
            y=3, x=496..503
            """)
        sliceOfLand.exploreToFindAllTilesOfWater()
        assertThat(sliceOfLand.countTilesWithWater(), equalTo(9))

    }


    @Test
    fun itShouldOverflowEqualSidedBucket() {

        val sliceOfLand = parseScanResults("""
            x=498, y=2..3
            x=502, y=2..3
            y=3, x=498..502
            x=510, y=1..1
            """)
        sliceOfLand.exploreToFindAllTilesOfWater()
        assertThat(sliceOfLand.countTilesWithWater(), equalTo(14))

    }

    @Test
    fun itShouldOverflowLopsidedBucket() {

        val sliceOfLand = parseScanResults("""
            x=499, y=2..4
            x=501, y=3..4
            y=4, x=499..501
            x=510, y=1..1
            """)
        sliceOfLand.exploreToFindAllTilesOfWater()
        assertThat(sliceOfLand.countTilesWithWater(), equalTo(7))

    }

    @Test
    fun itShouldFillBothSidesOfCommunicatingVessels() {

        val sliceOfLand = parseScanResults("""
            x=490, y=3..9
            x=508, y=2..9
            y=9, x=490..508
            x=502, y=5..6
            x=503, y=5..6
            x=510, y=1..1
            """)
        sliceOfLand.exploreToFindAllTilesOfWater()

        plotReservoir(sliceOfLand )
        assertThat(sliceOfLand.countTilesWithWater(), equalTo(125))

    }







    @Test
    fun itShouldFind57TilesWithWater() {

        val timesWithWater = findAllTilesWithWater(EXAMPLE_VEINS_OF_CLAY_INPUT)

        assertThat(timesWithWater.countTilesWithWater(), equalTo(57))

    }

    @Test @Ignore
    fun actualPartOne() {

        val timesWithWater = findAllTilesWithWater(CLAY_SCAN_RESULTS)


        plotReservoir(timesWithWater)


        assertThat(timesWithWater.countTilesWithWater(), greaterThan(499))
        assertThat(timesWithWater.countTilesWithWater(), equalTo(MIN_VALUE))

    }


}





const val EXAMPLE_VEINS_OF_CLAY_INPUT = """
x=495, y=2..7
y=7, x=495..501
x=501, y=3..7
x=498, y=2..4
x=506, y=1..2
x=498, y=10..13
x=504, y=10..13
y=13, x=498..504"""