package y2020.d18_Operation_Order

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Ignore
import org.junit.Test

class `Operation Order Test` {

    @Test
    fun linearExpressions() {
        assertThat(evaluateWithoutPrecedence("1 + 2"), equalTo(3))
        assertThat(evaluateWithoutPrecedence("1 + 2 + 3"), equalTo(6))
        assertThat(evaluateWithoutPrecedence("1 * 2 + 3"), equalTo(5))
        assertThat(evaluateWithoutPrecedence("1 + 2 * 3"), equalTo(9))
        assertThat(evaluateWithoutPrecedence("1 * 2 * 3"), equalTo(6))
    }

    @Test
    fun allExampleWithoutPrecedence() {
        assertThat(evaluateWithoutPrecedence("1 + 2 * 3 + 4 * 5 + 6"), equalTo(71))
        assertThat(evaluateWithoutPrecedence("1 + (2 * 3) + (4 * (5 + 6))"), equalTo(51))
        assertThat(evaluateWithoutPrecedence("2 * 3 + (4 * 5)"), equalTo(26))
        assertThat(evaluateWithoutPrecedence("5 + (8 * 3 + 9 + 3 * 4 * 3)"), equalTo(437))
        assertThat(evaluateWithoutPrecedence("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"), equalTo(12240))
        assertThat(evaluateWithoutPrecedence("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"), equalTo(13632))
    }

    @Test
    fun partOne() {
        assertThat(y2020d18input.lines().map { evaluateWithoutPrecedence(it) }.sum(), equalTo(6640667297513))
    }


    @Test
    fun linearExpressionsWithPrecedence() {
        assertThat(evaluateWithPrecedence("1 + 2"), equalTo(3))
        assertThat(evaluateWithPrecedence("1 + 2 + 3"), equalTo(6))
        assertThat(evaluateWithPrecedence("1 * 2 + 3"), equalTo(5))
        assertThat(evaluateWithPrecedence("1 + 2 * 3"), equalTo(9))
        assertThat(evaluateWithPrecedence("2 * 2 + 3"), equalTo(10))
    }

    @Test
    fun allExampleWithPrecedence() {
        assertThat(evaluateWithPrecedence("1 + 2 * 3 + 4 * 5 + 6"), equalTo(231))
        assertThat(evaluateWithPrecedence("1 + (2 * 3) + (4 * (5 + 6))"), equalTo(51))
        assertThat(evaluateWithPrecedence("2 * 3 + (4 * 5)"), equalTo(46))
        assertThat(evaluateWithPrecedence("5 + (8 * 3 + 9 + 3 * 4 * 3)"), equalTo(1445))
        assertThat(evaluateWithPrecedence("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))"), equalTo(669060))
        assertThat(evaluateWithPrecedence("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2"), equalTo(23340))
    }

}
