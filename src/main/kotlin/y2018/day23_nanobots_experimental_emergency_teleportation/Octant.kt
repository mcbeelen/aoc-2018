package y2018.day23_nanobots_experimental_emergency_teleportation

import util.space.Cube
import util.space.ORIGIN
import util.space.Point

fun IntRange.split() : Iterable<IntRange> {

    if (this.last == this.first) {
        return listOf(this)
    } else {
        if (last - first > 1) {
            val splitAt = (last - first) / 2 + first
            return listOf(first .. splitAt, splitAt .. last)
        } else {
            return listOf(first .. first, last..last)
        }



    }


}

data class Octant(val cube: Cube, val numberOfNanobotsInRange: Int) {
    fun divideIntoCubes(): Iterable<Cube> {

        println("Splitting Octant")

        val cubes : MutableList<Cube> = ArrayList()

        val xRanges = cube.xRange.split()
        val yRanges = cube.yRange.split()
        val zRanges = cube.zRange.split()
        xRanges.forEach { xRange ->
            yRanges.forEach { yRange ->
                zRanges.forEach { zRange ->
                    val from = Point(xRange.first, yRange.first, zRange.first)
                    val to =  Point(xRange.last, yRange.last, zRange.last)
                    cubes.add(Cube(from, to))
                }
            }
        }
        return cubes

    }

}


class OctantComparator : Comparator<Octant> {
    override fun compare(first: Octant, other: Octant): Int {


        if (first.numberOfNanobotsInRange != other.numberOfNanobotsInRange) {
            return other.numberOfNanobotsInRange - first.numberOfNanobotsInRange
        }
        println("Comparing $first to $other")

        if (first.cube.size() != other.cube.size()) {
            return first.cube.size().compareTo(other.cube.size())
        }

        return first.cube.shortestDistanceTo(ORIGIN).compareTo(other.cube.shortestDistanceTo(ORIGIN))

    }


}