package day17_reservoir_research

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

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
    fun itShouldFind57TilesWithWater() {

        val timesWithWater = findTilesWithWater(EXAMPLE_VEINS_OF_CLAY_INPUT)
        assertTrue(timesWithWater.any { it.location.isAt(500, 1)})


        assertThat(timesWithWater.size, equalTo(57))


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