package y2018.day08_memory_manuever

import java.util.*

class NodeInputParser {

    private val nodeBuilderStack = Stack<NodeBuilder>()

    init {
        prepareNextNodeBuilder()
    }

    lateinit var completedNode : Node

    fun parse(next: Int) {

        nodeBuilderStack.peek().processInput(next)

        while(nodeBuilderStack.isNotEmpty() && nodeBuilderStack.peek().node.isComplete()) {

            completedNode = nodeBuilderStack.pop().node

            if (nodeBuilderStack.isNotEmpty()) {
                val nodeBuilder = nodeBuilderStack.peek()
                nodeBuilder.processCompletedChildNode(completedNode)

            }
        }
    }


    private fun prepareNextNodeBuilder() {
        nodeBuilderStack.push(NodeBuilder(this))
    }

    fun startNewChildNode() {
        prepareNextNodeBuilder()
    }

    fun startNextChildNode() {
        prepareNextNodeBuilder()
    }


}
