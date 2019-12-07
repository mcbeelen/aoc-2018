package y2019.computer

import java.util.LinkedList
import java.util.Queue

interface Input {
    fun read() : Int
}

open class ConstantInput(private val value : Int) : Input {
    override fun read() = value

    override fun toString() = "-> $value"

}

class AlwaysZeroInput : ConstantInput(0)




interface Output {
    fun write(value: Int)
}

class WriteToSystemOutOutput : Output {
    override fun write(value: Int) {
        println(value)
    }
}

class LastPrintedReadableOutput : Output {
    var lastPrintedValue : Int = Int.MIN_VALUE
    override fun write(value: Int) {
        lastPrintedValue = value
    }
}


open class Buffer : Input, Output {

    private val storage : Queue<Int> = LinkedList()

    override fun read() = storage.poll()

    override fun write(value: Int) {
        storage.offer(value)
    }

    fun isEmpty() = storage.isEmpty()

}

class BufferWithMemory : Buffer() {
    var lastPrintedValue : Int = Int.MIN_VALUE
    override fun write(value: Int) {
        lastPrintedValue = value
        super.write(value)
    }
}