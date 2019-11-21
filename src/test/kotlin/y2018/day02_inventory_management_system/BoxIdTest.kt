package y2018.day02_inventory_management_system

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

const val boxIdsFromSecondChallenge = """abcde
fghij
klmno
pqrst
fguij
axcye
wvxyz"""

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


    @Test
    fun itShouldCountDifferentLetterBetweenBoxIds() {
        val boxIds = boxIdsFromSecondChallenge.trimIndent().lines().map { BoxId(it) }

        assertThat(countNumberOfDifferentLetters(boxIds[0], boxIds[5]), equalTo(2))






    }

    private fun countNumberOfDifferentLetters(aBox: BoxId, anotherBox: BoxId) = aBox.countNumberOfDifferentLetters(anotherBox)
}