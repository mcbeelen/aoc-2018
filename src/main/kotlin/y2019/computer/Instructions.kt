package y2019.computer


private const val ZERO = 0L
private const val ONE = 1L

interface Instruction {
    fun handle(parameters: List<Value>): Effect
    fun numberOfParameters(): Int = 0
}

class AddInstruction : Instruction {
    override fun handle(parameters: List<Value>): Effect {

        val first = parameters[0]
        val second = parameters[1]
        val address = parameters[2].toAddress()

        return WriteToMemoryEffect(address, first + second)
    }

    override fun numberOfParameters() = 3

}



class MultiplyInstruction : Instruction {
    override fun handle(parameters: List<Value>): Effect {

        val first = parameters[0]
        val second = parameters[1]
        val address = parameters[2].toAddress()
        return WriteToMemoryEffect(address, first * second)
    }

    override fun numberOfParameters() = 3
}


class ReadInputInstruction(private val input: Input) : Instruction {
    override fun handle(parameters: List<Value>) = WriteToMemoryEffect(parameters[0].toAddress(), input.read())

    override fun numberOfParameters() = 1
}

class WriteToOutputInstruction(val output: Output) : Instruction {
    override fun handle(parameters: List<Value>): Effect {
        output.write(parameters[0])
        return NoOpEffect()
    }

    override fun numberOfParameters() = 1

}

class JumpIfTrueInstruction : Instruction {

    override fun numberOfParameters() = 2

    override fun handle(parameters: List<Value>) = if (parameters[0] == ZERO)
        NoOpEffect()
    else
        JumpToEffect(parameters[1].toAddress())

}

class JumpIfFalseInstruction : Instruction {

    override fun numberOfParameters() = 2

    override fun handle(parameters: List<Value>) = if (parameters[0] == ZERO)
        JumpToEffect(parameters[1].toAddress())
    else
        NoOpEffect()

}

class LessThanInstruction : Instruction {
    override fun numberOfParameters() = 3

    override fun handle(parameters: List<Value>) = if (parameters[0] < parameters[1])
        WriteToMemoryEffect(parameters[2].toAddress(), ONE)
    else
        WriteToMemoryEffect(parameters[2].toAddress(), ZERO)
}

class EqualsInstruction : Instruction {

    override fun numberOfParameters() = 3

    override fun handle(parameters: List<Value>) = if (parameters[0] == parameters[1])
        WriteToMemoryEffect(parameters[2].toAddress(), ONE)
    else
        WriteToMemoryEffect(parameters[2].toAddress(), ZERO)
}

class AdjustRelativeBaseInstruction : Instruction {
    override fun numberOfParameters() = 1

    override fun handle(parameters: List<Value>) = AdjustRelativeBaseEffect(parameters[0].toAddress())
}


class ExitInstruction(val instructionPointer: Int) : Instruction {
    override fun handle(parameters: List<Value>): StopEffect {
        return StopEffect()
    }
}

class UnknownOpcodeInstruction(val currentInstruction: Value, val opcode: Int) : Instruction {
    override fun handle(parameters: List<Value>): Effect {
        System.err.println("$opcode from $currentInstruction")
        return NoOpEffect()
    }
}

