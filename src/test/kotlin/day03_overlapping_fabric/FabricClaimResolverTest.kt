package day03_overlapping_fabric

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class FabricClaimResolverTest {

    @Test
    fun theAnswerToPartOneShouldBe120408() {

        val claims = DAY_03_INPUT.trimIndent().lines().map { parseClaim(it) }

        val fabric = Fabric(claims)

        assertThat(fabric.countInchesOfOverlappingClaims(), equalTo(120408))

    }

    @Test
    fun theAnswerToPartTwo() {
        val claims = DAY_03_INPUT.trimIndent().lines().map { parseClaim(it) }

        val fabric = Fabric(claims)

        assert.that(fabric.findIdOfNonOverlappingClaim(), equalTo("1276"))
    }
}