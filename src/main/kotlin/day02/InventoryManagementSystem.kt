package day02

import java.lang.IllegalStateException
import kotlin.system.measureTimeMillis

class InventoryManagementSystem(boxesInWarehouse: List<String>) {


    private val boxIDs = boxesInWarehouse.map { BoxId(it) }

    val numberOfBoxesWithDoubleLetters = boxIDs.filter { it.hasAnyLetterTwice }.count()
    val numberOfBoxesWithTippleLetters = boxIDs.filter { it.hasAnyLetterTripple }.count()
    val checksum = numberOfBoxesWithDoubleLetters * numberOfBoxesWithTippleLetters


    fun findBoxesWithSimularBoxId(): Pair<BoxId, BoxId> {
        boxIDs.withIndex()
                .forEach {
                    val candidateBox = it.value
                    val other = boxIDs.subList(it.index, boxIDs.size).firstOrNull { other -> other.countNumberOfDifferentLetters(candidateBox) == 1 }

                    if (other != null) {
                        return Pair(candidateBox, other)
                    }
                }
        throw IllegalStateException("No suitable boxes found")

    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val timeElapsed = measureTimeMillis {
                val ims = InventoryManagementSystem(DAY_2_INPUT.trimIndent().lines())
                println("Checksum: ${ims.checksum}")

                val (first, other) = ims.findBoxesWithSimularBoxId()
                println("Simular boxes are     : '${first.label}' and '${other.label}'")

                val commonLetters = first.findCommonLettersWith(other)

                println("The common letters are: '${commonLetters}'")






            }

            println("Solved problem in ${timeElapsed}ms")
        }
    }
}
