package util.grid.search

import java.lang.Integer.MAX_VALUE

abstract class Vertex<V> : Comparable<Vertex<V>> where V : Vertex<V> {

    val previousVertexes: MutableList<V>  = ArrayList()

    var distanceFromSource : Int = MAX_VALUE

    val key : ZobraistKey by lazy {
        buildZobristKey()
    }


    abstract protected fun buildZobristKey(): ZobraistKey

    override fun compareTo(other: Vertex<V>) = distanceFromSource - other.distanceFromSource

    fun addPath(vertex: V) {
        previousVertexes.add(vertex)
    }

    override fun toString(): String {
        return key.toString()
    }


}
