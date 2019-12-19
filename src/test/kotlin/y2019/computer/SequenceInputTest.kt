package y2019.computer

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class SequenceInputTest {

    @Test
    fun read() {
        val input = SequenceInput(listOf(9L, 3L, 5L))
        assertThat(input.read(), equalTo(9L))
        assertThat(input.read(), equalTo(3L))
        assertThat(input.read(), equalTo(5L))
    }
}