package day20_regular_map

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class RegularAcceptanceTests {


    @Test
    fun itShouldBeAbleToCreateMapOfTheCompound() {

        val mapOfCompound = drawMap("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))\$")


        assertThat(mapOfCompound.findLengthOfShortestPathToFurthestRoom(), equalTo(31))


    }

}