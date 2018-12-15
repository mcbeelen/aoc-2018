package util.grid.search

abstract class Edge<V> (val origin: V, val destination: V, val distance : Int = 1) where V : Vertex<V>
