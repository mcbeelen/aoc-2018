package y2019.day12_moons_of_jupiter

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.lessThan
import org.junit.Assert.assertTrue
import org.junit.Test
import util.math.lcm
import util.space.isAt
import util.space.isCurrently

class JupiterMoonOrbitSimulatorTest {

    @Test
    fun shouldParseScan() {
        val scan = "<x=-14, y=5, z=4>"
        val moon = calculatePosition(scan)

        assertThat(moon.currentPosition, isAt(-14, 5, 4))
    }

    @Test
    fun shouldApplyGravity() {
        val orbit = readOrbit(TEST_SCAN_OF_MOONS)
        val moonsWithVelocity = applyGravity(orbit.moons)
        assertThat(moonsWithVelocity[0].currentVelocity.x, equalTo(3))
        assertThat(moonsWithVelocity[0].currentVelocity.y, equalTo(-1))
        assertThat(moonsWithVelocity[0].currentVelocity.z, equalTo(-1))
    }

    @Test
    fun shouldUpdatePosition() {
        val orbit = readOrbit(TEST_SCAN_OF_MOONS)
        val moonsWithVelocity = applyGravity(orbit.moons)

        val moonsOnNewPositions = applyVelocity(moonsWithVelocity)

        assertThat(moonsOnNewPositions[0].currentPosition.x, equalTo(2))
        assertThat(moonsOnNewPositions[0].currentPosition.y, equalTo(-1))
        assertThat(moonsOnNewPositions[0].currentPosition.z, equalTo(1))
    }


    @Test
    fun simulateTenStepsOnFirstExample() {
        var orbit = readOrbit(TEST_SCAN_OF_MOONS);
        for (i in 0 .. 9) {
            orbit = orbit.processStep()
        }

        val moon = orbit.moons[0]
        assertThat(moon.currentPosition, isAt(2, 1, -3))
        assertThat(moon.currentVelocity, isCurrently(-3, -2, 1))

    }

    @Test
    fun firstExample() {
        val totalAmountOfEnergy = totalAmountOfEnergyAfterSteps(TEST_SCAN_OF_MOONS, 10)
        assertThat(totalAmountOfEnergy, equalTo(179))
    }

    @Test
    fun secondExample() {
        val totalAmountOfEnergy = totalAmountOfEnergyAfterSteps(SECOND_EXAMPLE, 100)
        assertThat(totalAmountOfEnergy, equalTo(1940))
    }

    @Test
    fun verifyPartOne() {
        assertThat(totalAmountOfEnergyAfterSteps(JUPITER_MOONS, 1000), equalTo(10664))
    }

    @Test
    fun exampleForPartTwo() {
        //assertThat(numberOfStepToReachIdenticalState(SECOND_EXAMPLE), equalTo(4_686_774_924))
        assertThat(numberOfStepToReachIdenticalState(JUPITER_MOONS), equalTo(303459551979256))
    }

    @Test
    fun name() {
        println(lcm(56344L, 186028L,  231614)) // 303459551979256
    }

    @Test
    fun verifyProperStateComparion() {
        val orbit = readOrbit(TEST_SCAN_OF_MOONS);
        val expected = listOf(Pair(-1, 0), Pair(2, 0), Pair(4, 0), Pair(3, 0))
        assertTrue(expected == orbit.getStateOfX())
        assertThat(orbit.getStateOfX(), equalTo(expected))
    }


}


private const val TEST_SCAN_OF_MOONS = """
<x=-1, y=0, z=2>
<x=2, y=-10, z=-7>
<x=4, y=-8, z=8>
<x=3, y=5, z=-1>"""


private const val SECOND_EXAMPLE = """
<x=-8, y=-10, z=0>
<x=5, y=5, z=10>
<x=2, y=-7, z=3>
<x=9, y=-8, z=-3>
"""