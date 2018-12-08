package day08_memory_manuever

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class LicenceFileMetadataCheckerTest {

    @Test
    fun itShouldSolveTheExampleCorrectly() {

        assertThat(sumMetadata(MEMORY_MENUEVER_EXAMPLE), equalTo(138))

    }


    @Test
    fun itShouldBeAbleToParseInputToNodes() {

        val NODE_B_INPUT = "0 3 10 11 12"
        val nodeB: Node = parseToNode(NODE_B_INPUT)

        assertThat(nodeB.numberOfChildNodes, equalTo(0))
        assertThat(nodeB.numberOfMetadataEntries, equalTo(3))
        assertThat(nodeB.childNodes.size, equalTo(0))
        assertThat(nodeB.metadataEntries.size, equalTo(3))

        assertThat(nodeB.sumOfMetadataEntries(), equalTo(33))
    }


    @Test
    fun itShouldBeAbleToParseInputWithChildNode() {
        val input = "1 1 0 1 99 2"
        val nodeC:  Node = parseToNode(input)

        assertThat(nodeC.numberOfChildNodes, equalTo(1))
        assertThat(nodeC.numberOfMetadataEntries, equalTo(1))
        assertThat(nodeC.childNodes.size, equalTo(1))
        assertThat(nodeC.metadataEntries.size, equalTo(1))

        assertThat(nodeC.sumOfMetadataEntries(), equalTo(101))


    }

    private fun parseToNode(nodeInput: String): Node {
        val input = nodeInput.split(" ").map { it.toInt() }
        return parseToNode(input)

    }

    private fun parseToNode(input: List<Int>): Node {

        return when( input.first()) {
            0 -> parseToLeafNode(input)
            else -> {
                Node(1,
                        numberOfMetadataEntries = input[1],
                        childNodes = listOf(parseToLeafNode(input.subList(2, input.size - input[1]))),
                        metadataEntries = takeMetadataEntries(input))
            }

        }


    }

    private fun parseToLeafNode(input: List<Int>) =
        Node(numberOfMetadataEntries = input[1], metadataEntries = takeMetadataEntries(input))


    private fun takeMetadataEntries(input: List<Int>) =
            input.takeLast(input[1]).map { MetadataEntry(it) }

    private fun sumMetadata(licenseFile: String): Int {

        return 0

    }
}



class Node(
        val numberOfChildNodes: Int = 0,
        val numberOfMetadataEntries: Int = 0,
        val childNodes: List<Node> = emptyList(),
        val metadataEntries: List<MetadataEntry> = emptyList()) {

    fun sumOfMetadataEntries() : Int =
            childNodes.sumBy { it.sumOfMetadataEntries() } + metadataEntries.sumBy { it.value }


}

inline class MetadataEntry(val value: Int)