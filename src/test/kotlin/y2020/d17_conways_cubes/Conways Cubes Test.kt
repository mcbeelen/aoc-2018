package y2020.d17_conways_cubes

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class `Conways Cubes Test` {

    @Test
    fun examplePartOne() {
        assertThat(conwaysGameOfLiveIn3D(testInput), equalTo(112))
    }

    @Test
    fun partTwo() {
        assertThat(conwaysGameOfLiveIn4D(y2020d17input), equalTo(2276))

    }
}


const val testInput = """.#.
..#
###"""
