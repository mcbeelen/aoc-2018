package day17_reservoir_research

fun plotReservoir(sliceOfLand: SliceOfLand) {

    println()
    println()
    println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")

    print("---")
    for (x in sliceOfLand.minX - 1..sliceOfLand.maxX + 1) {
        print("${x.rem(10)}")
    }
    println()
    for (y in 0 .. sliceOfLand.maxY) {

        print("${y.rem(10)}: ")

        for (x in sliceOfLand.minX - 1..sliceOfLand.maxX + 1) {
            when {
                sliceOfLand.isThereClayAt(x, y) -> print('#')
                sliceOfLand.isThereWaterAt(x, y) -> print('~')
                else -> print(' ')
            }
        }
        println()
    }

    println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")
    println()



}