package y2018.day08_memory_manuever

import y2018.day08_memory_manuever.ProcessingState.*
import kotlin.Int.Companion.MIN_VALUE

class NodeBuilder(private val parser: NodeInputParser) {

    var state: ProcessingState = NUMBER_OF_CHILD_NODES
    var node = Node()

    fun processInput(value: Int) {

        when (state) {
            NUMBER_OF_CHILD_NODES -> {
                node = node.copy(numberOfChildNodes = value)
                state = NUMBER_OF_META_DATA
            }
            NUMBER_OF_META_DATA -> {
                node = node.copy(numberOfMetadataEntries = value)
                if (node.numberOfChildNodes > 0) {
                    state = CHILD_NODES
                    parser.startNewChildNode()
                } else {
                    state = META_DATA
                }
            }
            CHILD_NODES -> {
                throw IllegalStateException("Values for childNodes should nog be processed by a parent NodeBuilder")
            }
            META_DATA -> {
                node = node.copy(metadataEntries = node.metadataEntries + MetadataEntry(value))

            }
        }

    }

    fun processCompletedChildNode(completedNode: Node) {
        node = node.copy(childNodes = node.childNodes + completedNode)
        if (node.hasAllChildren()) {
            state = META_DATA
        } else {
            parser.startNextChildNode()
        }
    }
}

enum class ProcessingState {
    NUMBER_OF_CHILD_NODES,
    NUMBER_OF_META_DATA,
    CHILD_NODES,
    META_DATA
}


data class Node(
        val numberOfChildNodes: Int = MIN_VALUE,
        val numberOfMetadataEntries: Int = MIN_VALUE,
        val childNodes: List<Node> = emptyList(),
        val metadataEntries: List<MetadataEntry> = emptyList()) {

    val valueForSecondCheck: Int by lazy {
        calculateSecondCheck()
    }

    private fun calculateSecondCheck(): Int {
        return if (hasNoChildNodes()) {
            metadataEntries.sumBy { it.value }
        } else {
            metadataEntries.map {
                val index = it.value - 1
                if (index in 0 until childNodes.size) {
                    childNodes[index].valueForSecondCheck
                } else {
                    0
                }
            }.sumBy { it }
        }

    }

    private fun hasNoChildNodes() = numberOfChildNodes == 0

    fun sumOfMetadataEntries(): Int =
            childNodes.sumBy { it.sumOfMetadataEntries() } + metadataEntries.sumBy { it.value }


    fun isComplete(): Boolean {

        return hasAllChildren() && hasAllMetadataEntries()

    }

    fun hasAllChildren() = numberOfChildNodes == childNodes.size

    private fun hasAllMetadataEntries() = numberOfMetadataEntries == metadataEntries.size


}

inline class MetadataEntry(val value: Int)