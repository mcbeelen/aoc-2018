package util.input

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class FileUtilsTest {
    @Test
    fun `it should read an input file from the resources directory`() {
        assertThat(loadLines("/y2020/d07/testInput.txt").size, equalTo(9))
    }
}
