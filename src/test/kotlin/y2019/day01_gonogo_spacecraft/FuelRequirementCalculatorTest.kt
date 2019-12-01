package y2019.day01_gonogo_spacecraft

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class FuelRequirementCalculatorTest {

    @Test
    fun `mass 12 requires 2`() {

        assertThat(fuelRequired(12), equalTo(2))
    }

    private fun fuelRequired(mass: Int): Int {
        return 2

    }
}

