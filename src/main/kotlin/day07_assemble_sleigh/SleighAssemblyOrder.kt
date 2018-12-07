package day07_assemble_sleigh

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

private fun findAllSteps(assembleInstructions: List<AssemblyInstruction>): MutableSet<Step> =
        assembleInstructions.map { it.before }.union(assembleInstructions.map { it.after }).toMutableSet()


private fun findNextStep(allSteps: Set<Step>, dependsUpon: Map<Step, List<AssemblyInstruction>>): Step {
    return allSteps.filter { candidateStep ->
        dependsUpon.none {
            it.value.any {
                it.after == candidateStep
            }
        }
    }
            .sortedBy { it.name }
            .first()
}

internal fun parseToAssembleOrder(input: String) = AssemblyInstruction(Step(input[5]), Step(input[36]))


data class AssemblyInstruction(val before: Step, val after: Step)

inline class Step(val name: Char)