package y2018.day08_memory_manuever

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class LicenceFileMetadataCheckerTest {


    @Test
    fun itShouldSolveTheActualProblem() {
        val problem = parseToNode(MEMORY_MANUEVER_INPUT)
        assertThat(problem.sumOfMetadataEntries(), equalTo(41521))
        assertThat(problem.valueForSecondCheck, equalTo(19990))
    }

    @Test
    fun itShouldSolveTheExampleCorrectly() {

        val example = parseToNode(MEMORY_MENUEVER_EXAMPLE)
        assertThat(example.sumOfMetadataEntries(), equalTo(138))
        assertThat(example.valueForSecondCheck, equalTo(66))

    }


    @Test
    fun itShouldParseSingleNodeWithoutChildNodes() {

        val nodeBInput = "0 3 10 11 12"
        val nodeB: Node = parseToNode(nodeBInput)

        assertThat(nodeB.numberOfChildNodes, equalTo(0))
        assertThat(nodeB.numberOfMetadataEntries, equalTo(3))
        assertThat(nodeB.childNodes.size, equalTo(0))
        assertThat(nodeB.metadataEntries.size, equalTo(3))

        assertThat(nodeB.sumOfMetadataEntries(), equalTo(33))

        assertThat(nodeB.valueForSecondCheck, equalTo(33))

    }


    @Test
    fun itShouldBeAbleToParseInputWithChildNode() {
        val nodeCInput = "1 1 0 1 99 2"
        val nodeC: Node = parseToNode(nodeCInput)

        assertThat(nodeC.numberOfChildNodes, equalTo(1))
        assertThat(nodeC.numberOfMetadataEntries, equalTo(1))
        assertThat(nodeC.childNodes.size, equalTo(1))
        assertThat(nodeC.metadataEntries.size, equalTo(1))

        assertThat(nodeC.sumOfMetadataEntries(), equalTo(101))

        assertThat(nodeC.valueForSecondCheck, equalTo(0))


    }

    private fun parseToNode(nodeInput: String): Node {
        val input = nodeInput.split(" ").map { it.toInt() }.asIterable().iterator()
        return parseToNode(input)

    }

    private fun parseToNode(input: Iterator<Int>): Node {

        val parser = NodeInputParser()

        while (input.hasNext()) {
            parser.parse(input.next())
        }

        return parser.completedNode

    }

    private fun sumMetadata(licenseFile: String): Int {

        return 0

    }
}

