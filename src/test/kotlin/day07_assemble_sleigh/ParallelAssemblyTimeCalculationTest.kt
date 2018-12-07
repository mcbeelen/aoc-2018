package day07_assemble_sleigh

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class ParallelAssemblyTimeCalculationTest {


    @Test
    fun itShouldFigureOutToTheProperOrderForTheExample() {
        assertThat(minimumTimeNeededToAssemble(DAY07_EXAMPLE_INPUT.trimIndent(), 2), equalTo(15))
    }

    private fun minimumTimeNeededToAssemble(assemblyInstructions: String, numberOfWorkers: Int): Int {
        return 0

    }


}