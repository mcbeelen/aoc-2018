package day02

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BoxIdTest {

    @Test
    fun firstExampleWithoutDuplicates() {
        assertFalse(BoxId("abcdef").hasAnyLetterTwice)
        assertFalse(BoxId("abcdef").hasAnyLetterTripple)
    }

    @Test
    fun secondExampleCountForBothDoublesAndTriples() {
        val boxId = BoxId("bababc")
        assertTrue(boxId.hasAnyLetterTwice)
        assertTrue(boxId.hasAnyLetterTripple)

    }

    @Test
    fun fithExampleWithTwoLettersOccuringTwice() {
        assertTrue(BoxId("aabcdd").hasAnyLetterTwice)
    }

    @Test
    fun sixthExampleWithSingleLetterDuplicatesTwice() {
        val boxId = BoxId("abcdee")
        assertTrue(boxId.hasAnyLetterTwice)
        assertFalse(boxId.hasAnyLetterTripple)
    }


    @Test
    fun `seventh example with triple letters should not count for doubles`() {
        val boxId = BoxId("ababab")
        assertFalse(boxId.hasAnyLetterTwice)
        assertTrue(boxId.hasAnyLetterTripple)
    }
}