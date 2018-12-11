package day11_chronal_charge_power_grid

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class FuelCellTest {


    @Test
    fun itShouldCalculateThePowerLevel() {

        assertThat(FuelCell(3, 5).calculatePowerLevelInGrid(8), equalTo(4))


    }
}

class FuelCell(val x: Int, val y: Int) {
    fun calculatePowerLevelInGrid(gridSerialNumber: Int): Int {
        val rackID = x + 10
        val baseForPowerLevel = ((rackID * y) + gridSerialNumber) * rackID
        val hundredsDigit = if (baseForPowerLevel < 100) {
             0 - 5
        } else {
            baseForPowerLevel.rem(1000).div(100)
        }
        return hundredsDigit - 5
    }

    override fun toString(): String {
        return "FuelCell: '${x},${y})"
    }


}
