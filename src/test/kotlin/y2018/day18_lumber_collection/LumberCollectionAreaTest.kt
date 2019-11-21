package y2018.day18_lumber_collection

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import y2018.day18_lumber_collection.Acre.*
import org.junit.Test


class LumberCollectionAreaTest {


    @Test
    fun itShouldParseTheInput() {

        val lumberCollectionArea = parseLumberCollectionArea(LUMBER_EXAMPLE_INITIALLY)

        assertThat(lumberCollectionArea.typeAt(0, 0), equalTo(OPEN))
        assertThat(lumberCollectionArea.typeAt(1, 0), equalTo(LUMBERYARD))
        assertThat(lumberCollectionArea.typeAt(7, 0), equalTo(TREES))
        assertThat(lumberCollectionArea.typeAt(8, 0), equalTo(LUMBERYARD))
        assertThat(lumberCollectionArea.typeAt(9, 9), equalTo(OPEN))

    }

    @Test
    fun itShouldConvertPerMinute() {

        val lumberCollectionArea = parseLumberCollectionArea(LUMBER_EXAMPLE_INITIALLY)

        val afterOneMinute = letMinutePass(lumberCollectionArea)

        assertThat(afterOneMinute.typeAt(0, 0), equalTo(OPEN))
        assertThat(afterOneMinute.typeAt(1, 0), equalTo(OPEN))
        assertThat(afterOneMinute.typeAt(7, 0), equalTo(LUMBERYARD))
        assertThat(afterOneMinute.typeAt(8, 0), equalTo(LUMBERYARD))

        assertThat(afterOneMinute.typeAt(0, 1), equalTo(OPEN))
        assertThat(afterOneMinute.typeAt(1, 1), equalTo(OPEN))
        assertThat(afterOneMinute.typeAt(6, 1), equalTo(TREES))
        assertThat(afterOneMinute.typeAt(7, 1), equalTo(LUMBERYARD))
        assertThat(afterOneMinute.typeAt(8, 1), equalTo(LUMBERYARD))
        assertThat(afterOneMinute.typeAt(9, 1), equalTo(LUMBERYARD))



        assertThat(afterOneMinute.typeAt(0, 8), equalTo(TREES))
        assertThat(afterOneMinute.typeAt(1, 8), equalTo(TREES))
        assertThat(afterOneMinute.typeAt(7, 8), equalTo(TREES))
        assertThat(afterOneMinute.typeAt(9, 8), equalTo(TREES))

        assertThat(afterOneMinute.typeAt(4, 9), equalTo(TREES))
        assertThat(afterOneMinute.typeAt(5, 9), equalTo(TREES))
        assertThat(afterOneMinute.typeAt(8, 9), equalTo(TREES))
        assertThat(afterOneMinute.typeAt(9, 9), equalTo(OPEN))

        var eventually = lumberCollectionArea
        for (minute in 1 .. 10) {
            eventually = letMinutePass(eventually)
        }

        assertThat(eventually.countOfTrees() * eventually.countOfLumberyards(), equalTo(1147))

    }


}


const val LUMBER_EXAMPLE_INITIALLY = """
.#.#...|#.
.....#|##|
.|..|...#.
..|#.....#
#.#|||#|#|
...#.||...
.|....|...
||...#|.#|
|.||||..|.
...#.|..|.
"""