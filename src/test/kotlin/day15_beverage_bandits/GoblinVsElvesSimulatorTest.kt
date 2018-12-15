package day15_beverage_bandits

import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.assertThat
import day15_beverage_bandits.CreatureType.ELF
import day15_beverage_bandits.CreatureType.GOBLIN
import org.junit.Assert.assertTrue
import org.junit.Test
import util.grid.ScreenCoordinate
import util.grid.isAt

class GoblinVsElvesSimulatorTest {


    @Test
    fun itShouldParseTheInputIntoTheBattlefield() {

        val FIRST_EXAMPLE_BATTLEFIELD = """
    #######
    #.G.E.#
    #E.G.E#
    #.G.E.#
    #######"""


        val sampleBattlefield = parseIntoBattleField(FIRST_EXAMPLE_BATTLEFIELD)

        val playingField: MutableSet<ScreenCoordinate> = HashSet();
        for (x in 1..5) {
            for (y in 1..3) {
                playingField.add(ScreenCoordinate(x, y))
            }
        }
        assertThat(sampleBattlefield.openSpaces, allElements(isIn(playingField)))
        assertThat(playingField, allElements(isIn(sampleBattlefield.openSpaces)))

        val positionOfElves = listOf(ScreenCoordinate(4, 1), ScreenCoordinate(1, 2), ScreenCoordinate(5, 2), ScreenCoordinate(4, 3))
        assertThat(sampleBattlefield.findPositionOfAllElves(), allElements(isIn(positionOfElves)))

        val positionOfGoblins = listOf(ScreenCoordinate(2, 1), ScreenCoordinate(3, 2), ScreenCoordinate(2, 3))
        assertThat(sampleBattlefield.findPositionOfAllGoblins(), allElements(isIn(positionOfGoblins)))


    }


    @Test
    fun theCombatantsShouldFightInDisciplineOrder() {

        val sampleBattlefield = parseIntoBattleField(ENTIRE_SAMPLE_COMBAT)
        val combatants: List<Combatant> = sampleBattlefield.getUnitsInOrderForTakingTurns()

        sampleBattlefield.activeCombatant.let {
            assertThat(it.type, equalTo(GOBLIN))
            assertThat(it.position, isAt(2, 1))
        }

        combatants.last().let {
            assertThat(it.type, equalTo(ELF))
            assertThat(it.position, isAt(5, 4))
        }

        val twoElves = sampleBattlefield.identifyPossibleTargetsForActiveCombatant()
        assertThat(twoElves.size, equalTo(2))
        twoElves.let {
            assertThat(it[0].type, equalTo(ELF))
            assertThat(it[0].position, isAt(4, 2))

            assertThat(it[1].type, equalTo(ELF))
            assertThat(it[1].position, isAt(5, 4))
        }

        val target = sampleBattlefield.findTargetForActiveCombatant()
        target.let {
            assertThat(it.enemy.position, isAt(4, 2))
            assertThat(it.path.calculateDistance(), equalTo(2))
            val pathThroughBattleCoordinates = listOf(BattleCoordinate(ScreenCoordinate(3, 1)), BattleCoordinate(ScreenCoordinate(4, 1)))
            assertThat(it.path.vertices, equalTo(pathThroughBattleCoordinates))
        }


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