package util.hyperspace

import arrow.core.Option
import arrow.core.extensions.list.monadFilter.filterMap

data class Point4D(val x: Int, val y: Int, val z: Int, val w : Int = 0) {
    fun neighbors(): List<Point4D> {
        return (-1..1).map { x ->
            (-1..1).map { y ->
                (-1..1).map { z ->
                    (-1..1).map { w ->
                        if ((x == y) && (x == z) && (x == w) && (x == 0)) {
                            Option.empty()
                        } else
                            Option.just(Point4D(this.x + x, this.y + y, this.z + z, this.w + w))
                    }
                }.flatten()
            }.flatten()
        }.flatten().filterMap { it }
    }
}
