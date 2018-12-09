package day08_memory_manuever

import day08_memory_manuever.ProcessingState.*
import java.lang.IllegalStateException
import kotlin.Int.Companion.MIN_VALUE

class NodeBuilder {

    var state : ProcessingState = NUMBER_OF_CHILD_NODES
    var node = Node()

    fun processInput(value: Int) : Boolean {

        when(state) {
            NUMBER_OF_CHILD_NODES -> {
                node = node.copy(numberOfChildNodes = value)
                state = NUMBER_OF_META_DATA
            }
            NUMBER_OF_META_DATA -> {
                node = node.copy(numberOfMetadataEntries = value)
                if (node.numberOfChildNodes > 0) {
                    state = CHILD_NODES
                    return true
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

        return false

    }

    fun processCompletedChildNode(completedNode: Node) {
        node = node.copy(childNodes = node.childNodes + completedNode)
        if (node.hasAllChildren()) {
            state = META_DATA
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

    fun sumOfMetadataEntries(): Int =
            childNodes.sumBy { it.sumOfMetadataEntries() } + metadataEntries.sumBy { it.value }


    fun isComplete(): Boolean {

        return hasAllChildren() && hasAllMetadataEntries()

    }

    fun hasAllChildren() = numberOfChildNodes == childNodes.size

    private fun hasAllMetadataEntries() = numberOfMetadataEntries == metadataEntries.size


}

inline class MetadataEntry(val value: Int)