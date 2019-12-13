package y2019.day12_moons_of_jupiter


import util.math.lcm
import util.space.Point
import kotlin.math.absoluteValue

data class Velocity(val x: Int, val y: Int, val z: Int)

val AT_REST = Velocity(0, 0, 0)

data class Moon(val currentPosition: Point, val currentVelocity: Velocity) {

    constructor(x: Int, y: Int, z: Int) : this(Point(x, y, z), AT_REST)

    fun withVelocityAdjustedByGrafity(moons: List<Moon>): Moon {
        val newX = currentVelocity.x + moons.map { it.currentPosition.x.compareTo(currentPosition.x) }.sum()
        val newY = currentVelocity.y + moons.map { it.currentPosition.y.compareTo(currentPosition.y) }.sum()
        val newZ = currentVelocity.z + moons.map { it.currentPosition.z.compareTo(currentPosition.z) }.sum()
        val newVelocity = Velocity(newX, newY, newZ)
        return this.copy(currentVelocity = newVelocity)

    }

    fun withPositionAdjustedByVelocity(): Moon {

        val newX = currentPosition.x + currentVelocity.x
        val newY = currentPosition.y + currentVelocity.y
        val newZ = currentPosition.z + currentVelocity.z

        return this.copy(currentPosition = Point(newX, newY, newZ))
    }

    fun calculateTotalEnergy(): Int {
        return calculatePotentialEnergy() * calculateKineticEnergy()

    }

    private fun calculatePotentialEnergy() = currentPosition.let { it.x.absoluteValue + it.y.absoluteValue + it.z.absoluteValue }

    private fun calculateKineticEnergy() = currentVelocity.let { it.x.absoluteValue + it.y.absoluteValue + it.z.absoluteValue }
}


fun calculatePosition(scan: String): Moon {
    val split = scan.drop(1).dropLast(1).split(',')
    val x = readValue(split, 0)
    val y = readValue(split, 1)
    val z = readValue(split, 2)

    return Moon(x, y, z)

}

private fun readValue(split: List<String>, i: Int) =
        split[i].substringAfterLast('=').toInt()


fun applyGravity(moons: List<Moon>) = moons.map { applyGravity(it, moons) }

fun applyGravity(moon: Moon, moons: List<Moon>) = moon.withVelocityAdjustedByGrafity(moons)

fun applyVelocity(moons: List<Moon>) = moons.map { it.withPositionAdjustedByVelocity() }


data class Orbit(val moons: List<Moon>) {
    fun processStep() = applyGravity().applyVelocity()

    private fun applyGravity() = Orbit(applyGravity(moons))
    private fun applyVelocity() = Orbit(applyVelocity(moons))

    fun calculateTotalAmountOfEnergy() = moons.map { it.calculateTotalEnergy() }.sum()
    fun getStateOfX(): List<Pair<Int, Int>> = moons.map { Pair(it.currentPosition.x, it.currentVelocity.x) }
    fun getStateOfY(): List<Pair<Int, Int>> = moons.map { Pair(it.currentPosition.y, it.currentVelocity.y) }
    fun getStateOfZ(): List<Pair<Int, Int>> = moons.map { Pair(it.currentPosition.z, it.currentVelocity.z) }


}

fun readOrbit(orbitScans: String): Orbit {
    val moonScans = orbitScans.trimIndent().lines()
    val initialMoons = moonScans.map { calculatePosition(it) }
    return Orbit(initialMoons)
}


fun totalAmountOfEnergyAfterSteps(orbitScans: String, numberOfSteps: Int): Int {
    var orbit = readOrbit(orbitScans);
    for (i in 0 until numberOfSteps) {
        orbit = orbit.processStep()
        plotX(orbit)
    }
    return orbit.calculateTotalAmountOfEnergy()
}

fun numberOfStepToReachIdenticalState(orbitScans: String): Long {
    val initialOrbit = readOrbit(orbitScans)
    var adjustedOrbit = initialOrbit
    var stepCounter = 0L
    var xNotRepeatedYet = true
    var yNotRepeatedYet = true
    var zNotRepeatedYet = true
    var stepsNeededToRepeatX = 0L
    var stepsNeededToRepeatY = 0L
    var stepsNeededToRepeatZ = 0L

    val initialStateForX = initialOrbit.getStateOfX()
    val initialStateForY = initialOrbit.getStateOfY()
    val initialStateForZ = initialOrbit.getStateOfZ()

    while (xNotRepeatedYet || yNotRepeatedYet || zNotRepeatedYet) {
        adjustedOrbit = adjustedOrbit.processStep()
        stepCounter++
        if (xNotRepeatedYet && adjustedOrbit.getStateOfX() == initialStateForX) {
            println("X Repeated after $stepCounter")
            stepsNeededToRepeatX = stepCounter
            xNotRepeatedYet = false
        }
        if (yNotRepeatedYet && initialStateForY == adjustedOrbit.getStateOfY()) {
            println("Y Repeated after $stepCounter")
            stepsNeededToRepeatY = stepCounter
            yNotRepeatedYet = false
        }
        if (zNotRepeatedYet && initialStateForZ == adjustedOrbit.getStateOfZ()) {
            println("Z Repeated after $stepCounter ")
            stepsNeededToRepeatZ = stepCounter
            zNotRepeatedYet = false
        }
    }

    return lcm(stepsNeededToRepeatX, stepsNeededToRepeatY, stepsNeededToRepeatZ)

}


fun plotX(orbit: Orbit) {
    val moonsToCurrentX: List<Pair<Int, Int>> = orbit.moons.mapIndexed { index, moon -> Pair(index, moon.currentPosition.x) }
    val groupedMoons = moonsToCurrentX.groupBy { it.second }
    for (i in -50..50) {
        if (groupedMoons.containsKey(i)) {
            val moonsAtI = groupedMoons.getValue(i)
            if (moonsAtI.size == 1) {
                print(moonsAtI[0].first)
            } else {
                print('X')
            }
        } else {
            print(' ')
        }
    }
    println()
}


fun main() {
    println(totalAmountOfEnergyAfterSteps(JUPITER_MOONS, 1000))
    println("Part two: " + numberOfStepToReachIdenticalState(JUPITER_MOONS))
}


internal const val JUPITER_MOONS = """
<x=-16, y=15, z=-9>
<x=-14, y=5, z=4>
<x=2, y=0, z=6>
<x=-3, y=18, z=9>
"""
