package day03_overlapping_fabrik

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.grid.ScreenCoordinate
import util.grid.Square

const val OFFSET_SEPARATOR = " @ "

class Problem3Test {


    @Test
    fun itShouldParseTheInput() {

        val claimInput = "#123 @ 3,2: 5x4"

        val claim: Pair<String, Square> = parseClaim(claimInput)

        assertThat(claim.first, equalTo("123"))

        assertThat(claim.second.origin.left, equalTo(3))
        assertThat(claim.second.origin.top, equalTo(2))

        assertThat(claim.second.width, equalTo(5))
        assertThat(claim.second.height, equalTo(4))

    }



    private fun parseClaim(claimInput: String): Pair<String, Square> {

        val id = claimInput.substringBefore(OFFSET_SEPARATOR).substringAfter("#")
        val offsetInput = claimInput.substringAfter(OFFSET_SEPARATOR).substringBefore(":")
        val split = offsetInput.split(",")
        val origin = ScreenCoordinate(split[0].toInt(), split[1].toInt())

        val dimensionInput = claimInput.substringAfter(": ")
        val dimensionComponents = dimensionInput.split("x")



        return Pair(id, Square(origin, dimensionComponents[0].toInt(), dimensionComponents[1].toInt()))



    }
}