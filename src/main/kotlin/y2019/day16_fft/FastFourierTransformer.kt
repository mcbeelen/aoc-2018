package y2019.day16_fft

import kotlin.math.abs
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue


val basePattern = listOf(0, 1, 0, -1)

fun toDigits(input: String): List<Int> {
    return input.map { it.toString().toInt() }.toList()
}

fun performFastTransformation(input: List<Int>, numberOfPhases : Int): List<Int> {
    if (numberOfPhases == 0) { return input }
    val transformed = performOnePhaseOfTransformation(input)
    println("${numberOfPhases.toString().padStart(4, ' ')} " + transformed.subList(0, 16).joinToString("") + " " + transformed.subList(16, transformed.size).joinToString(""))
    return performTransformation(transformed, numberOfPhases - 1)
}
fun performTransformation(input: List<Int>, numberOfPhases : Int): List<Int> {
    if (numberOfPhases == 0) { return input }
    val transformed = performOnePhaseOfTransformation(input)
    println("${numberOfPhases.toString().padStart(4, ' ')} " + transformed.subList(0, 16).joinToString("") + " " + transformed.subList(16, transformed.size).joinToString(""))
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

/*
Now that your FFT is working, you can decode the real signal.

The real signal is your puzzle input repeated 10000 times.
Treat this new signal as a single input list.
Patterns are still calculated as before, and 100 phases of FFT are still applied.


The first seven digits of your initial input signal also represent the message offset.
The message offset is the location of the eight-digit message in the final output list.

Specifically, the message offset indicates the number of digits to skip before reading the eight-digit message.

    For example, if the first seven digits of your initial input signal were 1234567,
    the eight-digit message would be the eight digits after skipping 1,234,567 digits of the final output list.
    Or, if the message offset were 7 and your final output list were 98765432109876543210, the eight-digit message would be 21098765.
    (Of course, your real message offset will be a seven-digit number, not a one-digit number like 7.)

Here is the eight-digit message in the final output list after 100 phases.
The message offset given in each input has been highlighted. (Note that the inputs given below are repeated 10000 times to find the actual starting input lists.)

03036732577212944063491565474664 becomes 84462026.
02935109699940807407585447034323 becomes 78725270.
03081770884921959731165446850517 becomes 53553731.
After repeating your input signal 10000 times and running 100 phases of FFT, what is the eight-digit message embedded in the final output list?
 */


fun partOneOfFastFourierTransformation(): String {
    val output = performTransformation(toDigits(FFT_INPUT), 100).subList(0, 8).joinToString(separator = "")
    println(output)
    return output
}

fun partTwoOfFastFourierTransformation(): String {
    val toDigits = toDigits(FFT_INPUT)
    val realSignal : MutableList<Int> = ArrayList();
    repeat(100) {
        realSignal.addAll(toDigits)
    }
    return performTransformation(realSignal, 1).joinToString("")
}

@ExperimentalTime
fun main() {
    val (unit, duration) = measureTimedValue { partOneOfFastFourierTransformation() }
    println(duration)

    println(FFT_INPUT)
    println(FFT_INPUT.reversed())

}





internal const val FFT_INPUT = "59796737047664322543488505082147966997246465580805791578417462788780740484409625674676660947541571448910007002821454068945653911486140823168233915285229075374000888029977800341663586046622003620770361738270014246730936046471831804308263177331723460787712423587453725840042234550299991238029307205348958992794024402253747340630378944672300874691478631846617861255015770298699407254311889484508545861264449878984624330324228278057377313029802505376260196904213746281830214352337622013473019245081834854781277565706545720492282616488950731291974328672252657631353765496979142830459889682475397686651923318015627694176893643969864689257620026916615305397"