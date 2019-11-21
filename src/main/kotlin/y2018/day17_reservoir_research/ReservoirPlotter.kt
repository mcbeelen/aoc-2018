package y2018.day17_reservoir_research

import util.console.*

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
                sliceOfLand.isThereClayAt(x, y) -> print("$ANSI_WHITE$ANSI_YELLOW_BACKGROUND#$ANSI_RESET")
                sliceOfLand.isThereWaterAt(x, y) -> plotTile(sliceOfLand.tileAt(x, y))
                else -> print("$ANSI_BLACK_BACKGROUND $ANSI_RESET")
            }
        }
        println()
    }

    println("__________________________________________________________________________________________________________")
    println()



}

fun plotTile(tile: TileOfSoil) {
    print("$ANSI_WHITE$ANSI_BLUE_BACKGROUND${tile.getPrint()}$ANSI_RESET")
}


