package util.grid.search

abstract class ZobraistKey(private val key : String) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ZobraistKey) return false

        if (key != other.key) return false

        return true
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }



}
