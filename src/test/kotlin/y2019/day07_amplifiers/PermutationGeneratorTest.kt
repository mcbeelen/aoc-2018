package y2019.day07_amplifiers

import com.natpryce.hamkrest.anyOf
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class PermutationGeneratorTest {
    @Test
    fun `generate single option when there is one element`() {

        val listWithOneElement = listOf(1)
        val generator = PermutationGenerator()

        assertThat(generator.generatePermutations(listWithOneElement).first(), equalTo(listWithOneElement))

    }

    @Test
    fun `generate two option when there are two elements`() {

        val listWithTwoElements = listOf(1, 2)
        val generator = PermutationGenerator()

        assertThat(generator.generatePermutations(listWithTwoElements).first(), equalTo(listWithTwoElements))
        assertThat(generator.generatePermutations(listWithTwoElements).last(), equalTo(listOf(2, 1)))

    }


    @Test
    fun `generate six options when there are three elements`() {

        val listWithThreeElements = listOf(1, 2, 3)
        val generator = PermutationGenerator()

        assertThat(generator.generatePermutations(listWithThreeElements).first(), equalTo(listWithThreeElements))
        assertThat(generator.generatePermutations(listWithThreeElements).last(), equalTo(listOf(3, 2, 1)))
        assertThat(generator.generatePermutations(listWithThreeElements).count(), equalTo(6))

    }

    @Test
    fun `generate single option when there are four elements`() {

        val listWithFourElements = listOf(1, 2, 3, 4)
        val generator = PermutationGenerator()

        assertThat(generator.generatePermutations(listWithFourElements).first(), equalTo(listWithFourElements))
        assertThat(generator.generatePermutations(listWithFourElements).last(), equalTo(listOf(4, 3, 2, 1)))
        assertThat(generator.generatePermutations(listWithFourElements).count(), equalTo(24))


    }

}