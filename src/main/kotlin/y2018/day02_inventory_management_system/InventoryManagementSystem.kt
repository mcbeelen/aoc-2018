package y2018.day02_inventory_management_system

import java.lang.IllegalStateException
import kotlin.system.measureTimeMillis

class InventoryManagementSystem(private val boxesInWarehouse: List<BoxId>) {



    val numberOfBoxesWithDoubleLetters = boxesInWarehouse.filter { it.hasAnyLetterTwice }.count()
    val numberOfBoxesWithTippleLetters = boxesInWarehouse.filter { it.hasAnyLetterTripple }.count()
    val checksum = numberOfBoxesWithDoubleLetters * numberOfBoxesWithTippleLetters


    fun findBoxesWithSimularBoxId(): Pair<BoxId, BoxId> {
        boxesInWarehouse.withIndex()
                .forEach {
                    val candidateBox = it.value
                    val other = boxesInWarehouse
                            .subList(it.index, boxesInWarehouse.size)
                            .firstOrNull { other -> isSimilar(other, candidateBox) }

                    if (other != null) {
                        return Pair(candidateBox, other)
                    }
                }

        throw IllegalStateException("No suitable boxes found")

    }

    private fun isSimilar(other: BoxId, candidateBox: BoxId) = other.countNumberOfDifferentLetters(candidateBox) == 1



    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val timeElapsed = measureTimeMillis {

                val boxesInWarehouse = DAY_2_INPUT.trimIndent().lines().map { BoxId(it) }
                val ims = InventoryManagementSystem(boxesInWarehouse)

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
