package day02

import org.junit.Assert.assertFalse
import org.junit.Test

class BoxIdTest {

    @Test
    fun firstExampleWithoutDuplicates() {
        val boxId = BoxId("abcdef")

        assertFalse(boxId.hasAnyLetterTwice())
    }
}