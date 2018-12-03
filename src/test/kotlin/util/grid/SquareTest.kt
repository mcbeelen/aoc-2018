package util.grid

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class SquareTest {


    @Test
    fun containsCoordinate() {
        // #123 @ 3,2: 5x4

        val square = Square(ScreenCoordinate(3, 2), 5, 4)

        assertTrue(square.contains(ScreenCoordinate(3, 2)))
        assertTrue(square.contains(ScreenCoordinate(3, 5)))
        assertTrue(square.contains(ScreenCoordinate(7, 5)))
        assertTrue(square.contains(ScreenCoordinate(7, 2)))

        assertFalse(square.contains(ScreenCoordinate(2, 3)))
        assertFalse(square.contains(ScreenCoordinate(8, 3)))
        assertFalse(square.contains(ScreenCoordinate(4, 1)))
        assertFalse(square.contains(ScreenCoordinate(4, 6)))


    }
}