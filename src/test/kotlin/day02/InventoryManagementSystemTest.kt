package day02

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class InventoryManagementSystemTest {


    @Test
    fun examples() {
        val boxesInWarehouse = """abcdef
        bababc
        abbcde
        abcccd
        aabcdd
        abcdee
        ababab
        """

        val ims = InventoryManagementSystem(boxesInWarehouse.trimIndent().lines())

        assertThat(ims.numberOfBoxesWithDoubleLetters, equalTo(4))
        assertThat(ims.numberOfBoxesWithTrippleLetters, equalTo(3))

        assertThat(ims.checksum, equalTo(12))


    }
}