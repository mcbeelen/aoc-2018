package y2015.day02_wrapping_paper

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class WrappingPaperOrderCalculatorTest {

    @Test
    fun `2x3x4 equals 58`() {
        assertThat(calculateRequiredWrappingPaper("2x3x4"), equalTo(58))
        assertThat(calculateRequiredRibbon("2x3x4"), equalTo(34))
    }

    @Test
    fun `1x1x10 equals 43`() {
        assertThat(calculateRequiredWrappingPaper("1x1x10"), equalTo(43))
        assertThat(calculateRequiredRibbon("1x1x10"), equalTo(14))
    }

}