package y2018.day20_regular_map

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class RegularAcceptanceTests {


    @Test
    fun firstOfTheMoreExamples() {
        val directionsFromElf = "^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))\$"
        val facility = drawMap(directionsFromElf)

        println()
        println(directionsFromElf)
        println()
        plotFacility(facility)

        assertThat(findLengthOfShortestPathToFurthestRoom(facility), equalTo(23))


    }

    @Test
    fun itShouldBeAbleToCreateMapOfTheCompound() {

        val facility = drawMap("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))\$")

        plotFacility(facility)

        assertThat(findLengthOfShortestPathToFurthestRoom(facility), equalTo(31))


    }

}