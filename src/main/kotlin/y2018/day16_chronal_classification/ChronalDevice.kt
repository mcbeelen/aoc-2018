package y2018.day16_chronal_classification

import y2018.day16_chronal_classification.OpCode.*
import java.util.Arrays




fun doesSampleBehaveLike(opcode: OpCode, sample: Sample): Boolean {

    return when (opcode) {
        ADDI -> validate(sample, performAddImmediate(sample.instruction, sample.before))
        ADDR -> validate(sample, performAddRegisters(sample.instruction, sample.before))

        MULI -> validate(sample, performMultiplyImmediate(sample.instruction, sample.before))
        MULR -> validate(sample, performMultiplyRegisters(sample.instruction, sample.before))

        SETI -> validate(sample, performSetImmediate(sample.instruction, sample.before))
        SETR -> validate(sample, performSetRegister(sample.instruction, sample.before))

        BANI -> validate(sample, performBitwiseAndImmediate(sample.instruction, sample.before))
        BANR -> validate(sample, performBitwiseAndRegister(sample.instruction, sample.before))

        BORI -> validate(sample, performBitwiseOrImmediate(sample.instruction, sample.before))
        BORR -> validate(sample, performBitwiseOrRegister(sample.instruction, sample.before))

        GTIR -> validate(sample, performGreaterThanImmediateRegister(sample.instruction, sample.before))
        GRRI -> validate(sample, performGreaterThanRegisterImmediate(sample.instruction, sample.before))
        GTRR -> validate(sample, performGreaterThanRegisterRegister(sample.instruction, sample.before))

        EQIR -> validate(sample, performEqualImmediateRegister(sample.instruction, sample.before))
        EQRI -> validate(sample, performEqualRegisterImmediate(sample.instruction, sample.before))
        EQRR -> validate(sample, performEqualRegisterRegister(sample.instruction, sample.before))


        NOOP -> true
    }




}

private fun validate(sample: Sample, actual: IntArray) = validate(sample.after, actual)

fun validate(expected: IntArray, actual: IntArray) : Boolean {
    return Arrays.equals(expected, actual)
}




class ChronalDeviceSolver {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val samples = readDeviceSamples(CHRONAL_DEVICE_RECORDED_SAMPLES)

            val  numberOfRecordedSampleBehavingLikeThreeOrMoreOpCodes = samples
                    .filter { behavesLikeThreeOrMoreOpCodes(it) }
                    .count()

            println("${numberOfRecordedSampleBehavingLikeThreeOrMoreOpCodes}")

        }



        private fun behavesLikeThreeOrMoreOpCodes(sample: Sample) = OpCode.values()
                    .filter { doesSampleBehaveLike(it, sample) }
                    .count() >= 3

    }
}



/**
class ChronalDeviceOpCodeResolver {


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val opCodeResolver : MutableMap<Int, Set<OpCode>> = HashMap()
            (0 .. 15).forEach { opCodeResolver[it] = OpCode.values().toSet() }

            OpCode.values()
                    .filter { it.instruction != Int.MIN_VALUE }
                    .forEach {
                        opCodeResolver[it.instruction] = setOf(it)
                        (0 .. 15).forEach { index ->
                            if (index != it.instruction) {
                                opCodeResolver[index] = opCodeResolver[index]!!.minus(it)
                            }
                        }
                    }


            val samples = readDeviceSamples(CHRONAL_DEVICE_RECORDED_SAMPLES)

            samples.forEach { sample ->
                val possibleOpCodes = possibleOpCodeForInstruction(sample)
                val opCodeIndex = sample.instruction[0]
                opCodeResolver[opCodeIndex] = opCodeResolver[opCodeIndex]!!.intersect(possibleOpCodes)
            }

            (0 .. 15).forEach {
                println("Instruction ${it} should maps to one of ${opCodeResolver[it]}")
            }


        }

        private fun possibleOpCodeForInstruction(sample: Sample) =
            values()
                    .filter { opCode -> doesSampleBehaveLike(opCode, sample) }
                    .toSet()
    }


}
*/