package y2019.computer

interface Input {
    fun read() : Int
}

open class ConstantInput(private val value : Int) : Input {
    override fun read() = value
}

class AlwaysZeroInput : ConstantInput(0)


