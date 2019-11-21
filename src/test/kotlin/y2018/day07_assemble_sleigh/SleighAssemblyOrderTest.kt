package y2018.day07_assemble_sleigh

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test


class SleighAssemblyOrderTest {


    @Test
    fun actualPartOne() {
        assertThat(determineOrderOfAssembly(SLEIGH_ASSEMBLY_INSTRUCTIONS.trimIndent()), equalTo("BKCJMSDVGHQRXFYZOAULPIEWTN"))
    }

    @Test
    fun itShouldFigureOutToTheProperOrderForTheExample() {
        assertThat(determineOrderOfAssembly(DAY07_EXAMPLE_INPUT.trimIndent()), equalTo("CABDFE"))
    }


    @Test
    fun itShouldParseToInput() {
        val instruction: AssemblyInstruction = parseToAssembleOrder("Step C must be finished before step A can begin.")
        assertThat(instruction.before, equalTo(Step('C')))
        assertThat(instruction.after, equalTo(Step('A')))
    }

}

const val DAY07_EXAMPLE_INPUT = """
Step C must be finished before step A can begin.
Step C must be finished before step F can begin.
Step A must be finished before step B can begin.
Step A must be finished before step D can begin.
Step B must be finished before step E can begin.
Step D must be finished before step E can begin.
Step F must be finished before step E can begin.
"""