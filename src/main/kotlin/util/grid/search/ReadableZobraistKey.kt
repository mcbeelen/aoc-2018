package util.grid.search

data class ReadableZobraistKey(private val key : String) : ZobraistKey(key) {
    override fun toString(): String {
        return "ZobraistKey('$key')"
    }
}
