package y2019.day04

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SecureContainerTest {

      @Test
      fun examples() {
          assertTrue(couldBePassword(111111))
          assertFalse(couldBePassword(223450))
          assertFalse("No double", couldBePassword(123789))
      }

    @Test
    fun examplesPartTwo() {
        assertFalse("part of larger group", couldStillBePassword(111111))
        assertFalse("part of larger group", couldStillBePassword(211111))
        assertFalse("Decreases", couldStillBePassword(223450))
        assertFalse("No double", couldStillBePassword(123789))

        assertTrue(couldStillBePassword(112233))
        assertFalse("fours in larger group", couldStillBePassword(123444))
        assertTrue(couldStillBePassword(111122))
    }


}

