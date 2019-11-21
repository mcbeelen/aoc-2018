package y2018.day03_overlapping_fabric

import util.grid.ScreenCoordinate
import util.grid.Square

data class Claim(val id: String, val area: Square) {

    fun overlapsWith(other: Claim): Boolean {
        if (id.equals(other.id)) {
            return false
        }
        return area.overLapsWith(other.area)
    }
}


fun parseClaim(claimInput: String): Claim {


    try {
        val id = claimInput.substringBefore(" @ ").substringAfter("#")
        val offsetInput = claimInput.substringAfter(" @ ").substringBefore(":")
        val split = offsetInput.split(",")
        val origin: ScreenCoordinate
        origin = ScreenCoordinate(split[0].toInt(), split[1].toInt())
        val dimensionInput = claimInput.substringAfter(": ")
        val dimensionComponents = dimensionInput.split("x")



        return Claim(id, Square(origin, dimensionComponents[0].toInt(), dimensionComponents[1].toInt()))
    } catch (e: NumberFormatException) {
        System.err.println("Failed to parse: ${claimInput}")
        throw e
    }




}