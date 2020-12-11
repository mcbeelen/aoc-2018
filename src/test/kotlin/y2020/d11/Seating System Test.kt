package y2020.d11

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class `Seating System Test` {

    @Test
    fun examplePartOne() {
        assertThat(evolveUntilStableAndCountOccupiedSeats(testInput), equalTo(37))
    }

    @Test
    fun partOne() {
        assertThat(evolveUntilStableAndCountOccupiedSeats(y2020d11waitingAreaInput), equalTo(2324))
    }

    @Test
    fun examplePartTwo() {
        assertThat(countOccupiedSeatsInStableWaitingAreaPartTwo(testInput), equalTo(26))
    }

    @Test
    fun partTwo() {
        assertThat(countOccupiedSeatsInStableWaitingAreaPartTwo(y2020d11waitingAreaInput), equalTo(2068))
    }
}


const val testInput = """L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL
"""
