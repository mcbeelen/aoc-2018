package day03_overlapping_fabric

import util.grid.ScreenCoordinate
import util.grid.Square
import java.lang.NumberFormatException

data class Claim(val id: String, val area: Square) {



    fun overlapsWith(other: Claim): Boolean {
        // println("Comparing ${id} to ${other.id}")

        if (id.equals(other.id)) {
            return false
        }
        val overLapsWith = this.area.overLapsWith(other.area)
        if (overLapsWith) {
            area.getAllCoordinates()


            println("${other.id} overlaps with ${id}")
        }
        return overLapsWith
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