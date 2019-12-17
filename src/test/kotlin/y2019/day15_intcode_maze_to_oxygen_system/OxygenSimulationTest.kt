package y2019.day15_intcode_maze_to_oxygen_system

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.greaterThan
import com.natpryce.hamkrest.lessThan
import org.junit.Test

class OxygenSimulationTest {

    @Test
    fun verifyAocExample() {
        assertThat(OxygenSimulation(EXAMPLE_FROM_AOC).repair(), equalTo(4))
    }


    @Test
    fun verifyPartTwo() {
        assertThat(OxygenSimulation(MAP_OF_AREA_OXYGEN_AREA).repair(), equalTo(482))
    }

}


private const val EXAMPLE_FROM_AOC = """
 ##   
#..## 
#.#..#
#.O.# 
 ###  
"""