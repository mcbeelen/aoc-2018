package y2019.day08

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class SpaceImageFormatTest {

    @Test
    fun exampleOne() {
        assertThat(SIF_ENCODED_IMAGE.length % 25, equalTo(0))
        assertThat(SIF_ENCODED_IMAGE.length % 6, equalTo(0))

        SIF_ENCODED_IMAGE.take(25 * 6)

        assertThat(splitIntoLayers(SIF_ENCODED_IMAGE, WIDTH_OF_IMAGE, HEIGHT_OF_IMAGE).count(), equalTo(100))

        assertThat(partOne(SIF_ENCODED_IMAGE), equalTo(2318))
    }

    @Test
    fun itShouldDecodeImage() {
        val image = "0222112222120000"

        assertThat(decodeImage(image, 2, 2).toString(), equalTo("0110"))

    }


}

