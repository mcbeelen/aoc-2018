package day10_stars_align

class SkyPlotter {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val originalSky = Sky(parseToStars(STAR_ALIGN_INPUT))
            val skyWithMessage = shirkToMinimumHeight(originalSky)
            plotSky(skyWithMessage)  // EJXNCCNX

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