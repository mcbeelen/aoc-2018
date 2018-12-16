package day15_beverage_bandits

import com.natpryce.hamkrest.allElements
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.isIn
import com.natpryce.hamkrest.lessThan
import day15_beverage_bandits.CreatureType.ELF
import day15_beverage_bandits.CreatureType.GOBLIN
import org.junit.Ignore
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

        val playingField: MutableSet<ScreenCoordinate> = HashSet()
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
    fun itShouldBeAbleToIdentifySquaresInRange() {

        val sampleBattlefield = parseIntoBattleField(ENTIRE_SAMPLE_COMBAT)

        val squaresInRangeOfEnemies = sampleBattlefield.findSquaresInRangeOfEnemiesOfActiveCompatant()

        assertThat(squaresInRangeOfEnemies.size, equalTo(3))

        squaresInRangeOfEnemies.let {
            assertThat(it[0], isAt(4, 1))
            assertThat(it[1], isAt(3, 2))
            assertThat(it[2], isAt(5, 5))
        }


        sampleBattlefield.findSquareToMoveToAlongShortestPathToClosestTarget().let {
            assertThat(it!!, isAt(3, 1))
        }

    }


    @Test
    fun validateEntireSampleCombat() {

        val sampleBattlefield = parseIntoBattleField(ENTIRE_SAMPLE_COMBAT)

        val battlefieldAtEndOfTheCombat = battleItOut(sampleBattlefield)

        assertThat(battlefieldAtEndOfTheCombat.numberOfCompletedRoundsOfBattle, equalTo(47))
        assertThat(battlefieldAtEndOfTheCombat.sumOfHitPointsOfRemainingUnits(), equalTo(590))

    }

    @Test
    fun firstAdditionalExample() {
        val battlefield = parseIntoBattleField(FIRST_ADDITIONAL_EXAMPLE)
        val battlefieldAtEndOfTheCombat = battleItOut(battlefield)
        assertThat(battlefieldAtEndOfTheCombat.numberOfCompletedRoundsOfBattle, equalTo(37))
        assertThat(battlefieldAtEndOfTheCombat.sumOfHitPointsOfRemainingUnits(), equalTo(982))
    }

    @Test
    fun itShouldFindEnemyWithFewestPointsAndInReadingOrder() {

        val battlefield = parseIntoBattleField(FIRST_ADDITIONAL_EXAMPLE)

        battlefield.findAdjacentEnemyWithFewestHitPoints().let {
            assertThat(it.type, equalTo(ELF))
            assertThat(it.hitPoints, equalTo(200))
            assertThat(it.position, isAt(1, 2))
        }

        val battlefieldWithElfAtOneDotTwoActive = battlefield
                .withNextCombatantActive()
                .withNextCombatantActive()

        battlefieldWithElfAtOneDotTwoActive.findAdjacentEnemyWithFewestHitPoints().let {
            assertThat(it.type, equalTo(GOBLIN))
            assertThat(it.hitPoints, equalTo(200))
            assertThat(it.position, isAt(1, 1))
        }
    }


    @Test
    fun secondAdditionalExample() {

        val battlefield = parseIntoBattleField(SECOND_ADDITIONAL_EXAMPLE)
        val battlefieldAtEndOfTheCombat = battleItOut(battlefield)
        assertThat(battlefieldAtEndOfTheCombat.numberOfCompletedRoundsOfBattle, equalTo(46))
        assertThat(battlefieldAtEndOfTheCombat.sumOfHitPointsOfRemainingUnits(), equalTo(859))
    }

    @Test
    fun thirdAdditionalExample() {

        val battlefield = parseIntoBattleField(THIRD_ADDITIONAL_EXAMPLE)
        val battlefieldAtEndOfTheCombat = battleItOut(battlefield)
        assertThat(battlefieldAtEndOfTheCombat.numberOfCompletedRoundsOfBattle, equalTo(35))
        assertThat(battlefieldAtEndOfTheCombat.sumOfHitPointsOfRemainingUnits(), equalTo(793))
    }


    @Test
    fun fourthAdditionalExample() {

        val battlefield = parseIntoBattleField(FOURTH_ADDITIONAL_EXAMPLE)
        val battlefieldAtEndOfTheCombat = battleItOut(battlefield)
        assertThat(battlefieldAtEndOfTheCombat.numberOfCompletedRoundsOfBattle, equalTo(54))
        assertThat(battlefieldAtEndOfTheCombat.sumOfHitPointsOfRemainingUnits(), equalTo(536))
    }


    @Test
    fun fiveAdditionalExample() {

        val battlefield = parseIntoBattleField(FIVE_ADDITIONAL_EXAMPLE)
        val battlefieldAtEndOfTheCombat = battleItOut(battlefield)
        assertThat(battlefieldAtEndOfTheCombat.numberOfCompletedRoundsOfBattle, equalTo(20))
        assertThat(battlefieldAtEndOfTheCombat.sumOfHitPointsOfRemainingUnits(), equalTo(937))
    }


    @Test
    @Ignore
    fun actualPuzzle() {

        val battlefield = parseIntoBattleField(ACTUAL_BATTLEFIELD)
        val battlefieldAtEndOfTheCombat = battleItOut(battlefield)
        val numberOfCompletedRoundsOfBattle = battlefieldAtEndOfTheCombat.numberOfCompletedRoundsOfBattle
        val hitPointsOfRemainingUnits = battlefieldAtEndOfTheCombat.sumOfHitPointsOfRemainingUnits()

        val outcome = numberOfCompletedRoundsOfBattle * hitPointsOfRemainingUnits
        println("Outcome of ${numberOfCompletedRoundsOfBattle} * ${hitPointsOfRemainingUnits} == $outcome")


        assertThat(outcome, lessThan(198516))
        assertThat(outcome, !equalTo(196632))

        assertThat(numberOfCompletedRoundsOfBattle, equalTo(46))
        assertThat(hitPointsOfRemainingUnits, equalTo(859))
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


const val FIRST_ADDITIONAL_EXAMPLE = """
#######
#G..#E#
#E#E.E#
#G.##.#
#...#E#
#...E.#
#######"""


const val SECOND_ADDITIONAL_EXAMPLE = """
#######
#E..EG#
#.#G.E#
#E.##E#
#G..#.#
#..E#.#
#######"""

const val THIRD_ADDITIONAL_EXAMPLE = """
#######
#E.G#.#
#.#G..#
#G.#.G#
#G..#.#
#...E.#
#######
"""

const val FOURTH_ADDITIONAL_EXAMPLE = """
#######
#.E...#
#.#..G#
#.###.#
#E#G#G#
#...#G#
#######"""


const val FIVE_ADDITIONAL_EXAMPLE = """
#########
#G......#
#.E.#...#
#..##..G#
#...##..#
#...#...#
#.G...G.#
#.....G.#
#########
"""
