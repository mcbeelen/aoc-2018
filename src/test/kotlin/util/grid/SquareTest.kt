package util.grid

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
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

    @Test
    fun missingTest() {
        val squareOne = Square(ScreenCoordinate(6, 2), 2, 16)
        val squareTwo = Square(ScreenCoordinate(2, 4), 16, 1)

        assertTrue(squareOne.overLapsWith(squareTwo))
    }

    @Test
    fun `just touching at a single point`() {
        val topLeft = Square(ScreenCoordinate(2, 2), 2, 2)
        val topRight = Square(ScreenCoordinate(5, 2), 2, 2)

        val center = Square(ScreenCoordinate(3, 3), 3, 3)

        val bottomRight = Square(ScreenCoordinate(5, 5), 1, 1)
        val bottomLeft = Square(ScreenCoordinate(0, 5), 4, 1)

        val beyond = Square(ScreenCoordinate(6, 6), 1, 1)

        assertTrue(topLeft.overLapsWith(center))
        assertTrue(topRight.overLapsWith(center))
        assertTrue(center.overLapsWith(bottomRight))
        assertTrue(center.overLapsWith(bottomLeft))

        assertFalse(center.overLapsWith(beyond))

    }


    @Test
    fun `just overlapping on an entire border`() {
        val squareOne = Square(ScreenCoordinate(2, 2), 2, 2)
        val squareTwo = Square(ScreenCoordinate(3, 1), 1, 8)

        assertTrue(squareOne.overLapsWith(squareTwo))

    }


    @Test
    fun itShouldExpandToTopLeft() {
        val coordinates = listOf(ScreenCoordinate(5, 6), ScreenCoordinate(1, 1))

        buildSquareContainingAll(coordinates).let {
            assertThat("Left", it.left, equalTo(1))
            assertThat("Top", it.top, equalTo(1))
            assertThat("Bottom", it.bottom, equalTo(6))
            assertThat("Right", it.right, equalTo(5))
        }

    }


    @Test
    fun buildSmallestPossibleSquareContainingAllCoordinates() {
        val coordinates = listOf(ScreenCoordinate(5, 6), ScreenCoordinate(1, 1), ScreenCoordinate(8, 8))

        buildSquareContainingAll(coordinates).let {
            assertThat("Left", it.left, equalTo(1))
            assertThat("Top", it.top, equalTo(1))
            assertThat("Bottom", it.bottom, equalTo(8))
            assertThat("Right", it.right, equalTo(8))
        }

    }


    @Test
    fun allPoints() {
        val square = Square(ScreenCoordinate(2, 2), 2, 2)
        square.allPoints().let {
            assertThat(it.count(), equalTo(4))
            assertTrue(it.contains(ScreenCoordinate(2, 2)))
            assertTrue(it.contains(ScreenCoordinate(2, 3)))
            assertTrue(it.contains(ScreenCoordinate(3, 2)))
            assertTrue(it.contains(ScreenCoordinate(3, 3)))
        }
    }

    @Test
    fun testIsOnBorder() {
        val square = Square(ScreenCoordinate(4, 4), 3, 3)
        square.let {
            assertTrue(it.isOnBorder(ScreenCoordinate(4, 4)))
            assertTrue(it.isOnBorder(ScreenCoordinate(4, 5)))
            assertTrue(it.isOnBorder(ScreenCoordinate(5, 4)))
            assertTrue(it.isOnBorder(ScreenCoordinate(5, 6)))
            assertTrue(it.isOnBorder(ScreenCoordinate(6, 6)))
            assertTrue(it.isOnBorder(ScreenCoordinate(6, 5)))

            assertFalse(it.isOnBorder(ScreenCoordinate(5, 5)))

        }
    }
}
