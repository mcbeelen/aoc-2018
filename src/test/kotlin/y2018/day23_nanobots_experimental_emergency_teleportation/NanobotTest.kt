package y2018.day23_nanobots_experimental_emergency_teleportation

import com.google.common.math.Stats
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import util.space.Cube
import util.space.Point
import kotlin.math.roundToInt
import kotlin.ranges.IntRange.Companion.EMPTY

class NanobotTest {


    @Test
    fun itShouldReachCertainCubes() {
        val bot = Nanobot(Point(10, 10, 10), 11)
        assertTrue(bot.reaches(Cube(Point(-20, -20, -20), Point(100, 100, 0))))
        assertTrue(bot.reaches(Cube(Point(-20, -20, -20), Point(6, 6, 7))))
        assertFalse(bot.reaches(Cube(Point(-20, -20, -20), Point(6, 6, 6))))
        assertFalse(bot.reaches(Cube(Point(-20, -20, -20), Point(100, 100, -2))))
    }

    @Test
    fun itShouldDetermineTheBiggestBotToBeAtZeroZeroZero() {

        val bots : List<Nanobot> = PART_ONE_EXAMPLE_BOTS.trimIndent().lines().map { parseNanobot(it) }
        val botWithBiggestRange = bots.maxBy { it.range }!!

        assertThat(botWithBiggestRange.point, equalTo(Point(0, 0, 0)))

        val numberOfBotsInRange = bots.filter { isBotWithinRangeOf(it, botWithBiggestRange) }
        assertThat(numberOfBotsInRange.size, equalTo(7))

    }


    @Test
    fun itShouldDetermineReachIntoSectorHotel() {

        val alpha = Nanobot(Point(-20, -30, -10), 75)
        assertThat(alpha.rangeInSectorHotel(), equalTo(0 .. 15))

        val beta = Nanobot(Point(-20, 60, -10), 75)
        assertThat(beta.rangeInSectorHotel(), equalTo(15 .. 105))

        val charlie = Nanobot(Point(75, -20, -10), 75)
        assertThat(charlie.rangeInSectorHotel(), equalTo(30 .. 120))

        val delta = Nanobot(Point(75, 74, -10), 50)
        assertThat(delta.rangeInSectorHotel(), equalTo(109 .. 189))

        val echo = Nanobot(Point(-20, -30, 105), 75)
        assertThat(echo.rangeInSectorHotel(), equalTo(80 .. 130))

        val foxtrot = Nanobot(Point(-20, 30, 105), 75)
        assertThat(foxtrot.rangeInSectorHotel(), equalTo(80 .. 190))

        val empty = Nanobot(Point(20, -30, 105), 25)
        assertThat(empty.rangeInSectorHotel(), equalTo(EMPTY))

        val gamma = Nanobot(Point(20, -30, 105), 65)
        assertThat(gamma.rangeInSectorHotel(), equalTo(90 .. 160))

        val hotel = Nanobot(Point(20, 30, 105), 65)
        assertThat(hotel.rangeInSectorHotel(), equalTo(90 .. 220))
    }

    @Test
    fun itShouldZoomOut() {
        val bot = Nanobot(Point(4500, - 3800, 2100), 7884)
        val zoomedBot = bot.zoomOut(1_000)

        assertThat(zoomedBot.point.x, equalTo(4))
        assertThat(zoomedBot.point.y, equalTo(-3))
        assertThat(zoomedBot.point.z, equalTo(2))
        assertThat(zoomedBot.range, equalTo(7))

    }

    @Test
    fun staticticalInfo() {
        val bots: List<Nanobot> = NANOBOTS_FORMATION.trimIndent().lines().map { parseNanobot(it) }

        val xCoords = bots.map { it.point.x }.sorted()
        val yCoords = bots.map { it.point.y }.sorted()
        val zCoords = bots.map { it.point.z }.sorted()

        val xStats = Stats.of(xCoords.drop(50).dropLast(50))
        val yStats = Stats.of(yCoords.drop(50).dropLast(50))
        val zStats = Stats.of(zCoords.drop(50).dropLast(50))

        printStats(xStats, "X")
        printStats(yStats, "Y")
        printStats(zStats, "Z")

    }

}


fun printStats(stats: Stats, label: String) {
    println("${stats.count()}")
    println("${label}:  ${stats.min().roundToInt()} .. ${stats.max().roundToInt()}")
    println("  Mean: ${stats.mean().roundToInt()} with ${stats.populationStandardDeviation().roundToInt()}")
    println()
    println()
}

const val PART_ONE_EXAMPLE_BOTS = """pos=<0,0,0>, r=4
pos=<1,0,0>, r=1
pos=<4,0,0>, r=3
pos=<0,2,0>, r=1
pos=<0,5,0>, r=3
pos=<0,0,3>, r=1
pos=<1,1,1>, r=1
pos=<1,1,2>, r=1
pos=<1,3,1>, r=1"""


const val PART_TWO_EXAMPLE_FORMATION = """pos=<10,12,12>, r=2
pos=<12,14,12>, r=2
pos=<16,12,12>, r=4
pos=<14,14,14>, r=6
pos=<50,50,50>, r=200
pos=<10,10,10>, r=5"""