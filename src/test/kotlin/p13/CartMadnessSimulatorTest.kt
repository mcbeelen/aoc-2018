package p13

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import util.grid.ScreenCoordinate


class CartMadnessSimulatorTest {


    lateinit var simulator : CartMadnessSimulator

    @Before
    fun setUp() {
        simulator = CartMadnessSimulator(CART_MADNESS_EXAMPLE)
    }

    @Test
    fun itShouldFindCartsInInput() {

        assertTrue(simulator.isCartPresentAt(ScreenCoordinate(2, 0)))
        assertTrue(simulator.isCartPresentAt(ScreenCoordinate(9, 3)))

        simulator.tick()

        assertTrue(simulator.isCartPresentAt(ScreenCoordinate(3, 0)))
        assertTrue(simulator.isCartPresentAt(ScreenCoordinate(10, 3)))

    }

    @Test
    fun example() {

        val coordinateOfFirstCrash : ScreenCoordinate = simulator.findLocationOfFirstCrash()

        assertThat(coordinateOfFirstCrash, equalTo(ScreenCoordinate(7,3)))

    }

    @Test
    fun partOne() {

        val actualSimulator = CartMadnessSimulator(CART_MADNESS_INPUT)
        val coordinateOfFirstCrash : ScreenCoordinate = actualSimulator.findLocationOfFirstCrash()

        assertThat(coordinateOfFirstCrash, ! equalTo(ScreenCoordinate(137,43)))
        println(coordinateOfFirstCrash)

    }
}

