package y2018.day20_regular_map

import com.natpryce.hamkrest.MatchResult
import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.assertion.assertThat
import y2018.day20_regular_map.Bearing.*
import org.junit.Test


class FacilityTest {


    @Test
    fun itShouldBeAbleToParseTheFirstExample() {

        val mapOfCompound = drawMap("^WNE\$")

        val origin = Room(0 , 0)

        assertThat(mapOfCompound, hasDoorInDirection(origin, WEST))




    }

    /**
     *
#########
#.|.|.|.#
#-#######
#.|.|.|.#
#-#####-#
#.#.#X|.#
#-#-#####
#.|.|.|.#
#########

     */
    @Test
    fun itShouldBeAbleToBuildMapForTheSecondExample() {
        val facility = drawMap("^ENWWW(NEEE|SSE(EE|N))\$")

        val origin = Room(0 , 0)

        assertThat(facility, hasDoorInDirection(origin, EAST))

        plotFacility(facility)

    }

    private fun hasDoorInDirection(origin: Room, direction: Bearing): Matcher<Facility> {

        return object : Matcher<Facility> {
            override val description: String
                get() = "does have a door heading ${direction} from ${origin}"

            override fun invoke(actual: Facility): MatchResult {
                if (actual.hasDoorToThe(origin, direction)) {
                    return MatchResult.Match
                } else {
                    return MatchResult.Mismatch("did not find a door heading ${direction} from ${origin}")
                }

            }

        }

    }


}