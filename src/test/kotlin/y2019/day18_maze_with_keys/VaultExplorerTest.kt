package y2019.day18_maze_with_keys

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.grid.at

class UndergroundVaultTest {
    @Test
    fun itShouldFindTwoKeys() {
        val vault = UndergroundVault(SMALLEST_EXAMPLE)

        assertThat(vault.coordinatesOfKeys(), equalTo(setOf(at(1, 1), at(7, 1))))
    }
}


class VaultExplorerTest {

    @Test
    fun explore() {

        val explorer = VaultExplorer(SMALLEST_EXAMPLE)

        assertThat(explorer.explore(), equalTo(8))
    }
}


private const val SMALLEST_EXAMPLE = """
    #########
    #b.A.@.a#
    #########
"""