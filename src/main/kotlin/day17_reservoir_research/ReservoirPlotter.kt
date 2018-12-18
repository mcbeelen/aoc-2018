package day17_reservoir_research

fun plotReservoir(sliceOfLand: SliceOfLand) {

    println()
    println("__________________________________________________________________________________________________________")
    println()
    println()

    print("      ")
    for (x in sliceOfLand.minX - 1..sliceOfLand.maxX + 1) {
        print("${x.rem(10)}")
    }
    println()
    for (y in 0 .. sliceOfLand.maxY) {

        val rem = y.rem(1000)
        val number = rem.toString().padStart(4, '0')
        print("$number: ")

        for (x in sliceOfLand.minX - 1..sliceOfLand.maxX + 1) {
            when {
                sliceOfLand.isThereClayAt(x, y) -> print('#')
                sliceOfLand.isThereWaterAt(x, y) -> plotTile(sliceOfLand.tileAt(x, y))
                else -> print(' ')
            }
        }
        println()
    }

    println("__________________________________________________________________________________________________________")
    println()



}

fun plotTile(tile: TileOfSoil) {
    print(tile.getPrint())
}


