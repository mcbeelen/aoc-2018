package day03_overlapping_fabrik

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class Problem3Test {


    @Test
    fun itShouldParseTheInput() {



        assertThat(DAY_03_INPUT.trimIndent().lines().size, equalTo(1))

    }
}