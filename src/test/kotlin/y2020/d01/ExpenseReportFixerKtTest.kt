package y2020.d01

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis


class ExpenseReportFixerKtTest {

    @Test
    fun itShouldFindTwoExpensesThatSumTo2020() {
        val (low, high) = findTwoExpensesThatSumTo2020(y2020d1t1)
        assertThat(low, equalTo(299))
        assertThat(high, equalTo(1721))
    }

    @Test
    fun itShouldFindThreeExpensesThatSumTo2020() {
        val (low, middle, high) = findThreeExpensesThatSumTo2020(y2020d1t1)
        assertThat(low, equalTo(366))
        assertThat(middle, equalTo(675))
        assertThat(high, equalTo(979))
    }

    @Test
    fun itShouldFindTwoExpensesThatSumTo2020Additional() {
        val example = """1620
            |400
            |23
            |2199
        """.trimMargin()
        val (low, high) = findTwoExpensesThatSumTo2020(example)
        assertThat(low, equalTo(400))
        assertThat(high, equalTo(1620))
    }

    @Test
    fun itShouldSolveActualPuzzle() {
        val (first, last) = findTwoExpensesThatSumTo2020(y2020d1)
        assertThat(first * last, equalTo(270144))


        val measureNanoTime = measureTimeMillis {
            val (low, middle, high) = findThreeExpensesThatSumTo2020(y2020d1)
            assertThat(low * middle * high, equalTo(261342720))
        }

        println("Did part two in ${measureNanoTime} millis")
    }
}

private const val y2020d1t1 = """
    1721
979
366
299
675
1456""";
