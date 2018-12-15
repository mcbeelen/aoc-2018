package day15_beverage_bandits

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class BattlefieldTest {


    @Test
    fun itShouldParseTheInputIntoTheBattlefield() {

        FIRST_EXAMPLE_BATTLEFIELD.trimIndent().lines()

    }


    @Test
    fun validateEntireSampleCombat() {


        val sampleBattlefield = parseIntoBattleField(ENTIRE_SAMPLE_COMBAT)

        val battlefieldAtEndOfTheCombat = sampleBattlefield.battleItOut()

        assertThat(battlefieldAtEndOfTheCombat.numberOfCompletedRoundsOfBattle, equalTo(47))
        assertThat(battlefieldAtEndOfTheCombat.sumOfHitPointsOfRemainingUnits(), equalTo(590))

    }

}


const val FIRST_EXAMPLE_BATTLEFIELD = """
#######
#.G.E.#
#E.G.E#
#.G.E.#
#######"""



const val ENTIRE_SAMPLE_COMBAT = """
#######
#.G...#
#...EG#
#.#.#G#
#..G#E#
#.....#
####### """