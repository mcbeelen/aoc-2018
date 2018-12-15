package util.grid.search

abstract class Graph<V, out E> where V : Vertex<V>, E : Edge<V> {
    abstract fun findNeighbours(vertex: V): List<E>
}
