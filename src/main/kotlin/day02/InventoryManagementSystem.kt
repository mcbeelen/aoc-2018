package day02

import kotlin.system.measureTimeMillis

class InventoryManagementSystem(boxesInWarehouse: List<String>) {
    private val boxIDs = boxesInWarehouse.map { BoxId(it) }

    val numberOfBoxesWithDoubleLetters = boxIDs.filter { it.hasAnyLetterTwice }.count()
    val numberOfBoxesWithTrippleLetters = boxIDs.filter { it.hasAnyLetterTripple }.count()
    val checksum = numberOfBoxesWithDoubleLetters * numberOfBoxesWithTrippleLetters




    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val timeElapsed = measureTimeMillis {
                val ims = InventoryManagementSystem(DAY_2_INPUT.trimIndent().lines())
                println("Checksum: ${ims.checksum}")
            }

            println("Calculatd checksum in ${timeElapsed}ms")
        }
    }
}
