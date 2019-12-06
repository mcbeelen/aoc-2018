package y2019.day06

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test



class UniversalOrbitMapTest {
    @Test
    fun `d orbits 3`() {
        val map = UniversalOrbitMap(SIMPLE_MAP)

        assertThat(map.numberOfOrbits("D"), equalTo(3))
        assertThat(map.numberOfOrbits("L"), equalTo(7))
    }

    @Test
    fun sumOfAllOrbits() {
        val map = UniversalOrbitMap(SIMPLE_MAP)

        assertThat(map.sumOfAllNumberOfOrbits(), equalTo(42))
    }

    @Test
    fun minimumNumberOfRequiredOrbitTransfers() {
        val map = UniversalOrbitMap(MAP_WITH_SAN_AND_YOU)

        assertThat(map.minimumNumberOfRequiredOrbitTransfersToReachSanta(), equalTo(4))
    }


    @Test
    fun `find path to COM`() {
        val map = UniversalOrbitMap(MAP_WITH_SAN_AND_YOU)

        assertThat(map.pathToCom("YOU"), equalTo(listOf("COM", "B", "C", "D", "E", "J", "K")))
        assertThat(map.pathToCom("SAN"), equalTo(listOf("COM", "B", "C", "D", "I")))
    }
}


private const val SIMPLE_MAP = """COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L"""


private const val MAP_WITH_SAN_AND_YOU = """COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L
K)YOU
I)SAN"""

