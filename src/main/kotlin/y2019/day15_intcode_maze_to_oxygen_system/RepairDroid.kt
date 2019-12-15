package y2019.day15_intcode_maze_to_oxygen_system

import util.grid.GridWalker
import y2019.computer.ConstantInput
import y2019.computer.Input
import y2019.computer.IntcodeComputer
import y2019.computer.Output
import y2019.computer.Value

class RepairDroid : GridWalker(), Input, Output {
    override fun read(): Value {
        return 1
    }

    override fun write(value: Value) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

fun main() {
    letDroidExploreToOxygenSystem()
}

fun letDroidExploreToOxygenSystem() {

    val droid = RepairDroid()
    val boardComputer = IntcodeComputer(input = droid, output = droid, sourceCode = REPAIR_DROID_REMOTE_CONTROL_PROGRAM)
    boardComputer.runProgram()

}

