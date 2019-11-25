package y2015.day05

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NaughtyOrNicePredicateTest {
    @Test
    fun `ugknbfddgicrmopn is nice`() {
        assertTrue(isNicePartOne("ugknbfddgicrmopn"))
        assertTrue(isNicePartOne("aaa"))
        assertFalse(isNicePartOne("jchzalrnumimnmhp"))
        assertFalse(isNicePartOne("dvszwmarrgswjxmb"))
        assertFalse(isNicePartOne("haegwjzuvuyypxyu"))
    }

    @Test
    fun partTwo() {
        assertTrue(containsOneRepeatingLetterWithOneLetterInBetween("qjhvhtzxzqqjkmpb"))
        assertTrue(hasPairOfLettersThatAppearsTwice("qjhvhtzxzqqjkmpb"))
        assertTrue(isNicePartTwo("xxyxx"))

        assertFalse(isNicePartTwo("uurcxstgmygtbstg"))
        assertFalse(isNicePartTwo("ieodomkazucvgmuy"))
    }


}


/*
Now, a nice string is one with all of the following properties:

It contains a pair of any two letters that appears at least twice in the string without overlapping,
like xyxy (xy) or aabcdefgaa (aa), but not like aaa (aa, but it overlaps).
It contains at least one letter which repeats with exactly one letter between them, like xyx, abcdefeghi (efe), or even aaa.


For example:

 is nice because is has a pair that appears twice (qj) and a letter that repeats with exactly one letter between them (zxz).
xxyxx is nice because it has a pair that appears twice and a letter that repeats with one between, even though the letters used by each rule overlap.
 is naughty because it has a pair (tg) but no repeat with a single letter between them.
 is naughty because it has a repeating letter with one between (odo), but no pair that appears twice.
 */

