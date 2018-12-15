package day15_beverage_bandits

import com.natpryce.hamkrest.allElements
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isIn
import org.junit.Test
import util.grid.ScreenCoordinate

class BattlefieldTest {

    val FIRST_EXAMPLE_BATTLEFIELD = """
    #######
    #.G.E.#
    #E.G.E#
    #.G.E.#
    #######"""


    @Test
    fun itShouldParseTheInputIntoTheBattlefield() {

        val sampleBattlefield = parseIntoBattleField(FIRST_EXAMPLE_BATTLEFIELD)

        val playingField : MutableSet<ScreenCoordinate> = HashSet();
        for (x in 1 .. 5) {
            for ( y in 1 .. 3) {
                playingField.add(ScreenCoordinate(x, y))
            }
        }
        assertThat(sampleBattlefield.openSpaces, allElements(isIn(playingField)))
        assertThat(playingField, allElements(isIn(sampleBattlefield.openSpaces)))
    }


    @Test
    fun validateEntireSampleCombat() {


        val sampleBattlefield = parseIntoBattleField(ENTIRE_SAMPLE_COMBAT)

        val battlefieldAtEndOfTheCombat = sampleBattlefield.battleItOut()

        assertThat(battlefieldAtEndOfTheCombat.numberOfCompletedRoundsOfBattle, equalTo(47))
        assertThat(battlefieldAtEndOfTheCombat.sumOfHitPointsOfRemainingUnits(), equalTo(590))

    }

}




const val ENTIRE_SAMPLE_COMBAT = """
#######
#.G...#
#...EG#
#.#.#G#
#..G#E#
#.....#
####### """