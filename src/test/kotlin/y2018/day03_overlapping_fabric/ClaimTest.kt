package y2018.day03_overlapping_fabric

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

import org.junit.Assert.*

class ClaimTest {


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
    fun itShouldNotOverlapWithItself() {

        val claimOne = parseClaim("#1 @ 1,3: 4x4")
        val claimTwo = parseClaim("#2 @ 3,1: 4x4")
        val claimThree = parseClaim("#3 @ 5,5: 2x2")

        assertFalse(claimOne.overlapsWith(claimOne))

        assertTrue(claimOne.overlapsWith(claimTwo))
        assertFalse(claimOne.overlapsWith(claimThree))
        assertFalse(claimTwo.overlapsWith(claimThree))


    }
}