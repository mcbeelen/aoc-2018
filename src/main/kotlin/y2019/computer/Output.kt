package y2019.computer

interface Output {
    fun print(value: Int)
}

class WriteToSystemOutOutput : Output {
    override fun print(value: Int) {
        println(value)
    }
}

class LastPrintedReadableOutput : Output {
    var lastPrintedValue : Int = Int.MIN_VALUE
    override fun print(value: Int) {
        lastPrintedValue = value
    }


}