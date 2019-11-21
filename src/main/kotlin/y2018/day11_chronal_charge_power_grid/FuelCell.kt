package y2018.day11_chronal_charge_power_grid

class FuelCell(val gridSerialNumber: Int, val x: Int, val y: Int) {

    val powerLevel : Int by lazy {
        calculatePowerLevelInGrid()
    }


    private fun calculatePowerLevelInGrid(): Int {
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