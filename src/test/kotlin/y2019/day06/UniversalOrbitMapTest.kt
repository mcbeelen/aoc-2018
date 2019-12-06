package y2019.day06

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test



class UniversalOrbitMapTest {
    @Test
    fun `d orbits 3`() {
        val map = UniversalOrbitMap(TEST_INPUT)


        assertThat(map.numberOfOrbits("D"), equalTo(3))
    }
}


private const val TEST_INPUT = """COM)B
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
