package y2019.computer


data class State(val memory: Memory, val instructionPointer: Int = 0, val relativeBase: Long = 0) {

    constructor(sourceCode: String) : this(memory = Memory(sourceCode))
    constructor(sourceCode: String, instructionPointer: Int) : this(memory = Memory(sourceCode), instructionPointer = instructionPointer)


    fun readFromMemory(address: Int): Value {
        if (address < 0) {
            throw IllegalArgumentException("Address $address can NOT be read from")
        }
        return memory.read(address)
    }

    fun fetch() = readFromMemory(instructionPointer)

    fun applyEffects(effect: Effect, instruction: Instruction): State {
        return update(effect).jump(effect, instruction).shift(effect)
    }


    private fun update(effect: Effect) = if (effect is Update) applyUpdate(effect) else this

    private fun applyUpdate(update: Update) = updateMemory(update.getModification())

    private fun updateMemory(modification: Modification): State = this.copy(
            memory = memory.updateMemory(modification)
    )

    private fun jump(effect: Effect, instruction: Instruction) = this.copy(
            instructionPointer = effect.goto(instructionPointer, instruction.numberOfParameters())
    )

    private fun shift(effect: Effect) = if (effect is RelativeBaseAdjustment) applyAdjustment(effect) else this

    private fun applyAdjustment(relativeBaseAdjustment: RelativeBaseAdjustment) = this.copy(
            relativeBase = relativeBase + relativeBaseAdjustment.getAdjustment())


    fun readRelativeParam(index: Int): Value {
        val offset = readFromMemory(instructionPointer + 1 + index)
        return readFromMemory((offset + relativeBase).toInt())
    }

}

fun convertToMemoryMap(byteCode: List<Value>): Map<Address, Value> {
    val map: MutableMap<Address, Value> = HashMap()
    byteCode.forEachIndexed { index, it -> map.put(index, it) }
    return map
}


data class Memory(private val program: Map<Address, Value>) {

    constructor(byteCode: List<Value>) : this(program = convertToMemoryMap(byteCode))


    constructor(sourceCode: String) : this(byteCode = compile(sourceCode))

    fun updateMemory(modification: Modification) = Memory(program + modification)

    fun read(address: Address): Value = program.getOrDefault(address, 0L)
}
