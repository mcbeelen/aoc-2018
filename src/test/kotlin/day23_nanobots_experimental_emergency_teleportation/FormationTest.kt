package day23_nanobots_experimental_emergency_teleportation

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Ignore
import org.junit.Test
import util.space.Cube
import util.space.ORIGIN
import util.space.Point
import util.space.Sector
import util.space.Sector.*

class FormationTest {


    @Test
    @Ignore // This took about 1,5 minutes
    fun itShouldShrinkTheFormation() {
        val formation = Formation(NANOBOTS_FORMATION.trimIndent().lines().map { parseNanobot(it) })

        val formationTimesOneMillionth = formation.zoomOut(1_000_000)

        val from = formationTimesOneMillionth.from.copy(x = -10)
        val to = formationTimesOneMillionth.to
        val closestToOrigin = formationTimesOneMillionth.findPointInRangeOfMostNanobotsClosestToOrigin(from, to)


        println("I'll assume in to bigger formation the point will be near: ${closestToOrigin}")

    }



    @Test
    @Ignore // This took 8 minutes, 43sec
    fun inspectOneOnTenThousand() {
        val formation = Formation(NANOBOTS_FORMATION.trimIndent().lines().map { parseNanobot(it) })

        val formationTimesOneToTentThousand = formation.zoomOut(10_000)

        val from = Point(2_400, 2_000, 3_100)
        val to = Point(2_800, 2_400, 3_500)
        val closestToOrigin = formationTimesOneToTentThousand.findPointInRangeOfMostNanobotsClosestToOrigin(from, to)

        println("I'll assume in to bigger formation the point will be near: ${closestToOrigin}")
    }


    @Test
    @Ignore// This took ???? minutes
    fun inspectOneOnOneThousand() {
        val formation = Formation(NANOBOTS_FORMATION.trimIndent().lines().map { parseNanobot(it) })

        val formationTimesOneToTentThousand = formation.zoomOut(1_000)

        val from = Point(26_500, 22_500, 33_500)
        val to = Point(27_500, 23_500, 34_500)
        val closestToOrigin = formationTimesOneToTentThousand.findPointInRangeOfMostNanobotsClosestToOrigin(from, to)

        println("I'll assume in to bigger formation the point will be near: ${closestToOrigin}")
    }




    @Test @Ignore
    fun validateExamplePartTwo() {
        val formation = parseFormation(PART_TWO_EXAMPLE_FORMATION)

        assertThat(formation.xRange, equalTo(10 .. 50))
        assertThat(formation.yRange, equalTo(10 .. 50))
        assertThat(formation.zRange, equalTo(10 .. 50))

        val from = Point(formation.xRange.first, formation.yRange.first, formation.zRange.first)
        val to = Point(formation.xRange.last, formation.yRange.last, formation.zRange.last)

       // val point = formation.findPointInRangeOfMostNanobotsClosestToOrigin(from, to)
        //val point = formation.searchFormPointInRangeOfMostNanobotsClosestToOrigin()

       // assertThat(point, equalTo(Point(12, 12, 12)))

    }


    @Test
    fun validateFindQuantityOfMaxConnectedBots() {
        val formation = parseFormation(PART_TWO_EXAMPLE_FORMATION)
        assertThat(formation.findSizeOfBiggestClique(), equalTo(5))

        val searchSpace : Cube = formation.determineSearchSpace()

        assertThat("FROM", searchSpace.from, equalTo(Point(12, 12, 10)))
        assertThat("TO",   searchSpace.to,   equalTo(Point(12, 14, 14)))

        assertThat(searchSpace.size(), equalTo(15))
    }



    @Test
    fun findQuantityOfMaxConnectedBotsInActualFormation() {
        val formation = parseFormation(NANOBOTS_FORMATION)
        assertThat(formation.findSizeOfBiggestClique(), equalTo(974))

        val biggestClique = formation.findBiggestClique()
        inspectBiggestClique(biggestClique)


//
//        assertThat(searchSpace.size(), equalTo(1_116_931_744))
//
//        val time = measureTimeMillis {
//            val candidatePoints = formation.exploreSearchSpace(searchSpace)
//
//            println("After filtering of most critical bots ${candidatePoints.size} remain")
//        }
//
//
//        println("Exploring the searchSpace for candidates took: ${time}ms")



    }

    private fun inspectBiggestClique(biggestClique: Formation) {
        println("Largest Clique: " + biggestClique.bots.count())

        if (biggestClique.bots.all { it.isPointWithinRange(ORIGIN) }) {
            println("")
        }

        val originBots = biggestClique.bots.filter { it.isPointWithinRange(ORIGIN) }
        println("Bots reaching origin: " + originBots.count())
        val botWithSmallestReachBeyondOrigin = originBots.filter { it.sector == ALPHA }.minBy { it.reachBeyondOrigin }
        val reachOfSmallestRange = botWithSmallestReachBeyondOrigin!!.reachBeyondOrigin
        println("Answer top part two MUST be smaller then $reachOfSmallestRange")


        val inOuterSpace = biggestClique.bots.filter { !it.isPointWithinRange(ORIGIN) }
        println("Bots inOuterSpace: " + inOuterSpace.count())
        val botWithFurtestBorder = inOuterSpace.maxBy { it.distanceToBorder }
        val minimumDistanceFromOrigin = botWithFurtestBorder!!.reachBeyondOrigin
        println("Answer top part two MUST be greater then $minimumDistanceFromOrigin")

        val possibleBotLimiting = inOuterSpace
                .filter { it.distanceToBorder <= reachOfSmallestRange }
                .filter { it.distanceToBorder >= minimumDistanceFromOrigin}
        possibleBotLimiting.sortedByDescending { it.distanceToBorder }
                .forEach {
                    println("${it.distanceToBorder}")
                }

        // 84087794 is too low!

        val searchSpace = biggestClique.determineSearchSpace()

        println("The answer to part two is somewhere between ${searchSpace.from} and ${searchSpace.to}")
    }


    @Test
    fun educateGuess() {

        val formation = parseFormation(NANOBOTS_FORMATION)

        // 29652711	38661892

        val x = 25023255 // 27_337_983
        val z = 22352653 // 30_507_272

        var quantity = 0

        (19_298_728 .. 30_648_083).forEach { y->
            val point = Point(x, y, z)

            val counter = formation.bots.filter { it.isPointWithinRange(point) }.count()
            if (counter > quantity) {
                println("From ${y} the number of bots is ${counter} with distance ${point.distanceTo(ORIGIN)}")
                quantity =  counter
            }
            if (counter == 974) {
                println("Reached the magic number with y: $y at ${point.distanceTo(ORIGIN)}")
            }
        }


    }

    @Test
    fun figureOutSector() {
        val formation = parseFormation(NANOBOTS_FORMATION)
        val biggestClique = formation.findBiggestClique()
        val botsWithSideToOrigin = Formation(biggestClique.bots.filter { !it.isPointWithinRange(ORIGIN) })

        val sectorsWithNumberOfBots = botsWithSideToOrigin.bots.map { it.sector }.groupingBy { it }.eachCount()

        println(sectorsWithNumberOfBots.maxBy { it.value })




    }
}


