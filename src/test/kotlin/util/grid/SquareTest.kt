package util.grid

import day03_overlapping_fabric.parseClaim
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


    @Test
    fun checkOverlaps() {
        val squareOne = Square(ScreenCoordinate(1, 3), 4, 4)
        val squareTwo = Square(ScreenCoordinate(3, 1), 4, 4)
        val squareThree = Square(ScreenCoordinate(5, 5), 2, 2)


        assertTrue(squareOne.overLapsWith(squareOne))
        assertTrue(squareOne.overLapsWith(squareTwo))
        assertFalse(squareOne.overLapsWith(squareThree))

        assertTrue(squareTwo.overLapsWith(squareOne))
        assertTrue(squareTwo.overLapsWith(squareTwo))
        assertFalse(squareTwo.overLapsWith(squareThree))

        assertFalse(squareThree.overLapsWith(squareOne))
        assertFalse(squareThree.overLapsWith(squareTwo))
        assertTrue(squareThree.overLapsWith(squareThree))



    }
}
