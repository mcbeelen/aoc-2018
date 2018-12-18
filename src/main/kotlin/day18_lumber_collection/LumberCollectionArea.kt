package day18_lumber_collection

import day18_lumber_collection.Acre.*
import util.grid.ScreenCoordinate

data class LumberCollectionArea(private val areaMap: Map<ScreenCoordinate, Acre>) {

    fun typeAt(x: Int, y: Int) = areaMap.getValue(ScreenCoordinate(x, y))

    fun hasLocation(location: ScreenCoordinate) = areaMap.containsKey(location)

    fun countOfTrees() = countNumberOf(TREES)
    fun countOfLumberyards() = countNumberOf(LUMBERYARD)


    private fun countNumberOf(type: Acre): Int {
        var count = 0
        for (x in 0..maxX) {
            for (y in 0..maxY) {
                if (typeAt(x, y) == type) {
                    count++
                }
            }
        }
        return count
    }

    val maxX: Int by lazy { areaMap.map { it.key.left }.max() ?: Int.MIN_VALUE }

    val maxY: Int by lazy { areaMap.map { it.key.top }.max() ?: Int.MIN_VALUE }


}


fun letMinutePass(lumberCollectionArea: LumberCollectionArea): LumberCollectionArea {

    val areaMap: MutableMap<ScreenCoordinate, Acre> = HashMap()

    for (x in 0 .. lumberCollectionArea.maxX) {
        for (y in 0 .. lumberCollectionArea.maxY) {
            areaMap[ScreenCoordinate(x, y)] = determineNextAcre(lumberCollectionArea, x, y)
        }
    }

    return LumberCollectionArea(areaMap)

}

fun determineNextAcre(lumberCollectionArea: LumberCollectionArea, x: Int, y: Int): Acre {
    return when(lumberCollectionArea.typeAt(x, y)) {
        OPEN -> determineNextOpenAcre(lumberCollectionArea, x, y)
        TREES -> determineNextTreesAcre(lumberCollectionArea, x, y)
        LUMBERYARD -> determineNextLumberyardAcre(lumberCollectionArea, x, y)
    }
}


fun determineNextOpenAcre(lumberCollectionArea: LumberCollectionArea, x: Int, y: Int): Acre {
    val count = countAdjacent(TREES, lumberCollectionArea, x, y)
    return if (count >= 3) { TREES } else { OPEN }
}

fun determineNextTreesAcre(lumberCollectionArea: LumberCollectionArea, x: Int, y: Int): Acre {
    val count = countAdjacent(LUMBERYARD, lumberCollectionArea, x, y)
    return if (count >= 3) { LUMBERYARD } else { TREES }
}

fun determineNextLumberyardAcre(lumberCollectionArea: LumberCollectionArea, x: Int, y: Int): Acre {
    val trees = countAdjacent(TREES, lumberCollectionArea, x, y)
    val lumberyards = countAdjacent(LUMBERYARD, lumberCollectionArea, x, y)
    return if (trees > 0 && lumberyards > 0) { LUMBERYARD } else { OPEN }
}





fun countAdjacent(acre: Acre, lumberCollectionArea: LumberCollectionArea, x: Int, y: Int): Int {

    var count = 0
    for (i in -1 .. 1) {
        for (j in -1 .. 1) {
            if (i != 0 || j != 0) {

                val location = ScreenCoordinate(x + i, y + j)

                if (lumberCollectionArea.hasLocation(location) && lumberCollectionArea.typeAt(x + i, y + j) == acre) {
                    count++
                }
            }
        }
    }

    return count


}


enum class Acre {
    OPEN,
    TREES,
    LUMBERYARD
}