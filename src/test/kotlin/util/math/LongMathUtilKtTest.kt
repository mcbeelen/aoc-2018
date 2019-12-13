package util.math

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class LongMathUtilKtTest {

    @Test
    fun lcm() {
        assertThat(lcm(10, 3), equalTo(30L))
        assertThat(lcm(15, 3), equalTo(15L))
        assertThat(lcm(42, 56), equalTo(7L * 3 * 2 * 2 * 2))
        assertThat("Recursive LCM", lcm(lcm(56344, 186028), 231614), equalTo(303459551979256L))
    }

    @Test
    fun threeFactors() {
        assertThat(lcm(3, 12, 16), equalTo(48L))
        assertThat(lcm(3, 9, 21), equalTo(63L))
        assertThat(lcm(56344, 186028, 231614), equalTo(303459551979256L))

    }
}
