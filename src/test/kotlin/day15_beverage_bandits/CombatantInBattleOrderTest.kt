package day15_beverage_bandits

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import day15_beverage_bandits.CreatureType.ELF
import day15_beverage_bandits.CreatureType.GOBLIN
import org.junit.Test
import util.grid.isAt

class CombatantInBattleOrderTest {

    @Test
    fun compare() {

        val sampleBattlefield = parseIntoBattleField(ENTIRE_SAMPLE_COMBAT)
        val combatants: List<Combatant> = sampleBattlefield.combatants.sortedWith(combatantInBattleOrder)

        combatants.first().let {
            assertThat(it.type, equalTo(GOBLIN))
            assertThat(it.position, isAt(2, 1))
        }

        combatants.last().let {
            assertThat(it.type, equalTo(ELF))
            assertThat(it.position, isAt(5, 4))
        }


    }
}