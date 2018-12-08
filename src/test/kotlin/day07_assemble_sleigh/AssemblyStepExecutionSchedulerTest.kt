package day07_assemble_sleigh

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class AssemblyStepExecutionSchedulerTest {

    var stepMap : MutableMap<Step, Pair<String, Int>> = HashMap()


    /**
      -->A--->B--
     /    \      \
    C      -->D----->E
     \           /
      ---->F-----
     */
    @Test
    fun examplePartTwo() {
        val assembleInstructions = DAY07_EXAMPLE_INPUT.trimIndent().lines().map { parseToAssembleOrder(it) }

        // CABDFE
        assertThat(findMinimumTimeNeededToAssemble(assembleInstructions, Step('C')).second, equalTo(3))
        assertThat(findMinimumTimeNeededToAssemble(assembleInstructions, Step('A')).second, equalTo(4))
        assertThat(findMinimumTimeNeededToAssemble(assembleInstructions, Step('B')).second, equalTo(6))

        assertThat(findMinimumTimeNeededToAssemble(assembleInstructions, Step('D')).second, equalTo(8))

        assertThat(findMinimumTimeNeededToAssemble(assembleInstructions, Step('F')).second, equalTo(9))
        assertThat(findMinimumTimeNeededToAssemble(assembleInstructions, Step('E')).first, equalTo("CEF"))
        assertThat(findMinimumTimeNeededToAssemble(assembleInstructions, Step('E')).second, equalTo(14))

    }



/*
time: |01234567890123456789012345678901234567890123456789|01234567890123456789012345678901234567890123456789|01234567890123456789012345678901234567890123456789|01234567890123456789012345678901234567890123456789|012345678
1 :   |BB.........CCCJJJJJJJJJJ.....ZZZZZZZZZZZZZZZZZZZZZ|ZZZZZ.....YYYYYYYYYYYYYYYYYYYYYYYYYOOOOOOOOOOOOOOO|A....................LLLLLLLLLLLL....IIIIIIIIIEEEE|EWWWWWWWWWWWWWWWWWWWWWWWTTTTTTTTTTTTTTTTTTTTNNNNNN|NNNNNNNN.
2 :   |KKKKKKKKKKKMMMMMMMMMMMMM......DDDD.............RRR|RRRRRRRRRRRRRRR...................................|UUUUUUUUUUUUUUUUUUUUUPPPPPPPPPPPPPPPP.............|..................................................|.........
3 :   |VVVVVVVVVVVVVVVVVVVVVVGGGGGGG.QQQQQQQQQQQQQQQQQ...|....FFFFFF........................................|..................................................|..................................................|.........
4 :   |...........SSSSSSSSSSSSSSSSSSSXXXXXXXXXXXXXXXXXXXX|XXXX..............................................|..................................................|..................................................|.........
5 :   |......................HHHHHHHH....................|..................................................|..................................................|..................................................|.........
 */


    @Test
    fun minimumTimeNeededToAssemble() {


        val assembleInstructions = SLEIGH_ASSEMBLY_INSTRUCTIONS.trimIndent().lines().map { parseToAssembleOrder(it) }
        assertThat(findMinimumTimeNeededToAssemble(assembleInstructions, Step('B')).second, equalTo(62))
        assertThat(findMinimumTimeNeededToAssemble(assembleInstructions, Step('K')).second, equalTo(71))
        assertThat(findMinimumTimeNeededToAssemble(assembleInstructions, Step('V')).second, equalTo(82))
6//
//        assertThat(findMinimumTimeNeededToAssemble(assembleInstructions, Step('C')), equalTo(14))
//
//        assertThat(findMinimumTimeNeededToAssemble(assembleInstructions, Step('J')), equalTo(24))
//        assertThat(findMinimumTimeNeededToAssemble(assembleInstructions, Step('M')), equalTo(24))
//
//        assertThat("G", findMinimumTimeNeededToAssemble(assembleInstructions, Step('G')), equalTo(29))
//        assertThat("S", findMinimumTimeNeededToAssemble(assembleInstructions, Step('S')), equalTo(30))
//        assertThat("H", findMinimumTimeNeededToAssemble(assembleInstructions, Step('H')), equalTo(30))

        assertThat("Path to D", findMinimumTimeNeededToAssemble(assembleInstructions, Step('D')).first, equalTo("KSD"))
        assertThat("Time to D", findMinimumTimeNeededToAssemble(assembleInstructions, Step('D')).second, equalTo(34))



        assertThat("Path to O", findMinimumTimeNeededToAssemble(assembleInstructions, Step('O')).first, equalTo("VHXFYO"))
        assertThat("Time to O", findMinimumTimeNeededToAssemble(assembleInstructions, Step('O')).second, equalTo(100))


        assertThat("Path to N", findMinimumTimeNeededToAssemble(assembleInstructions, Step('N')).first, equalTo("VHXFYOUPIEWTN"))
        assertThat("Time to N", findMinimumTimeNeededToAssemble(assembleInstructions, Step('N')).second, equalTo(208))


    }






    private fun findMinimumTimeNeededToAssemble(assembleInstructions: List<AssemblyInstruction>, step: Step): Pair<String, Int> {

        if (stepMap.containsKey(step)) {
            return stepMap.getValue(step)
        }

        println("Going to calculate minTimeNeeded for ${step.name}")

        val instruction = assembleInstructions.filter { it.after == step }

        val minimumTimeNeededToAssemble : Pair<String, Int>  = if (instruction.isEmpty()) {
            Pair(step.name.toString(), step.duration())
        } else {
            val maxBy = instruction
                    .map { Pair(it.before, findMinimumTimeNeededToAssemble(assembleInstructions, it.before)) }
                    .maxBy { it.second.second }!!

            println("CriticalPath to ${step.name} is via ${maxBy.first.name} and takes ${maxBy.second} + ${step.duration()} = ${step.duration() + maxBy.second.second}")


            Pair(maxBy.second.first + step.name.toString(), step.duration() + maxBy.second.second)

        }

        stepMap.put(step, minimumTimeNeededToAssemble)
        println("Getting to ${step.name} will take at least ${minimumTimeNeededToAssemble.second} going through ${minimumTimeNeededToAssemble.first}")

        return minimumTimeNeededToAssemble

    }
}