package util.math

import com.google.common.math.LongMath.gcd

fun lcm(first: Long, second: Long) = (first * second) / gcd(first, second)


fun lcm(first: Long, second: Long, third: Long): Long {
    val factors: MutableList<Long> = ArrayList()

    var numberOne = first
    var numberTwo = second
    var numberThree = third

    var candidate = 2L
    while (numberOne > 1 || numberTwo > 1 || numberThree > 1) {
        while (numberOne % candidate == 0L || numberTwo % candidate == 0L || numberThree % candidate == 0L) {
            factors.add(candidate)
            if (numberOne % candidate == 0L) { numberOne /= candidate }
            if (numberTwo % candidate == 0L) { numberTwo /= candidate }
            if (numberThree % candidate == 0L) { numberThree /= candidate }
        }
        candidate++
    }

    var lcm = 1L
    factors.forEach { lcm *= it }
    return lcm
}
