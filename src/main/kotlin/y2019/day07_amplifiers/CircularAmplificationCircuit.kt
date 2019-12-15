package y2019.day07_amplifiers

import util.collections.generatePermutations
import y2019.computer.Buffer
import y2019.computer.BufferWithMemory
import y2019.computer.IntcodeComputer
import y2019.computer.Value
import y2019.computer.compile

private const val DEFAULT_INPUT = 0L

class CircularAmplificationCircuit(byteCode: List<Value>, phaseSetting: List<Value>) {

    private val bufferEA = BufferWithMemory()
    private val bufferAB = Buffer()
    private val bufferBC = Buffer()
    private val bufferCD = Buffer()
    private val bufferDE = Buffer()


    private var amplifiersA = IntcodeComputer(
            input = bufferEA,
            output = bufferAB,
            byteCode = byteCode)

    private var amplifiersB = IntcodeComputer(
            input = bufferAB,
            output = bufferBC,
            byteCode = byteCode)

    private var amplifiersC = IntcodeComputer(
            input = bufferBC,
            output = bufferCD,
            byteCode = byteCode)

    private var amplifiersD = IntcodeComputer(
            input = bufferCD,
            output = bufferDE,
            byteCode = byteCode)


    private var amplifiersE = IntcodeComputer(
            input = bufferDE,
            output = bufferEA,
            byteCode = byteCode)


    init {
        configureInitialPhaseSettingsInAmplifiers(phaseSetting)

        bufferEA.write(DEFAULT_INPUT)

    }

    private fun configureInitialPhaseSettingsInAmplifiers(phaseSetting: List<Value>) {
        bufferEA.write(phaseSetting[0])
        bufferAB.write(phaseSetting[1])
        bufferBC.write(phaseSetting[2])
        bufferCD.write(phaseSetting[3])
        bufferDE.write(phaseSetting[4])

        amplifiersA.tick()
        amplifiersB.tick()
        amplifiersC.tick()
        amplifiersD.tick()
        amplifiersE.tick()
    }


    fun calculateAmplification(): Value {

        println()
        while (!amplifiersD.isProgramFinished()) {
            while (bufferAB.isEmpty() && !amplifiersA.isProgramFinished()) {
                amplifiersA.tick()
            }

            while (bufferBC.isEmpty() && !amplifiersB.isProgramFinished()) {
                amplifiersB.tick()
            }

            while (bufferCD.isEmpty() && !amplifiersC.isProgramFinished()) {
                amplifiersC.tick()
            }

            while (bufferDE.isEmpty() && !amplifiersD.isProgramFinished()) {
                amplifiersD.tick()
            }
            while (bufferEA.isEmpty() && !amplifiersD.isProgramFinished()) {
                amplifiersE.tick()
            }
            println()
        }

        while (!amplifiersE.isProgramFinished()) {
            amplifiersE.tick()
        }



        return bufferEA.lastPrintedValue
    }

}


fun findMaxThrusterSignalInCircularConfiguration(sourceCode: String): Value {

    val byteCode = compile(sourceCode)

    val phaseSettings = listOf(5, 6, 7, 8, 9)
    return generatePermutations(phaseSettings)
            .map { phaseSetting -> calculateThrustSignalInCircularConfiguration(byteCode, phaseSetting) }
            .max()!!

}

fun calculateThrustSignalInCircularConfiguration(byteCode: List<Value>, phaseSetting: List<Int>): Value {
    return CircularAmplificationCircuit(byteCode, phaseSetting.map { it.toLong() }).calculateAmplification()

}

fun main() {
    println(findMaxThrusterSignalInCircularConfiguration(AMPLIFIER_CONTROLLER_SOFTWARE))
}
