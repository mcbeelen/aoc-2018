package day17_reservoir_research

import util.grid.ScreenCoordinate

/**
How many tiles can be reached by the water?
To prevent counting forever,
ignore tiles with a y coordinate smaller than the smallest y coordinate in your scan data
or larger than the largest one.

Any x coordinate is valid.

In this example, the lowest y coordinate given is 1, and the highest is 13,
causing the water spring (in row 0) and the water falling off the bottom of the render
(in rows 14 through infinity)to be ignored.

So, in the example above,
    counting both water at rest (~)
    and other sand tiles the water can hypothetically reach (|),
    the total number of tiles the water can reach is 57.

How many tiles can the water reach within the range of y values in your scan?

        */




class SliceOfLand(private val squaresWithClay : Set<ScreenCoordinate>) {
    fun isThereClayAt(x: Int, y: Int) = squaresWithClay.contains(ScreenCoordinate(x, y))

}


internal fun parseScanResults(foundVeinsOfClay: String): SliceOfLand {

    val squaresWithClay : MutableSet<ScreenCoordinate> = HashSet()

    foundVeinsOfClay.trimIndent().lines().forEach {
        if (it.startsWith('x')) {
            squaresWithClay.addAll(clayInVerticalVein(it))
        } else {
            squaresWithClay.addAll(clayInHorizontalVein(it))
        }
    }

    return SliceOfLand(squaresWithClay)

}

fun clayInVerticalVein(verticalVein: String): Set<ScreenCoordinate> {
    val squaresInVerticalVein : MutableSet<ScreenCoordinate> = HashSet()
    val x: Int = verticalVein.substringAfter("x=").substringBefore(",").toInt()
    val split = verticalVein.substringAfter("y=").split("..")
    val yRange = IntRange(split[0].toInt(), split[1].toInt())

    for (y in yRange) {
        squaresInVerticalVein.add(ScreenCoordinate(x, y))
    }
    return squaresInVerticalVein
}

fun clayInHorizontalVein(horizontalVein: String): Set<ScreenCoordinate> {
    val squaresInVerticalVein : MutableSet<ScreenCoordinate> = HashSet()
    val y: Int = horizontalVein.substringAfter("y=").substringBefore(",").toInt()
    val split = horizontalVein.substringAfter("x=").split("..")
    val xRange = IntRange(split[0].toInt(), split[1].toInt())

    for (x in xRange) {
        squaresInVerticalVein.add(ScreenCoordinate(x, y))
    }
    return squaresInVerticalVein
}
