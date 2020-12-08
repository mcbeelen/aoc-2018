package y2020.d08

import kotlin.system.measureTimeMillis

class HandheldGamingConsole(instructions: String) {

    var instructionPointer: Int = 0
    var accumulator: Int = 0

    var processedInstuctions : MutableSet<Int> = HashSet()

    val bytecode : MutableList<String> = instructions.lines().toMutableList()


    fun valueOfAccumulatorBeforeFirstRepeatedInstruction(): Int {

        return accumulator
    }

    private fun execute(instruction: String) {
        when {
            instruction.startsWith("acc") -> Acc(instruction)
            instruction.startsWith("jmp") -> Jmp(instruction)
            instruction.startsWith("nop") -> NoOp()
        }
    }

    private fun Acc(instruction: String) {
        accumulator += instruction.getParameter().asOffset()
        instructionPointer++
    }

    private fun Jmp(instruction: String) {
        instructionPointer += instruction.getParameter().asOffset()
    }

    private fun NoOp() {
        instructionPointer++
    }

    fun executeBootCode(): Int {
        while (notReachedStopCondition()) {
            processedInstuctions.add(instructionPointer)
            val instruction = bytecode[instructionPointer]
            execute(instruction)
        }
        if (instructionPointer == bytecode.size) {
            return 0
        } else {
            return -1
        }

    }

    private fun notReachedStopCondition() = !processedInstuctions.contains(instructionPointer) && instructionPointer < bytecode.size

    fun valueOfTheAccumulator() = accumulator
    fun patchLine(instructionToPatch: Int) {
        bytecode[instructionToPatch] = flipNopJmpInstruction(instructionToPatch)

    }

    private fun flipNopJmpInstruction(instructionToPatch: Int): String {
        val buggedLineOfCode = bytecode[instructionToPatch]
        return when {
            buggedLineOfCode.startsWith("nop") -> "jmp" + buggedLineOfCode.substring(3)
            buggedLineOfCode.startsWith("jmp") -> "nop" + buggedLineOfCode.substring(3)
            else -> buggedLineOfCode
        }
    }

}

private fun String.asOffset(): Int {
    val offset = this.substring(1).toInt()
    return when (this[0]) {
        '-' -> offset.unaryMinus()
        else -> offset
    }
}

private fun String.getParameter(): String {
    return this.substringAfter(" ")
}

fun main() {
    var exitedNormally = false
    var counter = 0

    val millis = measureTimeMillis {
        while (!exitedNormally) {
            counter++
            val console = HandheldGamingConsole(y2020d08bootstrapCode)
            console.patchLine(console.bytecode.size - counter)
            val exitCode = console.executeBootCode()
            exitedNormally = exitCode == 0
            if (exitedNormally) {
                println("Accumulator: ${console.valueOfTheAccumulator()}")
            }
        }
    }
    println("${millis} ms")
}
