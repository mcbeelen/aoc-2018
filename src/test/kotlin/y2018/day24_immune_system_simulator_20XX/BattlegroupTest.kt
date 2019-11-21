package y2018.day24_immune_system_simulator_20XX

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import y2018.day24_immune_system_simulator_20XX.AttackType.*
import org.junit.Test

class BattlegroupTest {

    @Test
    fun parseFirstOfImmuneGroup() {

        val immuneSystemGroups = parseBattlegroups(Fighter.IMMUNE_SYSTEM, IMMUNE_SYSTEM_DEFINITION)
        
        assertThat(immuneSystemGroups.size, equalTo(10))
        immuneSystemGroups[0].let {

            //(immune to cold; weak to bludgeoning)
            assertThat(it.attackType, equalTo(SLASHING))
            assertThat(it.immunities, equalTo(setOf(COLD)))
            assertThat(it.weaknesses, equalTo(setOf(BLUDGEONING)))
        }

        immuneSystemGroups[9].let {
            assertThat(it.weaknesses, equalTo(setOf(FIRE, RADIATION)))
        }
    }

}