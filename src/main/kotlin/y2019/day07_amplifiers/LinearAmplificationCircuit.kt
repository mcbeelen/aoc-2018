package y2019.day07_amplifiers

import util.collections.generatePermutations
import y2019.computer.Buffer
import y2019.computer.IntcodeComputer
import y2019.computer.compile

class LinearAmplificationCircuit(
        val byteCode: List<Int> = compile(AMPLIFIER_CONTROLLER_SOFTWARE),
        private val phaseSetting: List<Int>) {

    private val DEFAULT_INPUT: Int = 0
    private val initialInput = Buffer()
    private val bufferAB = Buffer()
    private val bufferBC = Buffer()
    private val bufferCD = Buffer()
    private val bufferDE = Buffer()
    private val output = Buffer()


    private var amplifiersA = IntcodeComputer(
            input = initialInput,
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
            output = output,
            byteCode = byteCode)


    init {
        initialInput.write(phaseSetting[0])
        bufferAB.write(phaseSetting[1])
        bufferBC.write(phaseSetting[2])
        bufferCD.write(phaseSetting[3])
        bufferDE.write(phaseSetting[4])
        initialInput.write(DEFAULT_INPUT)

        amplifiersA.tick()
        amplifiersB.tick()
        amplifiersC.tick()
        amplifiersD.tick()
        amplifiersE.tick()

    }


    fun calculateAmplification(): Int {

        while (bufferAB.isEmpty()) {
            amplifiersA.tick()
        }

        while (bufferBC.isEmpty()) {
            amplifiersB.tick()
        }

        while (bufferCD.isEmpty()) {
            amplifiersC.tick()
        }

        while (bufferDE.isEmpty()) {
            amplifiersD.tick()
        }
        while (output.isEmpty()) {
            amplifiersE.tick()
        }
        return output.read()
    }

}


fun findMaxThrustSignalInLinearConfiguration(program: List<Int>): Int {
    val phaseSettings = listOf(0, 1, 2, 3, 4)
    return generatePermutations(phaseSettings)
            .map { phaseSetting -> calculateThrustSignalInLinearConfiguration(program, phaseSetting) }
            .max()!!
}

fun calculateThrustSignalInLinearConfiguration(program: List<Int>, phaseSetting: List<Int>): Int {

    val circuit = LinearAmplificationCircuit(program, phaseSetting)
    return circuit.calculateAmplification()

}


fun main() {
    println(findMaxThrustSignalInLinearConfiguration(compile(AMPLIFIER_CONTROLLER_SOFTWARE)))
}

internal const val AMPLIFIER_CONTROLLER_SOFTWARE = "3,8,1001,8,10,8,105,1,0,0,21,34,59,68,89,102,183,264,345,426,99999,3,9,102,5,9,9,1001,9,5,9,4,9,99,3,9,101,3,9,9,1002,9,5,9,101,5,9,9,1002,9,3,9,1001,9,5,9,4,9,99,3,9,101,5,9,9,4,9,99,3,9,102,4,9,9,101,3,9,9,102,5,9,9,101,4,9,9,4,9,99,3,9,1002,9,5,9,1001,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,99"