package day03_overlapping_fabric

import org.junit.Test

import org.junit.Assert.*

class ClaimTest {

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