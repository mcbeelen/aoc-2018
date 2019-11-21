package y2018.day11_chronal_charge_power_grid

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class FuelCellTest {


    @Test
    fun itShouldCalculateThePowerLevel() {

        assertThat(FuelCell(8, 3, 5).powerLevel, equalTo(4))
        assertThat(FuelCell(8, 3, 5).powerLevel, equalTo(4))


        assertThat(FuelCell(57, 122,79).powerLevel, equalTo(-5))
        assertThat(FuelCell(39, 217,196).powerLevel, equalTo(0))
        assertThat(FuelCell(71, 101,153).powerLevel, equalTo(4))


    }
}

