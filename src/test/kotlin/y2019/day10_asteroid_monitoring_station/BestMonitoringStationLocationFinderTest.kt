package y2019.day10_asteroid_monitoring_station

import com.natpryce.hamkrest.anyElement
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.lessThan
import org.junit.Test
import util.grid.ScreenCoordinate
import util.grid.at


class BestMonitoringStationLocationFinderTest {

    @Test
    fun initialExample() {
        val bestMonitoringStationLocation = findBestMonitoringStationLocation(INITIAL_EXAMPLE)
        assertThat(bestMonitoringStationLocation.first, equalTo(ScreenCoordinate(3, 4)))
        assertThat(bestMonitoringStationLocation.second, equalTo(8))
    }


    @Test
    fun firstLargeExample() {
        val bestMonitoringStationLocation = findBestMonitoringStationLocation(FIRST_LARGE_EXAMPLE)
        assertThat(bestMonitoringStationLocation.second, equalTo(33))
        assertThat(bestMonitoringStationLocation.first, equalTo(ScreenCoordinate(5, 8)))
    }

    @Test
    fun verifyFirstExample() {
        val asteroidMap: AsteroidMap = convertFieldToMap(FIRST_LARGE_EXAMPLE.trimIndent())
        assertThat(findNumberOfVisibleAsteroids(at(5, 8), asteroidMap), equalTo(33))
        assertThat(findNumberOfVisibleAsteroids(at(4, 7), asteroidMap), lessThan(33))
    }


    @Test
    fun `convert field to map`() {
        val asteroidMap: AsteroidMap = convertFieldToMap(FIRST_LARGE_EXAMPLE.trimIndent())

        assertThat(asteroidMap.asteroids, anyElement(equalTo(at(0, 1))))
        assertThat(asteroidMap.asteroids, anyElement(equalTo(at(6, 0))))
        assertThat(asteroidMap.asteroids, anyElement(equalTo(at(8, 0))))
    }


    @Test
    fun findNumberOfVisibleAsteroidsShouldFind7at02() {
        val asteroidMap: AsteroidMap = convertFieldToMap(INITIAL_EXAMPLE.trimIndent())
        assertThat(findNumberOfVisibleAsteroids(at(0, 2), asteroidMap), equalTo(6))
        assertThat(findNumberOfVisibleAsteroids(at(1, 0), asteroidMap), equalTo(7))
        assertThat(findNumberOfVisibleAsteroids(at(3, 4), asteroidMap), equalTo(8))
    }

    @Test
    fun largeExample() {
        val bestMonitoringStationLocation = findBestMonitoringStationLocation(LARGEST_EXAMPLE)
        assertThat(bestMonitoringStationLocation.second, equalTo(210))
        assertThat(bestMonitoringStationLocation.first, equalTo(ScreenCoordinate(11, 13)))
    }

    @Test
    fun `200th asteroid blast`() {

        assertThat(asteroidBlastedAs200th(LARGEST_EXAMPLE), equalTo(at(8, 2)))
        assertThat(asteroidBlastedAs200th(ASTEROID_FIELD), equalTo(at(4, 16)))

    }
}


private const val INITIAL_EXAMPLE = """
.#..#
.....
#####
....#
...##
"""

private const val FIRST_LARGE_EXAMPLE = """
......#.#.
#..#.#....
..#######.
.#.#.###..
.#..#.....
..#....#.#
#..#....#.
.##.#..###
##...#..#.
.#....####"""

private const val LARGEST_EXAMPLE = """
.#..##.###...#######
##.############..##.
.#.######.########.#
.###.#######.####.#.
#####.##.#.##.###.##
..#####..#.#########
####################
#.####....###.#.#.##
##.#################
#####.##.###..####..
..######..##.#######
####.##.####...##..#
.#####..#.######.###
##...#.##########...
#.##########.#######
.####.#.###.###.#.##
....##.##.###..#####
.#.#.###########.###
#.#.#.#####.####.###
###.##.####.##.#..##    
"""