package y2018.day25_four_dimensional_adventure

import kotlin.math.abs


data class SpaceTimePoint(val x: Int, val y: Int, val z: Int, val t: Int) {

    fun isInSameConstellation(other: SpaceTimePoint): Boolean {
        return distanceTo(other) <= 3
    }

    fun distanceTo(point: SpaceTimePoint) = abs(x - point.x) +
            abs(y - point.y) +
            abs(z - point.z) +
            abs(t - point.t)


}

fun parseToSpaceTimePoint(spaceTimePointDefinition: String): SpaceTimePoint {

    val coordinates = spaceTimePointDefinition
            .split(",")
            .map { it.trim().toInt() }
    return SpaceTimePoint(coordinates[0], coordinates[1], coordinates[2], coordinates[3])

}


