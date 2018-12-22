package day22_node_maze

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.grid.ScreenCoordinate

class CaveTest {

    @Test
    fun `validateDetermineRiskLevel()`() {
        val cave = Cave(510, ScreenCoordinate(10, 10))
        cave.exploreUntilTarget()

        assertThat(cave.calculateRiskLevel(), equalTo(114))

    }

    @Test
    fun validateCalculateGeolocicIndex() {
        val cave = Cave(10647, ScreenCoordinate(7, 770))

        assertThat(cave.calculateGeolocicIndex(0, 0), equalTo(0))

        assertThat(cave.calculateGeolocicIndex(7, 770), equalTo(0))

        assertThat(cave.calculateGeolocicIndex(1, 0), equalTo(16807))
        assertThat(cave.calculateGeolocicIndex(2, 0), equalTo(33614))

        assertThat(cave.calculateGeolocicIndex(0, 1), equalTo(48271))
        assertThat(cave.calculateGeolocicIndex(0, 3), equalTo(144813))
    }
}