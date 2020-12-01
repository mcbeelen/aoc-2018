package y2020.d01

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test



class ExpenseReportFixerKtTest {

    @Test
    fun itShouldFindTwoExpensesThatSumTo2020() {
        val example = """1721
979
366
299
675
1456"""
        val (low, high) = findTwoExpensesThatSumTo2020(example)
        assertThat(low, equalTo(299))
        assertThat(high, equalTo(1721))
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
}
