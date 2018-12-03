package day03_overlapping_fabric

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

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


    @Test
    fun itShouldDetemineMinimumFabricSize() {

        val claimOne = parseClaim("#1 @ 662,777: 18x27")
        val claimTwo = parseClaim("#2 @ 893,985: 13x10")

        val claims : List<Claim> = arrayListOf(claimOne, claimTwo)

        val fabric = Fabric(claims)

        assertThat(fabric.maxX, equalTo(905))
        assertThat(fabric.maxY, equalTo(994))

    }
}