package y2018.day10_stars_align

import kotlin.system.measureTimeMillis

class SkyPlotter {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val timeMillis = measureTimeMillis {

                val originalSky = Sky(parseToStars(STAR_ALIGN_INPUT))
                val skyWithMessage = shirkToMinimumHeight(originalSky)
                plotSky(skyWithMessage)  // EJXNCCNX

                println("It took ${starMovementCounter.get()} iterations to get the message")
            }

            println("Solving it ${timeMillis}ms")

        }


    }
}

fun plotSky(sky: Sky) {

    for (y in sky.height().value) {
        for (x in sky.width().value) {
            if (sky.hasStarAt(Position(x, y))) {
                print('*')
            } else {
                print(' ')
            }
        }
        println()
    }

}


fun parseToStars(rawInput: String) = rawInput.trimIndent().lines().map { parseInputToStar(it) }