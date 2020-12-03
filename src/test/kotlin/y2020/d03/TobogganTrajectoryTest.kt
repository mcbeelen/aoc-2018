package y2020.d03

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class TobogganTrajectoryTest {

    @Test
    fun itShouldHitSevenTrees() {

        assertThat(7, equalTo(numberOfTreesToEncounterOnSlope(y2020d03testInput)))


    }

    private fun numberOfTreesToEncounterOnSlope(terrain: String): Int {
        return 0;
    }
}
