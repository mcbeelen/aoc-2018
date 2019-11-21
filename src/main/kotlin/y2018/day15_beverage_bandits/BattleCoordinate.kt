package y2018.day15_beverage_bandits

import util.grid.ScreenCoordinate
import util.grid.search.ReadableZobraistKey
import util.grid.search.Vertex

class BattleCoordinate(val coordinate: ScreenCoordinate) : Vertex<BattleCoordinate>() {

    constructor(left: Int, top: Int) : this(ScreenCoordinate(left, top))

    override fun buildZobristKey() = ReadableZobraistKey(coordinate.toString())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BattleCoordinate

        if (coordinate != other.coordinate) return false

        return true
    }

    override fun hashCode(): Int {
        return coordinate.hashCode()
    }

    override fun toString(): String {
        return "BC('<${coordinate.left}, ${coordinate.top}>')"
    }

}




