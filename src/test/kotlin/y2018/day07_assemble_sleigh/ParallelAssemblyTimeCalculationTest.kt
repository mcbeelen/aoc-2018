package y2018.day07_assemble_sleigh

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.greaterThan
import org.junit.Test

class ParallelAssemblyTimeCalculationTest {


    @Test
    fun actualAnswerShouldBeMoreThen208() {
        assertThat(minimumTimeNeededToAssemble(SLEIGH_ASSEMBLY_INSTRUCTIONS.trimIndent(), 5), greaterThan(208))
    }

    @Test
    fun itShouldFigureOutToTheProperOrderForTheExample() {
        assertThat(minimumTimeNeededToAssemble(DAY07_EXAMPLE_INPUT.trimIndent(), 2), equalTo(15))
    }



}