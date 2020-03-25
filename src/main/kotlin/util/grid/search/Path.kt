package util.grid.search

data class Path<V>(val vertices: List<V>) where V : Vertex<V> {
    fun calculateDistance() = vertices.last().distanceFromSource
    fun contains(vertex: V) = vertices.contains(vertex)

    fun withAdditionalStep(destination: V) : Path<V> = this.copy(
            vertices = vertices + destination
    )
}
