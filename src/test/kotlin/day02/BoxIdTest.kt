package day02

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BoxIdTest {

    @Test
    fun firstExampleWithoutDuplicates() {
        val boxId = BoxId("abcdef")

        assertFalse(boxId.hasAnyLetterTwice())
    }

    @Test
    fun sixthExampleWithSingleLetterDuplicatesTwice() {
        assertTrue(BoxId("abcdee").hasAnyLetterTwice())
    }
}