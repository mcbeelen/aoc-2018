package y2020.d15

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class `Rambunctious Recitation Memory Game Test` {

    @Test
    fun examplePartOne() {
        assertThat(spokenNumber("0,3,6", 4), equalTo(0))
        assertThat(spokenNumber("0,3,6", 5), equalTo(3))
        assertThat(spokenNumber("0,3,6", 9), equalTo(4))
        assertThat(spokenNumber("0,3,6", 10), equalTo(0))
        assertThat(spokenNumber("0,3,6", 2020), equalTo(436))
    }

    @Test
    fun additionalExamplesPartOne() {
        assertThat(spokenNumber("1,3,2", 2020), equalTo(1))
        assertThat(spokenNumber("2,1,3", 2020), equalTo(10))
        assertThat(spokenNumber("1,2,3", 2020), equalTo(27))
        assertThat(spokenNumber("2,3,1", 2020), equalTo(78))
        assertThat(spokenNumber("3,2,1", 2020), equalTo(438))
        assertThat(spokenNumber("3,1,2", 2020), equalTo(1836))
    }

    @Test
    fun partOne() {
        assertThat(spokenNumber("14,8,16,0,1,17", 2020), equalTo(240))
    }

    @Test
    fun examplePartTwo() {
        assertThat(spokenNumber("0,3,6", 30000000), equalTo(175594))
    }
    @Test
    fun partTwo() {
        assertThat(spokenNumber("14,8,16,0,1,17", 30000000), equalTo(505))
    }

}
