package y2020.d14

import util.collections.Queue


fun `run initialization program`(input: String): Long {
    var mask = ""
    val memory : MutableMap<Int, Long> = HashMap()

    input.lines().forEach {
        when {
            it.startsWith("mask = ") -> mask = it.substring(7)
            else -> {
                val inputValue = it.substringAfter(" = ").toLong()
                val valueWrittenToMemory = applyMask(mask, inputValue)
                val memoryAddress = it.substringAfter("[").substringBefore("]").toInt()
                memory[memoryAddress] = valueWrittenToMemory
            }
        }
    }

    return memory.values.sum()
}

fun `run initialization program v2`(initializationProgram: String): Long {
    var mask = ""
    val memory : MutableMap<Long, Long> = HashMap()
    initializationProgram.lines().forEach {
        when {
            it.startsWith("mask = ") -> mask = it.substring(7)
            else -> {
                val inputValue = it.substringAfter(" = ").toLong()
                val memoryAddress = it.substringAfter("[").substringBefore("]").toLong()
                val addressesToWrite = applyMaskToAddress(mask, memoryAddress)
                addressesToWrite.forEach {
                    memory[it] = inputValue
                }
            }
        }
    }

    return memory.values.sum()
}


fun applyMask(mask: String, value: Long): Long {
    val bitvalue = value.toString(2)
    val allBits = bitvalue.padStart(36, padChar = '0')
    val maskedValue = allBits.mapIndexed { index, c -> if (mask[index] == 'X') c else mask[index] }.joinToString(separator = "")
    return maskedValue.toLong(2)
}

fun applyMaskToAddress(mask: String, inputAddress: Long) : List<Long> {
    val allBits = inputAddress.toString(2).padStart(36, padChar = '0')
    val maskedValue =
        allBits.mapIndexed { index, c -> if (mask[index] == '0') c else mask[index] }.joinToString(separator = "")
    return generateAddressWithZeroAndOneForeachX(maskedValue)
}

private fun generateAddressWithZeroAndOneForeachX(maskedValue: String): List<Long> {
    val generatedAddresses: Queue<String> = Queue()
    generatedAddresses.enqueue(maskedValue)

    while (generatedAddresses.peek()!!.contains('X')) {
        val addressWithX = generatedAddresses.dequeue()
        generatedAddresses.enqueue(addressWithX.replaceFirst('X', '0'))
        generatedAddresses.enqueue(addressWithX.replaceFirst('X', '1'))
    }
    return generatedAddresses.map { it.toLong(2) }
}


fun main() {
    println(`run initialization program`(y2020d14_docking_data_input))
    println(`run initialization program v2`(y2020d14_docking_data_input))
}
