package y2019.computer

import y2019.computer.ParameterMode.IMMEDIATE
import y2019.computer.ParameterMode.POSITION

enum class ParameterMode {

    POSITION,
    IMMEDIATE

}

fun toParameterMode(code: Char) = if ('0' == code) POSITION else IMMEDIATE


data class Parameter(val value: Int, val mode: ParameterMode = POSITION)