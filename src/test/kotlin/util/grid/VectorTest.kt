package util.grid

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.closeTo
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class VectorTest {

    @Test
    fun simplify() {
        assertThat(simplify(Vector(-2, 4)), equalTo(Vector(-1, 2)))
        assertThat(simplify(Vector(-480, 720)), equalTo(Vector(-2, 3)))
    }

    @Test
    fun `simplify with zero`() {
        assertThat(simplify(Vector(3, 0)), equalTo(Vector(1, 0)))
        assertThat(simplify(Vector(0, 4)), equalTo(Vector(0, 1)))
    }

    @Test
    fun `verify as angle`() {
        assertThat(Vector(0, -1).asAngle(), closeTo(0.0, error = 0.001))

        assertThat("NE", Vector(1, -4).asAngle(), closeTo(15.50, error = 5.000))
        assertThat("NE", Vector(1, -1).asAngle(), closeTo(45.0, error = 0.001))
        assertThat("NE", Vector(4, -1).asAngle(), closeTo(75.0, error = 5.001))

        assertThat("SE1", Vector(8, 1).asAngle(), closeTo(95.0, error = 5.000))
        assertThat("SE2", Vector(1, 1).asAngle(), closeTo(135.0, error = 0.001))
        assertThat("SE3", Vector(1, 7).asAngle(), closeTo(175.0, error = 5.00))

        assertThat("SW1", Vector(-1, 8).asAngle(), closeTo(185.0, error = 5.001))
        assertThat("SW2", Vector(-1, 1).asAngle(), closeTo(225.0, error = 0.001))
        assertThat("SW3", Vector(-8, 1).asAngle(), closeTo(265.0, error = 5.001))

        assertThat("NW1", Vector(-8, -1).asAngle(), closeTo(275.0, error = 5.001))
        assertThat("NW2", Vector(-1, -1).asAngle(), closeTo(315.0, error = 0.001))
        assertThat("NW3", Vector(-1, -8).asAngle(), closeTo(355.0, error = 5.001))
    }
}