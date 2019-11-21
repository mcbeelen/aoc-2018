package y2018.day04_sleeping_guards

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class RecordTest {
    @Test
    fun itShouldParseRelevantInputFields() {


        val record = fromInput("[1518-11-02 00:50] wakes up")

        assertThat(record.month, equalTo(11))
        assertThat(record.day, equalTo(2))
        assertThat(record.minute, equalTo(Minute(50)))
        assertThat(record.action, equalTo("wakes up"))
    }
}