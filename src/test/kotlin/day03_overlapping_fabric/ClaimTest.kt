package day03_overlapping_fabric

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

import org.junit.Assert.*

class ClaimTest {


    @Test
    fun itShouldParseTheInput() {

        val claimInput = "#123 @ 3,2: 5x4"

        val claim = parseClaim(claimInput)

        assert.that(claim.id, equalTo("123"))

        assert.that(claim.area.origin.left, equalTo(3))
        assert.that(claim.area.origin.top, equalTo(2))

        assert.that(claim.area.width, equalTo(5))
        assert.that(claim.area.height, equalTo(4))

        assert.that(claim.area.right, equalTo(7))
        assert.that(claim.area.bottom, equalTo(5))


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