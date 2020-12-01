package y2018.day03_overlapping_fabric

import util.grid.ScreenCoordinate

class Fabric(private val claims: Iterable<Claim>) {


    val maxX: Int by lazy {
        claims.map { it.area.right }.max() ?: 0
    }
    val maxY: Int by lazy {
        claims.map { it.area.bottom }.max() ?: 0
    }


    fun hasMultipleClaimsFor(inchOfFabric: ScreenCoordinate) : Boolean {

        var firstFound = false
        claims.forEach {
            if (it.area.contains(inchOfFabric)) {
                if (firstFound) return true
                firstFound = true
            }
        }
        return false

    }



    fun countInchesOfOverlappingClaims(): Int {
        var numberOfInchesWithOverlappingClaims = 0

        for (x in 0..maxX) {
            for (y in 0..maxY) {
                val inchOfFabric = ScreenCoordinate(x, y)
                if (hasMultipleClaimsFor(inchOfFabric)) {
                    numberOfInchesWithOverlappingClaims++
                }
            }
        }

        return numberOfInchesWithOverlappingClaims
    }

    fun findIdOfNonOverlappingClaim() = claims
            .filter {
                hasNoOverlaps(it)
            }
            .single().id


    private fun hasNoOverlaps(candidate: Claim) = claims.none { it.overlapsWith(candidate) }


}
