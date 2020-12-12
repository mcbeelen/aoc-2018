package y2020.d12

import util.grid.GridWalker
import util.grid.Vector

class `Actual Ship Navigation System` {

    var waypointOffset = Vector(10, -1)
    val ship = GridWalker()

    /**
     Action N means to move the waypoint north by the given value.
     Action S means to move the waypoint south by the given value.
     Action E means to move the waypoint east by the given value.
     Action W means to move the waypoint west by the given value.
     Action L means to rotate the waypoint around the ship left (counter-clockwise) the given number of degrees.
     Action R means to rotate the waypoint around the ship right (clockwise) the given number of degrees.
     Action F means to move forward to the waypoint a number of times equal to the given value.
     */
    fun processInstructions(instructions: String) {
        instructions.lines().forEach {
            processInstruction(it)
        }
    }

    private fun processInstruction(instruction: String) {
        when {
            vectorPrefixes.contains(instruction[0]) -> moveWaypoint(instruction)
            turnPrefixes.contains(instruction[0]) -> rotateWaypoint(instruction)
            else -> moveShip(instruction)
        }
    }

    private fun moveWaypoint(instruction: String) {
        waypointOffset += instruction.toVector()
    }

    private fun rotateWaypoint(instruction: String) {
        when {
            isFlip(instruction) -> waypointOffset *= -1
            representsClockwiseRotation(instruction) -> waypointOffset = waypointOffset.rotateClockwise()
            representsCounterClockwiseRotation(instruction) -> waypointOffset = waypointOffset.rotateCounterClockwise()
        }
    }

    private fun representsClockwiseRotation(instruction: String) = "R90" == instruction || "L270" == instruction
    private fun representsCounterClockwiseRotation(instruction: String) = "L90" == instruction || "R270" == instruction

    private fun isFlip(instruction: String)=  instruction.trim().endsWith("180")

    private fun moveShip(instruction: String) {
        ship.move(waypointOffset.times(instruction.toDistance()))
    }
}
