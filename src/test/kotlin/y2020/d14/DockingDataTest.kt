package y2020.d14

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import org.junit.Test

class DockingDataTest {

    @Test
    fun examplePartOne() {
        assertThat(`run initialization program`(testInput), equalTo(165))
    }

    @Test
    fun `it should apply the mask`() {
        val mask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X"
        assertThat(applyMask(mask, 11), equalTo(73))
    }

    @Test
    fun partOne() {
        assertThat(`run initialization program`(y2020d14_docking_data_input), equalTo(4886706177792))
    }

    @Test
    fun `it should apply the mask to address`() {
        val mask = "000000000000000000000000000000X1001X"
        val inputAddress = 42L
        val adresses = applyMaskToAddress(mask, inputAddress)
        assertThat(adresses, hasSize(equalTo(4)))
        adresses.let {
            assertThat(it[0], equalTo(26))
            assertThat(it[1], equalTo(27))
            assertThat(it[2], equalTo(58))
            assertThat(it[3], equalTo(59))
        }

    }

    @Test
    fun `example part two`() {
        assertThat(`run initialization program v2`(exampleForPartTwo), equalTo(208))
    }

    @Test
    fun partTwo() {
        assertThat(`run initialization program v2`(y2020d14_docking_data_input), equalTo(3348493585827))
    }
}


const val testInput = """mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
mem[8] = 11
mem[7] = 101
mem[8] = 0"""

const val exampleForPartTwo = """mask = 000000000000000000000000000000X1001X
mem[42] = 100
mask = 00000000000000000000000000000000X0XX
mem[26] = 1"""
