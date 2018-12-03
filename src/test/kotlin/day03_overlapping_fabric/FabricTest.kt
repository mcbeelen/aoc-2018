package day03_overlapping_fabric

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Before
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

        assertThat(fabric.countInchesOfOverlappingClaims(), equalTo(4))

    }

    @Test
    fun findIdOfNonOverlappingClaim() {
        val exampleClaims = exampleClaimsInput.trimIndent().lineSequence().map { parseClaim(it) }.toList()
        val fabric = Fabric(exampleClaims)

        assertThat(fabric.findIdOfNonOverlappingClaim(), equalTo("3"))
    }
}