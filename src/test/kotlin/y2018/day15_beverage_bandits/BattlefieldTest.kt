package y2018.day15_beverage_bandits

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import util.grid.ScreenCoordinate

class BattlefieldTest {

    @Test
    fun findNeighbours() {

        val sampleBattleSimulator = parseIntoBattleField(ENTIRE_SAMPLE_COMBAT)

        val potentialEnemy = Combatant(CreatureType.ELF, position = ScreenCoordinate(4, 2))
        val battlefield = Battlefield(openSpaces = sampleBattleSimulator.openSpaces,
                combatants = sampleBattleSimulator.combatants)

        val foundNeighbours = battlefield.findNeighbours(BattleCoordinate(2, 1))
        foundNeighbours.let {
            assertThat(it.size, equalTo(3))
            assertThat(it[0].destination, equalTo(BattleCoordinate(3,1)))
            assertThat(it[1].destination, equalTo(BattleCoordinate(2,2)))
            assertThat(it[2].destination, equalTo(BattleCoordinate(1,1)))


        }



    }
}