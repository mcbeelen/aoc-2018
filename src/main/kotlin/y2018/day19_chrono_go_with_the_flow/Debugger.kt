package y2018.day19_chrono_go_with_the_flow

interface Debugger {
    fun debug(program: Program, instuctionPointer: Int, registers: IntArray)
}

 class SystemOutDebugger {

    fun debug(program: Program, instuctionPointer: Int, registers: IntArray) {
         print("${instuctionPointer.toString().padStart(3, ' ')}: [${program.instructions[instuctionPointer].opCode}]  ")
         println(registers.withIndex().map { "${it.index}: ${it.value.toString().padStart(3)}" }.joinToString())
    }

}