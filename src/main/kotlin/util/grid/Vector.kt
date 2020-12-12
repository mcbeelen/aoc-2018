package util.grid

import com.google.common.math.IntMath
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2

data class Vector(val left: Int = 0, val top: Int = 0) {
    fun asAngle(): Double {
        return when {
            (left == 0) -> if (top > 0) 180.0 else 0.0
            (top == 0) -> if (left < 0) 270.0 else 90.0
            else -> {
                val rad = angleInRad()
                val degree = rad * (180 / PI)
                when {
                    degree < -90 -> 90 + neg(degree)
                    -90 < degree && degree < 0 ->  180 - (90 + degree)
                    0.0 < degree && degree < 90 -> 90 - degree
                    90 < degree -> 450 - degree
                    else -> throw IllegalStateException()


                }
            }
        }
    }

    private fun neg(value: Double) = value * -1

    private fun angleInRad() = atan2(-1.0 * top, 1.0 * left)
    operator fun plus(delta: Vector): Vector {
        return Vector(left = left + delta.left, top = top + delta.top)
    }

    operator fun times(factor: Int): Vector {
        return Vector(left * factor, top * factor)

    }

    fun rotateClockwise(): Vector = Vector(top.unaryMinus(), left)
    fun rotateCounterClockwise() = Vector(top, left.unaryMinus())
}

tailrec fun simplify(vector: Vector): Vector {
    val left = vector.left
    val top = vector.top
    val absLeft = abs(left)
    val absTop = abs(top)
    return when (IntMath.gcd(absLeft, absTop)) {
        0 -> vector
        1 -> vector
        else -> simplify(Vector(left / IntMath.gcd(absLeft, absTop), top / IntMath.gcd(absLeft, absTop)))
    }
}
