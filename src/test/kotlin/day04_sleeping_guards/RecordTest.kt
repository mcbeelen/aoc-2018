package day04_sleeping_guards

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class RecordTest {
    @Test
    fun itShouldParseRelevantInputFields() {


        val record = fromInput("[1518-11-02 00:50] wakes up")

        assert.that(record.month, equalTo(11))
        assert.that(record.day, equalTo(2))
        assert.that(record.minute, equalTo(50))
        assert.that(record.action, equalTo("wakes up"))
    }
}