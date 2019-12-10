package util.grid

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class VectorTest {

    @Test
    fun simplify() {
        assertThat(simplify(Vector(-2, 4)), equalTo(Vector(-1,2)))
        assertThat(simplify(Vector(-480, 720)), equalTo(Vector(-2,3)))
    }

    @Test
    fun `simplify with zero`() {
        assertThat(simplify(Vector(3, 0)), equalTo(Vector(1, 0)))
        assertThat(simplify(Vector(0, 4)), equalTo(Vector(0, 1)))
    }
}