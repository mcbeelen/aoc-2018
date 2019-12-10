package y2019.computer

import java.math.BigInteger
import java.math.BigInteger.ZERO
import java.math.BigInteger.valueOf
import java.util.LinkedList
import java.util.Queue
import kotlin.Long.Companion.MIN_VALUE

interface Input {
    fun read() : Value
}

open class ConstantInput(private val value : Value) : Input {
    constructor(int: Int) : this(int.toBigInteger())
    override fun read() = value

    override fun toString() = "-> $value"

}

class AlwaysZeroInput : ConstantInput(ZERO)




interface Output {
    fun write(value: Value)
}

class WriteToSystemOutOutput : Output {
    override fun write(value: Value) {
        println(value)
    }
}

class LastPrintedReadableOutput : Output {
    var lastPrintedValue : Value = valueOf(MIN_VALUE)
    override fun write(value: Value) {
        lastPrintedValue = value
    }
}


open class Buffer : Input, Output {

    private val storage : Queue<Value> = LinkedList()

    override fun read() = storage.poll()

    override fun write(value: Value) {
        storage.offer(value)
    }

    fun isEmpty() = storage.isEmpty()

}

class BufferWithMemory : Buffer() {
    var lastPrintedValue : Value = MIN_VALUE.toBigInteger()
    override fun write(value: Value) {
        lastPrintedValue = value
        super.write(value)
    }
}