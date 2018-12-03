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

        val claim = parseClaim(claimInput)

        assertThat(claim.id, equalTo("123"))

        assertThat(claim.area.origin.left, equalTo(3))
        assertThat(claim.area.origin.top, equalTo(2))

        assertThat(claim.area.width, equalTo(5))
        assertThat(claim.area.height, equalTo(4))

        assertThat(claim.area.right, equalTo(7))
        assertThat(claim.area.bottom, equalTo(5))


    }



    private fun parseClaim(claimInput: String): Claim {

        val id = claimInput.substringBefore(OFFSET_SEPARATOR).substringAfter("#")
        val offsetInput = claimInput.substringAfter(OFFSET_SEPARATOR).substringBefore(":")
        val split = offsetInput.split(",")
        val origin = ScreenCoordinate(split[0].toInt(), split[1].toInt())

        val dimensionInput = claimInput.substringAfter(": ")
        val dimensionComponents = dimensionInput.split("x")



        return Claim(id, Square(origin, dimensionComponents[0].toInt(), dimensionComponents[1].toInt()))



    }
}