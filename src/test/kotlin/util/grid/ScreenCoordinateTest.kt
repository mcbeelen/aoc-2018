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
}