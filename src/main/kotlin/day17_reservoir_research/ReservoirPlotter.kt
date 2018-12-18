package day17_reservoir_research

fun plotReservoir(sliceOfLand: SliceOfLand) {


    for (y in 0 .. sliceOfLand.maxY) {
        for (x in sliceOfLand.minX - 1..sliceOfLand.maxX + 1) {
            when {
                sliceOfLand.isThereClayAt(x, y) -> print('#')
                sliceOfLand.isThereWaterAt(x, y) -> print('~')
                else -> print(' ')
            }
        }
        println()
    }


}