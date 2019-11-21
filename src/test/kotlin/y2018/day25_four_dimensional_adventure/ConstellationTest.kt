package y2018.day25_four_dimensional_adventure

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test


class ConstellationTest {


    @Test
    fun firstExample() {

        val explaination = """ 0,0,0,0
 3,0,0,0
 0,3,0,0
 0,0,3,0
 0,0,0,3
 0,0,0,6
 9,0,0,0
12,0,0,0"""


        val spaceTimePoints = explaination.trimIndent().lines().map { parseToSpaceTimePoint(it) }

        assertThat(countNumberOfConstellations(spaceTimePoints), equalTo(2))

    }

    @Test
    fun secondExample() {
        val input = """-1,2,2,0
0,0,2,-2
0,0,0,-2
-1,2,0,0
-2,-2,-2,2
3,0,2,-1
-1,3,2,2
-1,0,-1,0
0,2,1,-2
3,0,0,0"""

        val spaceTimePoints = input.trimIndent().lines().map { parseToSpaceTimePoint(it) }

        assertThat(countNumberOfConstellations(spaceTimePoints), equalTo(4))


    }


    @Test
    fun finalExample() {
        val input = """1,-1,-1,-2
-2,-2,0,1
0,2,1,3
-2,3,-2,1
0,2,3,-2
-1,-1,1,-2
0,-2,-1,0
-2,2,3,-1
1,2,2,0
-1,-2,0,-2"""

        val spaceTimePoints = input.trimIndent().lines().map { parseToSpaceTimePoint(it) }

        assertThat(countNumberOfConstellations(spaceTimePoints), equalTo(8))

    }
}