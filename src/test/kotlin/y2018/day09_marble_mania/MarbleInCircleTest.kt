package y2018.day09_marble_mania

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class MarbleInCircleTest {


    @Test
    fun marbleZeroShouldHaveItSelfAsNextAndPrevious() {


        val marbleInCicle = MarbleInCircle(Marble(0))
        assertThat(marbleInCicle.next, equalTo(marbleInCicle))
        assertThat(marbleInCicle.previous, equalTo(marbleInCicle))

    }
}