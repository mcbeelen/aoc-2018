package y2019.day16_fft

import java.util.LinkedList
import kotlin.math.abs
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue


val basePattern = listOf(0, 1, 0, -1)

fun toDigits(input: String): List<Int> {
    return input.map { it.toString().toInt() }.toList()
}

fun performFastTransformation(input: List<Int>, numberOfPhases : Int): List<Int> {
    if (numberOfPhases == 0) { return input }
    val transformed = performOneFastTransformation(input)
    return performFastTransformation(transformed, numberOfPhases - 1)
}

fun performOneFastTransformation(input: List<Int>): List<Int> {
    val reversed = input.reversed()
    val calculated : MutableList<Int> = LinkedList()
    var sum = 0;
    reversed.forEach {
        sum = (sum + it) % 10
        calculated.add(sum)
    }
    return calculated.reversed()

}

fun performTransformation(input: List<Int>, numberOfPhases : Int): List<Int> {
    if (numberOfPhases == 0) { return input }
    val transformed = performOnePhaseOfTransformation(input)
    return performTransformation(transformed, numberOfPhases - 1)
}

fun performOnePhaseOfTransformation(input: List<Int>): List<Int> {
    return 1.rangeTo(input.size).map {
        calculateDigits(it, input)
    }.toList()
}

private fun calculateDigits(patternIndex: Int, input: List<Int>) = abs(yieldBasePattern(patternIndex).zip(input.asSequence())
                .map { it.first * it.second }
                .sum() % 10)

fun yieldBasePattern(phaseNumber: Int): Sequence<Int> {
    return sequence {
        var phaseIndex = 1
        var multiplierIndex = 0
        while (true) {
            while (multiplierIndex < basePattern.size) {
                val multiplier = basePattern[multiplierIndex]
                while (phaseIndex < phaseNumber) {
                    yield(multiplier)
                    phaseIndex++
                }
                phaseIndex = 0
                multiplierIndex++
            }
            multiplierIndex = 0
        }
    }
}


fun partOneOfFastFourierTransformation(): String {
    val output = performTransformation(toDigits(FFT_INPUT), 100).subList(0, 8).joinToString(separator = "")
    println(output)
    return output
}

fun partTwoOfFastFourierTransformation(): String {
    val realSignal: MutableList<Int> = realSignal(toDigits(FFT_INPUT))
    val offset = FFT_INPUT.take(7).toInt()
    val relevantInput = realSignal.subList(offset, realSignal.size)

    println("Do the transformations on last ${relevantInput.size} digits")

    return performFastTransformation(relevantInput, 100).joinToString("")
}

private fun realSignal(toDigits: List<Int>): MutableList<Int> {
    val realSignal: MutableList<Int> = ArrayList();
    repeat(10_000) {
        realSignal.addAll(toDigits)
    }
    return realSignal
}




@ExperimentalTime
fun main() {
    val (_, durationOfPartOne) = measureTimedValue { partOneOfFastFourierTransformation() }
    println(durationOfPartOne)

    val (output, durationOfParTwo) = measureTimedValue { partTwoOfFastFourierTransformation() }
    println(output.take(8))
    println(durationOfParTwo)


}





internal const val FFT_INPUT = "59796737047664322543488505082147966997246465580805791578417462788780740484409625674676660947541571448910007002821454068945653911486140823168233915285229075374000888029977800341663586046622003620770361738270014246730936046471831804308263177331723460787712423587453725840042234550299991238029307205348958992794024402253747340630378944672300874691478631846617861255015770298699407254311889484508545861264449878984624330324228278057377313029802505376260196904213746281830214352337622013473019245081834854781277565706545720492282616488950731291974328672252657631353765496979142830459889682475397686651923318015627694176893643969864689257620026916615305397"