package day03_overlapping_fabric

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test


class FabricTest {

    @Test
    fun hasMultipleClaimsFor() {

        val exampleClaims = """
            #1 @ 1,3: 4x4
#2 @ 3,1: 4x4
#3 @ 5,5: 2x2
        """.trimIndent().lineSequence().map { parseClaim(it) }
                .toList()
                .toList()


        val fabric = Fabric(exampleClaims)

        assertThat(fabric.countInchesOfOverlappingClaims(), equalTo(4))


    }
}