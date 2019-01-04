package util.space

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.greaterThan
import org.junit.Test


class CubeTest {

    @Test
    fun sizeShouldBePositive() {

        val cube = Cube(Point(161365893, -10481220, 27574663),
                Point(36583197, 101582892, 205344607))

        assertThat(cube.size(), greaterThan(0L))

    }

    @Test
    fun sizeShouldBePositiveForDoubleNegatives() {

        val cube = Cube(Point(-161365893, -10481220, 27574663),
                Point(36583197, 101582892, 205344607))

        assertThat(cube.size(), greaterThan(0L))

    }
}