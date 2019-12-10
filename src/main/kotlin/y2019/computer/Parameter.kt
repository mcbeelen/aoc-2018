package y2019.computer

import y2019.computer.ParameterMode.IMMEDIATE
import y2019.computer.ParameterMode.POSITION
import y2019.computer.ParameterMode.RELATIVE

enum class ParameterMode {

    POSITION,
    IMMEDIATE,
    RELATIVE

}

fun toParameterMode(code: Char) = if ('0' == code) POSITION else if ('1' == code) IMMEDIATE else RELATIVE

data class Parameter(val value: Value, val mode: ParameterMode = POSITION)