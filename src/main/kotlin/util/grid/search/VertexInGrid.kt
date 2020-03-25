package util.grid.search

import util.grid.ScreenCoordinate

class StepInGrid<V>(origin: V, destination: V) : Edge<V>(origin, destination) where V : VertexInGrid<V>

open class VertexInGrid<V> (val coordinate: ScreenCoordinate) : Vertex<V>() where V : VertexInGrid<V> {
    override fun buildZobristKey() = ReadableZobraistKey(coordinate.toString())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VertexInGrid<*>

        if (coordinate != other.coordinate) return false

        return true
    }


    override fun hashCode(): Int {
        return coordinate.hashCode()
    }

    override fun toString(): String {
        return "at($coordinate)"
    }


}