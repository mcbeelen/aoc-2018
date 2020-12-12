package y2020.d12

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.grid.ORIGIN


class `Ship Navigation Tracker Test` {

    @Test
    fun examplePartOne() {
        assertThat(trackInstructuctionAndReportDistance(testInput), equalTo(25))
    }

    @Test
    fun partOne() {
        assertThat(trackInstructuctionAndReportDistance(y2020d11input), equalTo(1186))
    }

    @Test
    fun examplePartTwo() {
        val shipNavigationSystem = `Actual Ship Navigation System`()
        shipNavigationSystem.processInstructions(testInput)
        assertThat(ORIGIN.distanceTo(shipNavigationSystem.ship.currentPosition), equalTo(286))
    }

    @Test
    fun partTwo() {
        val shipNavigationSystem = `Actual Ship Navigation System`()
        shipNavigationSystem.processInstructions(y2020d11input)
        assertThat(ORIGIN.distanceTo(shipNavigationSystem.ship.currentPosition), equalTo(47806))
    }
}

const val testInput = """F10
N3
F7
R90
F11"""
