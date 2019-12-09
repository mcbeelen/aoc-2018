package util.collections

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class PermutationGeneratorTest {
    @Test
    fun `generate single option when there is one element`() {

        val listWithOneElement = listOf(1)

        assertThat(generatePermutations(listWithOneElement).first(), equalTo(listWithOneElement))

    }

    @Test
    fun `generate two option when there are two elements`() {

        val listWithTwoElements = listOf(1, 2)

        assertThat(generatePermutations(listWithTwoElements).first(), equalTo(listWithTwoElements))
        assertThat(generatePermutations(listWithTwoElements).last(), equalTo(listOf(2, 1)))

    }


    @Test
    fun `generate six options when there are three elements`() {

        val listWithThreeElements = listOf(1, 2, 3)

        assertThat(generatePermutations(listWithThreeElements).first(), equalTo(listWithThreeElements))
        assertThat(generatePermutations(listWithThreeElements).last(), equalTo(listOf(3, 2, 1)))
        assertThat(generatePermutations(listWithThreeElements).count(), equalTo(6))

    }

    @Test
    fun `generate single option when there are four elements`() {

        val listWithFourElements = listOf(1, 2, 3, 4)

        assertThat(generatePermutations(listWithFourElements).first(), equalTo(listWithFourElements))
        assertThat(generatePermutations(listWithFourElements).last(), equalTo(listOf(4, 3, 2, 1)))
        assertThat(generatePermutations(listWithFourElements).count(), equalTo(24))


    }

}