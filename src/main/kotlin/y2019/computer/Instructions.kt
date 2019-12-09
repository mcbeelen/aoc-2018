package y2019.computer


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

class WriteToOutputInstruction(val output: Output) : Instruction {
    override fun handle(parameters: List<Int>): Effect {
        output.write(parameters[0])
        return NoOpEffect()
    }

    override fun numberOfParameters() = 1

}

class JumpIfTrueInstruction : Instruction {

    override fun numberOfParameters() = 2

    override fun handle(parameters: List<Int>) = if (parameters[0] == 0)
        NoOpEffect()
    else
        JumpToEffect(parameters[1])

}

class JumpIfFalseInstruction : Instruction {

    override fun numberOfParameters() = 2

    override fun handle(parameters: List<Int>) = if (parameters[0] == 0)
        JumpToEffect(parameters[1])
    else
        NoOpEffect()

}

class LessThanInstruction : Instruction {
    override fun numberOfParameters() = 3

    override fun handle(parameters: List<Int>) = if (parameters[0] < parameters[1])
        WriteToMemoryEffect(parameters[2], 1)
    else
        WriteToMemoryEffect(parameters[2], 0)
}

class EqualsInstruction : Instruction {

    override fun numberOfParameters() = 3

    override fun handle(parameters: List<Int>) = if (parameters[0] == parameters[1])
        WriteToMemoryEffect(parameters[2], 1)
    else
        WriteToMemoryEffect(parameters[2], 0)
}

class AdjustRelativeBaseInstruction : Instruction {
    override fun numberOfParameters() = 1

    override fun handle(parameters: List<Int>) = AdjustRelativeBaseEffect(parameters[0])
}


class ExitInstruction(val instructionPointer: Int) : Instruction {
    override fun handle(parameters: List<Int>): StopEffect {
        return StopEffect()
    }
}

class UnknownOpcodeInstruction(val currentInstruction: Int, val opcode: Int) : Instruction {
    override fun handle(parameters: List<Int>): Effect {
        System.err.println("$opcode from $currentInstruction")
        return NoOpEffect()
    }
}

