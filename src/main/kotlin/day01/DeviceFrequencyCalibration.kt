package day01

class DeviceFrequencyCalibration {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(calibate(DAY_1_INPUT))
        }
    }

}

fun calibate(input: String) : Int {

    return input.trimIndent().lines()
            .map { it.toInt() }
            .sum()
}


