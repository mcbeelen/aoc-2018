package y2019.computer

import kotlin.system.exitProcess


interface Instruction {
    fun handle(parameters: List<Int>): Effect
    fun numberOfParameters(): Int = 0
}

class AddInstruction() : Instruction {
    override fun handle(parameters: List<Int>): Effect {

        val first = parameters[0]
        val second = parameters[1]
        val address = parameters[2]



        return WriteToMemoryEffect(address, first + second)
    }

    override fun numberOfParameters() = 3

}

class MultiplyInstruction() : Instruction {
    override fun handle(parameters: List<Int>): Effect {

        val first = parameters[0]
        val second = parameters[1]
        val address = parameters[2]
        return WriteToMemoryEffect(address, first * second)
    }

    override fun numberOfParameters() = 3
}


class ReadInputInstruction(private val input: Input) : Instruction {
    override fun handle(parameters: List<Int>) = WriteToMemoryEffect(parameters[0], input.read())

    override fun numberOfParameters() = 1
}

class WriteToOutputInstruction(private val intCode: List<Int>) : Instruction {
    override fun handle(parameters: List<Int>): Effect {
        println("Test: ${parameters[0]}")
        return NoOpEffect()
    }

    override fun numberOfParameters() = 1

}



class ExitInstruction : Instruction {
    override fun handle(parameters: List<Int>) = exitProcess(0)
}

class UnknownOpcodeInstruction(val currentInstruction: Int, val opcode: Int) : Instruction {
    override fun handle(parameters: List<Int>) : Effect {
        System.err.println("$opcode from $currentInstruction")
        return NoOpEffect()
    }
}

