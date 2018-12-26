package util.grid.search

import mu.KotlinLogging
import java.util.*
import kotlin.collections.HashMap

abstract class BreadthFirstSearchAlgorithm<V, E>(val graph: Graph<V, E>) where V : Vertex<V>, E : Edge<V> {

    private val logger = KotlinLogging.logger { }

    protected val unvisitedVertices: Queue<V> = buildQueue()

    open fun <V> buildQueue(): Queue<V> = ArrayDeque<V>()

    private val visitedVertices: MutableMap<ZobraistKey, V> = HashMap()

    fun findShortestPath(origin: V, destination: V): Path<V>? {
        try {
            initiateStartingVertex(origin)

            performSearchForShortestPathTo(destination)
            return buildPathFrom(origin, visitedVertices.getValue(destination.key))
        } catch (e: IllegalStateException) {
            return null
        }
    }

    private fun initiateStartingVertex(origin: V) {
        origin.distanceFromSource = 0
        unvisitedVertices.offer(origin)
        visitedVertices.put(origin.key, origin)
    }

    fun exploreGraphFrom(origin: V) {
        initiateStartingVertex(origin)
        performSearchForShortestPathTo(origin)
    }


    private fun performSearchForShortestPathTo(destination: V) {
        while (stillNeedToProcessNodes(destination)) {
            processNode()
        }
    }

    open fun stillNeedToProcessNodes(destination: V): Boolean {
        return ! hasDestinationBeenReached(destination)
    }

    private fun hasDestinationBeenReached(destination: V) = visitedVertices.containsKey(destination.key)

    protected fun peekAtClosestVertex() : V = unvisitedVertices.peek()

    fun numberOfVisitedVertices() = visitedVertices.size


    private fun processNode() {

        if (unvisitedVertices.isEmpty()) {
            throw IllegalStateException("No more unvisitedVertices available to explore")
        }

        val vertex = unvisitedVertices.poll()
        if (vertex != null) {
            exploreVertex(vertex) {
                processEdge(it)
            }
        }
    }

    abstract fun exploreVertex(vertex: V, callback: (E) -> Unit)

    private fun processEdge(edge: E) {
        val distanceFromSourceToDestination = edge.origin.distanceFromSource + edge.distance
        val vertexAtTheEndOfTheEdge = traverseEdge(edge, distanceFromSourceToDestination)

        if (visitedVertices.containsKey(vertexAtTheEndOfTheEdge.key)) {

            val previousVisit = visitedVertices.getValue(vertexAtTheEndOfTheEdge.key)

            if (distanceFromSourceToDestination < previousVisit.distanceFromSource) {
                visitedVertices.put(vertexAtTheEndOfTheEdge.key, vertexAtTheEndOfTheEdge)
            } else {
                if (distanceFromSourceToDestination == previousVisit.distanceFromSource) {
                    previousVisit.addPath(edge.origin)
                }
            }
        } else {
            logger.debug("Discovered a new vertex: ${edge.destination}")
            visitedVertices.put(vertexAtTheEndOfTheEdge.key, vertexAtTheEndOfTheEdge)

            unvisitedVertices.offer(edge.destination)
        }
    }

    private fun traverseEdge(edge: E, distanceFromSourceToDestination: Int): V {
        val destination = edge.destination

        (destination).let {
            it.distanceFromSource = distanceFromSourceToDestination
            it.previousVertexes.add(edge.origin)
        }

        return destination
    }


    private fun buildPathFrom(origin: V, destination: V): Path<V> {

        val stack = Stack<V>()
        stack.push(destination)

        var currentNode : V = destination
        while (currentNode != origin) {
            currentNode = currentNode.previousVertexes.first()
            stack.push(currentNode)
        }


        return Path(stack.reversed())

    }

}
