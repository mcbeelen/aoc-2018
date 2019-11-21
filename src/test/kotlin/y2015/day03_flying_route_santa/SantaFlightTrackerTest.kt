package y2015.day03_flying_route_santa

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class SantaFlightTrackerTest {

    @Test
    fun countThisOriginalLocation() {
        validateNumberOfHouseOnRoute("", 1)
    }

    @Test
    fun flySmallSquare() {
        validateNumberOfHouseOnRoute("^>v<", 4)

        validateNumberOfHouseOnRoute("^v^v^v^v^v", 2)
    }

    private fun validateNumberOfHouseOnRoute(route: String, numberOfHouseOnRoute: Int) {
        val santaFlightTracker = SantaFlightTracker()
        santaFlightTracker.track(route)
        assertThat(santaFlightTracker.getNumberOfHouseVisited(), equalTo(numberOfHouseOnRoute))
    }
}