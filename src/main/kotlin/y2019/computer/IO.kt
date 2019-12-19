package y2019.computer

import java.util.LinkedList
import java.util.Queue
import kotlin.Long.Companion.MIN_VALUE

interface Input {
    fun read() : Value
}

open class ConstantInput(private val value : Value) : Input {
    constructor(int: Int) : this(int.toLong())
    override fun read() = value

    override fun toString() = "-> $value"

}

class AlwaysZeroInput : ConstantInput(0L)

class SequenceInput(private val inputValues: List<Value>) : Input {
    constructor(vararg inputValues: Int) : this(inputValues.map { it.toLong() })

    private var index = 0

    override fun read(): Value {
        return inputValues[index++]
    }


}





interface Output {
    fun write(value: Value)
}

class WriteToSystemOutOutput : Output {
    override fun write(value: Value) {
        println(value)
    }
}

class LastPrintedReadableOutput : Output {
    var lastPrintedValue : Value = MIN_VALUE
    override fun write(value: Value) {
        lastPrintedValue = value
    }
}


open class Buffer : Input, Output {

    private val storage : Queue<Value> = LinkedList()

    override fun read(): Value = storage.poll()

    override fun write(value: Value) {
        storage.offer(value)
    }

    fun isEmpty() = storage.isEmpty()

}

class BufferWithMemory : Buffer() {
    var lastPrintedValue : Value = MIN_VALUE
    override fun write(value: Value) {
        lastPrintedValue = value
        super.write(value)
    }
}