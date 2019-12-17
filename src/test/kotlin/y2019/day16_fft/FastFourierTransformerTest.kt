package y2019.day16_fft

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class FastFourierTransformerTest() {

    @Test
    fun parseInputToDigits() {

        val input = toDigits("15243")
        assertThat(input[4], equalTo(3))
        assertThat(input.size, equalTo(5))
    }

    @Test
    fun verifyYieldingBasePatternForPhase() {
        assertThat(yieldBasePattern(1).take(8).toList(), equalTo(listOf(1, 0, -1, 0, 1, 0, -1, 0)))
        assertThat(yieldBasePattern(3).take(12).toList(), equalTo(listOf(0, 0, 1, 1, 1, 0, 0, 0, -1, -1, -1, 0)))
    }



    @Test
    fun verifySinglePhaseTransformation() {
        val input = toDigits("12345678")
        val afterOnePhase = performOnePhaseOfTransformation(input)

        assertThat(afterOnePhase, equalTo(toDigits("48226158")))
        val afterPhaseTwo = performOnePhaseOfTransformation(afterOnePhase)
        assertThat(afterPhaseTwo, equalTo(toDigits("34040438")))
        val afterPhaseThree = performOnePhaseOfTransformation(afterPhaseTwo)
        assertThat(afterPhaseThree, equalTo(toDigits("03415518")))
        val afterPhaseFour = performOnePhaseOfTransformation(afterPhaseThree)
        assertThat(afterPhaseFour, equalTo(toDigits("01029498")))

    }


    @Test
    fun verifyLargerExampleWithMultiplePasses() {

        assertThat(performTransformation(toDigits("80871224585914546619083218645595"), 100).subList(0, 8), equalTo(toDigits("24176176")))
        assertThat(performTransformation(toDigits("19617804207202209144916044189917"), 100).subList(0, 8), equalTo(toDigits("73745418")))
        assertThat(performTransformation(toDigits("69317163492948606335995924319873"), 100).subList(0, 8), equalTo(toDigits("52432133")))
    }
}

