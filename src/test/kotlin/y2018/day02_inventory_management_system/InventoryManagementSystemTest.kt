package y2018.day02_inventory_management_system

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class InventoryManagementSystemTest {

    private val idsOfTheBoxes = """abcdef
        bababc
        abbcde
        abcccd
        aabcdd
        abcdee
        ababab
        """.trimIndent().lines()
    @Test
    fun checksumOfExampleWarehouseShouldBe12() {

        val boxesInWarehouse = idsOfTheBoxes.map { BoxId(it) }
        val ims = InventoryManagementSystem(boxesInWarehouse)

        assertThat(ims.numberOfBoxesWithDoubleLetters, equalTo(4))
        assertThat(ims.numberOfBoxesWithTippleLetters, equalTo(3))

        assertThat(ims.checksum, equalTo(12))

    }


    @Test
    fun `simularBoxIdsInExampleWarehouseShouldBe fghij and fguij`() {
        val ims = InventoryManagementSystem(boxIdsFromSecondChallenge.trimIndent().lines().map { BoxId(it) })

        val simularBoxIds = ims.findBoxesWithSimularBoxId()

        assertThat(simularBoxIds.first.label, equalTo("fghij"))
        assertThat(simularBoxIds.second.label, equalTo("fguij"))


    }
}