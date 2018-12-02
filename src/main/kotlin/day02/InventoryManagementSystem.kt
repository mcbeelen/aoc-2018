package day02

class InventoryManagementSystem(boxesInWarehouse: List<String>) {
    private val boxIDs = boxesInWarehouse.map { BoxId(it) }

    val numberOfBoxesWithDoubleLetters = boxIDs.filter { it.hasAnyLetterTwice }.count()
    val numberOfBoxesWithTrippleLetters = boxIDs.filter { it.hasAnyLetterTripple }.count()
    val checksum = numberOfBoxesWithDoubleLetters * numberOfBoxesWithTrippleLetters


}
