package y2019.computer


data class State(val memory: Memory, val instructionPointer: Int = 0, val relativeBase: Int = 0) {

    constructor(sourceCode: String) : this(memory = Memory(sourceCode))
    constructor(sourceCode: String, instructionPointer: Int) : this(memory = Memory(sourceCode), instructionPointer = instructionPointer)


    fun readFromMemory(address: Int): Int {
        if (address < 0) {
            throw IllegalArgumentException("Address $address can NOT be read from")
        }
        return memory.read(address)
    }

    fun fetch() = readFromMemory(instructionPointer)

    fun applyEffects(effect: Effect, instruction: Instruction): State {
        return update(effect).jump(effect, instruction)
    }

    private fun update(effect: Effect) = if (effect is Update) applyUpdate(effect) else this

    private fun applyUpdate(update: Update) = updateMemory(update.getModification())

    private fun updateMemory(modification: Modification): State = this.copy(
            memory = memory.updateMemory(modification)
    )

    private fun jump(effect: Effect, instruction: Instruction) = this.copy(
            instructionPointer = effect.goto(instructionPointer, instruction.numberOfParameters())
    )

    fun readRelativeParam(index: Int): Int {
        val offset = readFromMemory(instructionPointer + 1 + index)
        return readFromMemory(relativeBase + offset)
    }

}

fun convertToMemoryMap(byteCode: List<Int>): Map<Int, Int> {
    val map: MutableMap<Int, Int> = HashMap()
    byteCode.forEachIndexed { index, it -> map.put(index, it) }
    return map
}


data class Memory(private val program: Map<Address, Value>) {

    constructor(byteCode: List<Value>) : this(program = convertToMemoryMap(byteCode))


    constructor(sourceCode: String) : this(byteCode = compile(sourceCode))

    fun updateMemory(modification: Modification) = Memory(program + modification)

    fun read(address: Int): Int = program.getValue(address)
}
