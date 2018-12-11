package day11_chronal_charge_power_grid

import util.grid.ScreenCoordinate
import kotlin.system.measureTimeMillis

class PowerGrid(private val gridSerialNumber: Int) {


    private val fuelCellsInGrid: Map<ScreenCoordinate, FuelCell> by lazy {
        calculatePowerLevelsForGrid()
    }


    fun powerLevelAt(screenCoordinate: ScreenCoordinate): Int = fuelCellsInGrid[screenCoordinate]!!.powerLevel



    fun findTopLevelCornerOfMostPowerfulSquareOfAnySize(): Pair<ScreenCoordinate, Int> {

        val sizeSquareMap : MutableMap<Pair<ScreenCoordinate, Int>, Int> = HashMap()

        (0 until 300)
                .forEach { size ->
                    val (screenCoordinate, totalPowerLevel) = findTopLevelCornerOfMostPowerfulSquare(size)
                    println("With size ${size} the most powerful Square would be at ${screenCoordinate} with total power of: ${totalPowerLevel}")
                    sizeSquareMap[Pair(screenCoordinate, size)] = totalPowerLevel
                }

        return sizeSquareMap.maxBy { it.value }!!.key

    }


    fun findTopLevelCornerOfMostPowerfulSquare(squareSize: Int) : Pair<ScreenCoordinate, Int> {
        val squares: MutableMap<ScreenCoordinate, Int> = HashMap()

        val dimension = squareSize - 1

        val i = 300 - dimension
        for (x in 1..i) {
            for (y in 1..i) {
                squares[ScreenCoordinate(x, y)] = sumPowerLevels(x, y, dimension)
            }
        }

        return squares.maxBy { it.value }!!.toPair()


    }

    private fun sumPowerLevels(x: Int, y: Int, squareSize: Int): Int {

        var sum = 0

        for (xOffset in 0..squareSize) {
            for (yOffset in 0..squareSize) {

                sum += fuelCellsInGrid[ScreenCoordinate(x + xOffset, y + yOffset)]!!.powerLevel

            }
        }

        return sum

    }

    private fun calculatePowerLevelsForGrid(): Map<ScreenCoordinate, FuelCell> {
        val grid: MutableMap<ScreenCoordinate, FuelCell> = HashMap()

        for (x in 0..300) {
            for (y in 0..300) {
                grid[ScreenCoordinate(x, y)] = FuelCell(gridSerialNumber, x, y)
            }
        }

        return grid
    }


}


class ChronalChargeSolver {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {

            val time = measureTimeMillis {
                val grid = PowerGrid(8561)
                val findTopLevelCornerOfMostPowerfulSquare = grid.findTopLevelCornerOfMostPowerfulSquare(2)
                println("Most powerful square is located at $findTopLevelCornerOfMostPowerfulSquare")


            }
            println("Solved part one in ${time}ms")


            val timeForTwo = measureTimeMillis {
                val grid = PowerGrid(8561)
                val findTopLevelCornerOfMostPowerfulSquare = grid.findTopLevelCornerOfMostPowerfulSquareOfAnySize()
                println("Most powerful square is located at ${findTopLevelCornerOfMostPowerfulSquare.first} with power: ${findTopLevelCornerOfMostPowerfulSquare.second}")


            }
            println("Solved part one in ${timeForTwo}ms")


        }

    } //236,146,12: 160
}





