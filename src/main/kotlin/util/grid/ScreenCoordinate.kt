package util.grid

data class ScreenCoordinate(val left: Int = 0, val top: Int = 0) : Comparable<ScreenCoordinate> {

    override fun compareTo(other: ScreenCoordinate): Int {
        if (left == other.left) {
            return top - other.top
        }
        return left - other.left
    }


}
