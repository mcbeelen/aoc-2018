package y2018.day07_assemble_sleigh

class SleighAssemblyOrder {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println(determineOrderOfAssembly(SLEIGH_ASSEMBLY_INSTRUCTIONS.trimIndent()))
        }
    }
}


internal fun determineOrderOfAssembly(input: String): String {
    val assembleInstructions = input.lines().map { parseToAssembleOrder(it) }
    val allSteps: MutableSet<Step> = findAllSteps(assembleInstructions)
    var dependsUpon: Map<Step, List<AssemblyInstruction>> = assembleInstructions.groupBy { it.after }


    val executionOrder = StringBuilder(assembleInstructions.size)
    while (allSteps.isNotEmpty()) {
        val step = findNextStep(allSteps, dependsUpon)
        executionOrder.append(step.name)

        allSteps.remove(step)
        dependsUpon = dependsUpon.mapValues { orders -> orders.value.filter { it.before != step } }
    }



    return executionOrder.toString()
}

internal fun findAllSteps(assembleInstructions: List<AssemblyInstruction>): MutableSet<Step> =
        assembleInstructions.map { it.before }.union(assembleInstructions.map { it.after }).toMutableSet()


private fun findNextStep(allSteps: Set<Step>, dependsUpon: Map<Step, List<AssemblyInstruction>>): Step {
    return findStepsReadyToBeWorkedOn(allSteps, dependsUpon).first()
}

private fun findStepsReadyToBeWorkedOn(allSteps: Set<Step>, dependsUpon: Map<Step, List<AssemblyInstruction>>): List<Step> {
    return allSteps.filter { candidateStep ->
        dependsUpon.none {
            shouldBeDoneBefore(it.value, candidateStep)
        }
    }
    .sortedBy { it.name }
}

private fun shouldBeDoneBefore(it: List<AssemblyInstruction>, candidateStep: Step): Boolean {
    return it.any {
        it.after == candidateStep
    }
}

internal fun parseToAssembleOrder(input: String) = AssemblyInstruction(Step(input[5]), Step(input[36]))


data class AssemblyInstruction(val before: Step, val after: Step)

inline class Step(val name: Char) {
    fun duration() : Int = 60 + (name - 'A' + 1)
}