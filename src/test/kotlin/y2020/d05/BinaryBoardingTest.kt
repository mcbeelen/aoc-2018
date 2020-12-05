package y2020.d05

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class BinaryBoardingTest {


    @Test
    fun examples() {
        assertThat(seatIdOf("FBFBBFFRLR"), equalTo(357))
        assertThat(seatIdOf("BFFFBBFRRR"), equalTo(567))
        assertThat(seatIdOf("FFFBBBFRRR"), equalTo(119))
        assertThat(seatIdOf("BBFFBBFRLL"), equalTo(820))
    }

    @Test
    fun partOne() {
        assertThat(maxSeatIdIn(y2020d05input), equalTo(989))
    }

    @Test
    fun partTwo() {
        assertThat(emptySeatIn(y2020d05input), equalTo(548))
    }

}



