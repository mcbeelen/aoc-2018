package y2018.day20_regular_map

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import org.junit.Test


class RouteTest {

    @Test
    fun itShouldParseTheFirstExample() {

        val route = parseRoute("^WNE\$")
        val directions = route.getDirections()
        assertThat(directions, hasSize(equalTo(3)))

        val routeWithBranch = parseRoute("^N(E|W)N\$")

        val directionsWithBranch = routeWithBranch.getDirections()
        assertThat(directionsWithBranch, hasSize(equalTo(3)))
    }


    @Test
    fun `itShouldParseTheSectionExample() {`() {
        val route = parseRoute("^ENWWW(NEEE|SSE(EE|N))\$")
        val directions = route.getDirections()
        assertThat(directions, hasSize(equalTo(6)))
        assertThat((directions.get(5) as Branch).getRoutes(), hasSize(equalTo(2)))

    }
}