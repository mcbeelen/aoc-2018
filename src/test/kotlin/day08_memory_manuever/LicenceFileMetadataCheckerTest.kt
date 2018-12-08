package day08_memory_manuever

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class LicenceFileMetadataCheckerTest {

    @Test
    fun itShouldSolveTheExampleCorrectly() {


        assertThat(sumMetadata(MEMORY_MENUEVER_EXAMPLE), equalTo(138))


    }

    private fun sumMetadata(licenseFile: String): Int {

        return 0

    }
}