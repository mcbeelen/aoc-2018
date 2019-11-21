package y2018.day19_chrono_go_with_the_flow

import y2018.day16_chronal_classification.performOperation

class Executable(val program: Program, val breakpoints: Map<Int, Debugger> = emptyMap()) {

    var registers = intArrayOf(0, 0, 0, 0, 0, 0)
    var instuctionPointer : Int = 0


    fun execute() {
        while (instuctionPointer in 0 until program.instructions.size) {

            if (breakpoints.keys.contains(instuctionPointer)) {
                breakpoints.getValue(instuctionPointer).debug(program, instuctionPointer, registers)
            }

            executeInstruction()
        }
    }

    private fun executeInstruction() {

        registers[program.boundRegister] = instuctionPointer
        registers = performOperation(program.instructions[instuctionPointer], registers)
        instuctionPointer = registers[program.boundRegister]


        instuctionPointer++

    }

}

