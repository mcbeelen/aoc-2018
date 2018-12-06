package util.grid

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.grid.ScreenCoordinate


class ScreenCoordinateTest {

    @Test
    fun compareTo() {
        val first = ScreenCoordinate(1, 1)
        val second = ScreenCoordinate(1, 1)

        assertThat(first.compareTo(second), equalTo(0))

    }

    @Test
    fun `parseXcommaYInput()`() {
        assertThat(parseXcommaY("8, 3"), equalTo(ScreenCoordinate(8, 3)))
    }

    @Test
    fun distanceTo() {
        val first = ScreenCoordinate(1, 1)
        val second = ScreenCoordinate(1, 2)
        val third = ScreenCoordinate(3, 1)
        val fourth = ScreenCoordinate(5, 5)


        assertThat(first.distanceTo(first), equalTo(0))
        assertThat(first.distanceTo(second), equalTo(1))
        assertThat(first.distanceTo(third), equalTo(2))
        assertThat(first.distanceTo(fourth), equalTo(8))

        assertThat(fourth.distanceTo(second), equalTo(7))
        assertThat(fourth.distanceTo(third), equalTo(6))

    }


}