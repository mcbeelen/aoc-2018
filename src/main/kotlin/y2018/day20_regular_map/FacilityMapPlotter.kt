package y2018.day20_regular_map


fun plotFacility(facility: Facility) {

    val map = facility.map

    for (y in facility.minY - 1.. facility.maxY + 1) {
        for (x in facility.minX -1 ..facility.maxX + 1) {
            when {
                x == 0 && y == 0 -> print('X')
                x.rem(2) == 0 && y.rem(2) == 0 -> print('.')
                facility.isThereADoorAt(x, y) -> {
                    if (x.rem(2) == 0) { print('-') } else { print('|')}
                }
                else -> print('#')
            }
        }
        println()
    }


}
