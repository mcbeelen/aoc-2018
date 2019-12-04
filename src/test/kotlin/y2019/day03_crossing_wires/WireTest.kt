package y2019.day03_crossing_wires

import com.natpryce.hamkrest.anyElement
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.grid.ScreenCoordinate

class WireTest {
    @Test
    fun name() {
        val wire = wireOf("R8")
        assertThat(wire.currentLocation, equalTo(ScreenCoordinate(8, 0)))

        wire.tracePath("U5")
        assertThat(wire.currentLocation, equalTo(ScreenCoordinate(8, -5)))

        wire.tracePath("L5,D3")
        assertThat(wire.currentLocation, equalTo(ScreenCoordinate(3, -2)))
    }

    @Test
    fun `track entire path`() {
        findIntersectionOnPath("R8,U5,L5,D3", 6, -5)
        findIntersectionOnPath("U7,R6,D4,L4", 6, -5)
    }


    @Test
    fun `find intersections`() {
        val first = wireOf("U7,R6,D4,L4")
        val second = wireOf("R8,U5,L5,D3")

        val intersections : Set<ScreenCoordinate> = first.crossPathsWith(second)

        assertThat(intersections.size, equalTo(2))
        assertThat(intersections, anyElement(equalTo(ScreenCoordinate(6, -5))))
        assertThat(intersections, anyElement(equalTo(ScreenCoordinate(3, -3))))

        assertThat(findDistanceToFirstIntersection(first, second), equalTo(30))
    }

    private fun findIntersectionOnPath(path: String, left: Int, top: Int) {
        val wire = wireOf(path)

        assertThat(wire.path, anyElement(equalTo(ScreenCoordinate(left, top))))
    }

    @Test
    fun examples() {
        val exampleOne = """R75,D30,R83,U83,L12,D49,R71,U7,L72
U62,R66,U55,R34,D71,R55,D58,R83""".lines()


        assertThat(findDistanceToNearestIntersection(exampleOne), equalTo(159))
        assertThat(findDistanceToFirstIntersection(exampleOne), equalTo(610))
    }

    @Test
    fun `example two`() {
        val input = """R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51
U98,R91,D20,R16,D67,R40,U7,R15,U6,R7""".lines()

        assertThat(findDistanceToNearestIntersection(input), equalTo(135))
        assertThat(findDistanceToFirstIntersection(input), equalTo(410))
    }

}
