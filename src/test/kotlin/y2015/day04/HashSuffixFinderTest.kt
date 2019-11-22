package y2015.day04

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.startsWith
import org.junit.Test

class HashSuffixFinderTest {

    @Test
    fun `suffix for key abcdef should be 609043`() {
        assertThat(findSuffixThatYieldMd5HashWith5leadingZeros("abcdef"), equalTo(609043))
    }


    @Test
    fun `suffix for key pqrstuv should be 1048970`() {
        assertThat(findSuffixThatYieldMd5HashWith5leadingZeros("pqrstuv"), equalTo(1048970))
    }

    @Test
    fun checkMd5() {
        assertThat("abcdef609043".md5(), startsWith("000001dbbfa"))
    }

}



