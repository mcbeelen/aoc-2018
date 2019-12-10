package y2019.day10_asteroid_monitoring_station

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.grid.ORIGIN
import util.grid.ScreenCoordinate

typealias NumberOfVisibleAsteroids = Int
typealias AsteroidMonitoringLocation = Pair<ScreenCoordinate, NumberOfVisibleAsteroids>
class BestMonitoringStationLocationFinderTest {

    @Test
    fun firstLargeExample() {
        val bestMonitoringStationLocation = findBestMonitoringStationLocation(FIRST_LARGE_EXAMPLE)
        assertThat(bestMonitoringStationLocation.first, equalTo(ScreenCoordinate(5,8)))
        assertThat(bestMonitoringStationLocation.second, equalTo(33))
    }



    private fun findBestMonitoringStationLocation(asteroidField: String): AsteroidMonitoringLocation {
        return Pair(ORIGIN, 0)

    }
}

private const val FIRST_LARGE_EXAMPLE =  """......#.#.
                                            #..#.#....
                                            ..#######.
                                            .#.#.###..
                                            .#..#.....
                                            ..#....#.#
                                            #..#....#.
                                            .##.#..###
                                            ##...#..#.
                                            .#....####"""