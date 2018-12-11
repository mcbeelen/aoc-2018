package day11_chronal_charge_power_grid

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.grid.ScreenCoordinate

class PowerGridTest {


    @Test
    fun itShouldFindMostPowerfulSquare() {

        val grid = PowerGrid(18)

        assertThat(grid.powerLevelAt(ScreenCoordinate(33, 45)), equalTo(4))

        val topLevelCorner : ScreenCoordinate = grid.findTopLevelCornerOfMostPowerfulSquare()

        topLevelCorner.let {
            assertThat(it.left, equalTo(33) )
            assertThat(it.top, equalTo(45) )

        }

    }
}