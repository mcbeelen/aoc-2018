package y2018.day18_lumber_collection

class LumberYardSolver {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            var area = parseLumberCollectionArea(LUMBER_COLLECTION_AREA_INPUT)
            for (minute in 1 .. 1_000) {
                area = letMinutePass(area)
                println("After ${minute} minutes: ${area.countOfTrees() * area.countOfLumberyards()}")
            }



        }
    }
}

class OffsetCalculator {
    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            println("1_000_000_000 has offset ${1_000_000_000.rem(28)}")
            println("972 also has an offset ${972.rem(28)}")

        }
    }
}