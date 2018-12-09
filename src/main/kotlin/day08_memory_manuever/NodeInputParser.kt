package day08_memory_manuever

import java.util.*

class NodeInputParser {

    private val nodeBuilderStack = Stack<NodeBuilder>()

    init {
        startNewNode()
    }

    lateinit var completedNode : Node

    fun parse(next: Int) {

        if (nodeBuilderStack.peek().processInput(next)) {
            startNewNode()
        }

        while(nodeBuilderStack.isNotEmpty() && nodeBuilderStack.peek().node.isComplete()) {

            completedNode = nodeBuilderStack.pop().node

            if (nodeBuilderStack.isNotEmpty()) {
                val nodeBuilder = nodeBuilderStack.peek()
                nodeBuilder.processCompletedChildNode(completedNode)
                if (!nodeBuilder.node.hasAllChildren()) {
                    startNewNode()
                }
            }
        }
    }

    private fun startNewNode() {
        nodeBuilderStack.push(NodeBuilder())
    }


}
