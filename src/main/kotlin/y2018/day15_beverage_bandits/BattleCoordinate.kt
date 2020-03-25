package y2018.day15_beverage_bandits

import util.grid.ScreenCoordinate
import util.grid.search.VertexInGrid

class BattleCoordinate(coordinate: ScreenCoordinate) : VertexInGrid<BattleCoordinate>(coordinate) {

    constructor(left: Int, top: Int) : this(ScreenCoordinate(left, top))


    override fun toString(): String {
        return "BC: " + super.toString()
    }

}




