package y2019.day07_amplifiers

import y2019.computer.Buffer
import y2019.computer.IntcodeComputer
import y2019.computer.parseIntCode

class AmplificationCircuit(
        val program: List<Int> = parseIntCode(AMPLIFIER_CONTROLLER_SOFTWARE),
        val phaseSetting: List<Int>) {

    private val DEFAULT_INPUT: Int = 0
    private val initialInput = Buffer()
    private val bufferAB = Buffer()
    private val bufferBC = Buffer()
    private val bufferCD = Buffer()
    private val bufferDE = Buffer()
    private val output = Buffer()


    private var amplifiersA  = IntcodeComputer(
            input = initialInput,
            output = bufferAB,
            program = program)

    private var amplifiersB  = IntcodeComputer(
            input = bufferAB,
            output = bufferBC,
            program = program)

    private var amplifiersC  = IntcodeComputer(
            input = bufferBC,
            output = bufferCD,
            program = program)

    private var amplifiersD  = IntcodeComputer(
            input = bufferCD,
            output = bufferDE,
            program = program)


    private var amplifiersE  = IntcodeComputer(
            input = bufferDE,
            output = output,
            program = program)


    init {
        initialInput.write(phaseSetting[0])
        bufferAB.write(phaseSetting[1])
        bufferBC.write(phaseSetting[2])
        bufferCD.write(phaseSetting[3])
        bufferDE.write(phaseSetting[4])
        initialInput.write(DEFAULT_INPUT)

        amplifiersA = amplifiersA.tick()
        amplifiersB = amplifiersB.tick()
        amplifiersC = amplifiersC.tick()
        amplifiersD = amplifiersD.tick()
        amplifiersE = amplifiersE.tick()

    }


    fun calculateAmplification(): Int {

        while (bufferAB.isEmpty()) {
            amplifiersA = amplifiersA.tick()
        }

        while (bufferBC.isEmpty()) {
            amplifiersB = amplifiersB.tick()
        }

        while (bufferCD.isEmpty()) {
            amplifiersC = amplifiersC.tick()
        }

        while (bufferDE.isEmpty()) {
            amplifiersD = amplifiersD.tick()
        }
        while (output.isEmpty()) {
            amplifiersE = amplifiersE.tick()
        }
        return output.read()
    }

}


fun findMaxThrustSignal(program: List<Int>): Int {
    val phaseSettings = listOf(0, 1, 2, 3, 4)
    return generatePermutations(phaseSettings)
            .map { phaseSetting -> calculateThrustSignal(program, phaseSetting) }
            .max()!!
}

fun calculateThrustSignal(program: List<Int>, phaseSetting: List<Int>): Int {

    val circuit = AmplificationCircuit(program, phaseSetting)
    return circuit.calculateAmplification()

}


private const val AMPLIFIER_CONTROLLER_SOFTWARE = "3,8,1001,8,10,8,105,1,0,0,21,34,59,68,89,102,183,264,345,426,99999,3,9,102,5,9,9,1001,9,5,9,4,9,99,3,9,101,3,9,9,1002,9,5,9,101,5,9,9,1002,9,3,9,1001,9,5,9,4,9,99,3,9,101,5,9,9,4,9,99,3,9,102,4,9,9,101,3,9,9,102,5,9,9,101,4,9,9,4,9,99,3,9,1002,9,5,9,1001,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,99"