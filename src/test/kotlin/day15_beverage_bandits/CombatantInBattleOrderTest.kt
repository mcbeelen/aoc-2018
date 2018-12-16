package day15_beverage_bandits

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import day15_beverage_bandits.CreatureType.ELF
import day15_beverage_bandits.CreatureType.GOBLIN
import org.junit.Test
import util.grid.ScreenCoordinate
import util.grid.isAt

class CombatantInBattleOrderTest {

    @Test
    fun compare() {

        val combatantsSource = listOf(
                Combatant(ELF, ScreenCoordinate(3, 3)),
                Combatant(GOBLIN, ScreenCoordinate(2, 1)),
                Combatant(ELF, ScreenCoordinate(5, 4)),
                Combatant(ELF, ScreenCoordinate(1, 4))


        )
        val combatants: List<Combatant> = combatantsSource.sortedWith(combatantInBattleOrder)

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