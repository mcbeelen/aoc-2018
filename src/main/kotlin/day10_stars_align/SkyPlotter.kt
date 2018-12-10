package day10_stars_align

class SkyPlotter {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val originalSky = Sky(parseToStars(STAR_ALIGN_INPUT))


        }




    }
}


fun parseToStars(rawInput: String) = rawInput.trimIndent().lines().map { parseInputToStar(it) }