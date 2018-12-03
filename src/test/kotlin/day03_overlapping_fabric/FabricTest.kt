package day03_overlapping_fabric

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

const val exampleClaimsInput = """
    #1 @ 1,3: 4x4
    #2 @ 3,1: 4x4
    #3 @ 5,5: 2x2
                """

class FabricTest {

    @Test
    fun hasMultipleClaimsFor() {

        val exampleClaims = exampleClaimsInput.trimIndent().lineSequence().map { parseClaim(it) }.toList()
        val fabric = Fabric(exampleClaims)

        assert.that(fabric.countInchesOfOverlappingClaims(), equalTo(4))

    }

    @Test
    fun findIdOfNonOverlappingClaim() {
        val exampleClaims = exampleClaimsInput.trimIndent().lineSequence().map { parseClaim(it) }.toList()
        val fabric = Fabric(exampleClaims)

        assert.that(fabric.findIdOfNonOverlappingClaim(), equalTo("3"))
    }




    @Test
    fun itShouldDetemineMinimumFabricSize() {

        val claimOne = parseClaim("#1 @ 662,777: 18x27")
        val claimTwo = parseClaim("#2 @ 893,985: 13x10")

        val claims : List<Claim> = arrayListOf(claimOne, claimTwo)

        val fabric = Fabric(claims)

        assert.that(fabric.maxX, equalTo(905))
        assert.that(fabric.maxY, equalTo(994))

    }
}