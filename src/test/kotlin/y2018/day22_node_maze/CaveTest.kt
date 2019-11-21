package y2018.day22_node_maze

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.lessThan
import org.junit.Test
import util.grid.ScreenCoordinate

class CaveTest {

    @Test
    fun `validateDetermineRiskLevel()`() {
        val cave = Cave(510, ScreenCoordinate(10, 10))
        cave.exploreUntilTarget()

        assertThat(cave.calculateRiskLevel(), equalTo(114))

    }

    @Test
    fun validateCalculateGeolocicIndex() {
        val cave = Cave(10647, ScreenCoordinate(7, 770))

        assertThat(cave.calculateGeolocicIndex(0, 0), equalTo(0))

        assertThat(cave.calculateGeolocicIndex(7, 770), equalTo(0))

        assertThat(cave.calculateGeolocicIndex(1, 0), equalTo(16807))
        assertThat(cave.calculateGeolocicIndex(2, 0), equalTo(33614))

        assertThat(cave.calculateGeolocicIndex(0, 1), equalTo(48271))
        assertThat(cave.calculateGeolocicIndex(0, 3), equalTo(144813))
    }

    @Test
    fun itShouldSolveTheExamplePartTwo() {
        val target = ScreenCoordinate(10, 10)
        val cave = Cave(510, target)
        cave.exploreUntilTarget()


        val caveExploration = CaveExploration(cave)

        val origin = ReachedRegion(cave.caveMap.getValue(ScreenCoordinate(0, 0)), Equipment.TORCH)
        val destination = ReachedRegion(cave.caveMap.getValue(target), Equipment.TORCH)

        val path = caveExploration.findShortestPath(origin, destination)

        val timeInCave = path!!.calculateDistance()
        println("Getting to the man's friend takes at least $timeInCave minutes")

        assertThat(timeInCave, equalTo(45))

    }

    @Test
    fun itShouldYieldTheSameAnswerAsOnReddit() {
        // https://www.reddit.com/r/adventofcode/comments/a8jk9c/2018_day_22_part_2_answer_drops_lower_than/

        val target = ScreenCoordinate(7, 782)
        val redditCave = Cave(11820, target)
        redditCave.exploreUntil(ScreenCoordinate(1000, 1500))

        val caveExploration = CaveExploration(redditCave)

        val origin = ReachedRegion(redditCave.caveMap.getValue(ScreenCoordinate(0, 0)), Equipment.TORCH)
        val destination = ReachedRegion(redditCave.caveMap.getValue(target), Equipment.TORCH)

        assertThat(redditCave.calculateRiskLevel(), equalTo(6318))

        val path = caveExploration.findShortestPath(origin, destination)

        if (path != null) {
            plotCave(redditCave, path)

            path.vertices.forEach {
                    println(it.region.location)
            }

            println("Getting to the man's friend takes at least ${path.calculateDistance()} minutes")

            assertThat(path.calculateDistance(), equalTo(1075))

        }

    }



    @Test
    fun verifyActualCaveExploration() {

        val target = ScreenCoordinate(7, 770)
        val actualCave = Cave(10647, target)
        actualCave.exploreUntilTarget()

        val caveExploration = CaveExploration(actualCave)

        val origin = ReachedRegion(actualCave.caveMap.getValue(ScreenCoordinate(0, 0)), Equipment.TORCH)
        val destination = ReachedRegion(actualCave.caveMap.getValue(target), Equipment.TORCH)

        val path = caveExploration.findShortestPath(origin, destination)

        println("Getting to the man's friend takes at least ${path!!.calculateDistance()} minutes")

        assertThat(path.calculateDistance(), lessThan(1053))


    }
}