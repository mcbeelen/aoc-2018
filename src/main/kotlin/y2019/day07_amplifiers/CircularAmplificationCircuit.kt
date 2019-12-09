package y2019.day07_amplifiers

import util.collections.generatePermutations
import y2019.computer.Buffer
import y2019.computer.BufferWithMemory
import y2019.computer.IntcodeComputer
import y2019.computer.compile

class CircularAmplificationCircuit(byteCode: List<Int>, phaseSetting: List<Int>) {

    // constructor(phaseSetting) : this(byteCode = compile(AMPLIFIER_CONTROLLER_SOFTWARE), phaseSetting = phaseSetting)

    private val DEFAULT_INPUT: Int = 0
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

    private fun configureInitialPhaseSettingsInAmplifiers(phaseSetting: List<Int>) {
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


    fun calculateAmplification(): Int {

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


fun findMaxThrusterSignalInCircularConfiguration(sourceCode: String): Int {

    val byteCode = compile(sourceCode)

    val phaseSettings = listOf(5, 6, 7, 8, 9)
    return generatePermutations(phaseSettings)
            .map { phaseSetting -> calculateThrustSignalInCircularConfiguration(byteCode, phaseSetting) }
            .max()!!

}

fun calculateThrustSignalInCircularConfiguration(byteCode: List<Int>, phaseSetting: List<Int>): Int {
    return CircularAmplificationCircuit(byteCode, phaseSetting).calculateAmplification()

}

fun main() {
    println(findMaxThrusterSignalInCircularConfiguration(AMPLIFIER_CONTROLLER_SOFTWARE))
}
