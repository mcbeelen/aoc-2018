package day20_regular_map

import com.natpryce.hamkrest.MatchResult
import com.natpryce.hamkrest.Matcher
import com.natpryce.hamkrest.assertion.assertThat
import day20_regular_map.CardinalDirection.*
import org.junit.Test


class RegularMapOfCompoundTest {


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
        val mapOfCompound = drawMap("^ENWWW(NEEE|SSE(EE|N))\$")

        val origin = Room(0 , 0)

        assertThat(mapOfCompound, hasDoorToTheNorth(origin, NORTH))

    }

    private fun hasDoorToTheNorth(origin: Room, direction: CardinalDirection): Matcher<RegularMapOfCompound> {

        return object : Matcher<RegularMapOfCompound> {
            override val description: String
                get() = "does have a door heading ${direction} from ${origin}"

            override fun invoke(actual: RegularMapOfCompound): MatchResult {
                if (actual.hasDoorToThe(origin, direction)) {
                    return MatchResult.Match
                } else {
                    return MatchResult.Mismatch("did not find a door heading ${direction} from ${origin}")
                }

            }

        }

    }


}