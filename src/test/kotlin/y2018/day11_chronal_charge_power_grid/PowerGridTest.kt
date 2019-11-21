package y2018.day11_chronal_charge_power_grid

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.grid.ScreenCoordinate

class PowerGridTest {


    @Test
    fun itShouldFindMostPowerfulSquare() {

        val grid = PowerGrid(18)

        assertThat(grid.powerLevelAt(ScreenCoordinate(33, 45)), equalTo(4))

        val topLevelCorner : ScreenCoordinate = grid.findTopLevelCornerOfMostPowerfulSquare(3).first

        topLevelCorner.let {
            assertThat(it.left, equalTo(33) )
            assertThat(it.top, equalTo(45) )

        }


        val anySizeSquare : Pair<ScreenCoordinate, Int> = grid.findTopLevelCornerOfMostPowerfulSquareOfAnySize()


        anySizeSquare.first.let {
            assertThat(it.left, equalTo(90) )
            assertThat(it.top, equalTo(269) )
        }
        assertThat(anySizeSquare.second, equalTo(16))





    }
}