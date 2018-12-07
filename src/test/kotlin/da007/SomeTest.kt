package da007

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test


class SomeTest {


    @Test
    fun itShouldFindTheSafestAreaInTheExampleIs17() {
        assertThat(determineOrderOfAssembly(DAY07_EXAMPLE_INPUT.trimIndent()), equalTo("CABDFE"))
    }

    private fun determineOrderOfAssembly(input: String): String? {
        return ""
    }

    @Test
    fun itShouldParseToInput() {
        val instruction: AssemblyInstruction = parseToAssembleOrder("Step C must be finished before step A can begin.")
        assertThat(instruction.before, equalTo(Step('C')))
        assertThat(instruction.after, equalTo(Step('A')))
    }

    private fun parseToAssembleOrder(input: String) = AssemblyInstruction(Step(input[5]), Step(input[36]) )

}


data class AssemblyInstruction(val before: Step, val after: Step)

inline class Step(val name: Char)


const val DAY07_EXAMPLE_INPUT = """
Step C must be finished before step A can begin.
Step C must be finished before step F can begin.
Step A must be finished before step B can begin.
Step A must be finished before step D can begin.
Step B must be finished before step E can begin.
Step D must be finished before step E can begin.
Step F must be finished before step E can begin.
"""