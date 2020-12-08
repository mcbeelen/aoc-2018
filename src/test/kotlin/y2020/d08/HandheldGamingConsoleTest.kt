package y2020.d08

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import kotlin.Int.Companion.MIN_VALUE

class HandheldGamingConsoleTest {

    @Test
    fun examplePartOne() {
        val console = HandheldGamingConsole(testInput)
        val exitCode = console.executeBootCode()
        assertThat(exitCode, equalTo(-1))
        assertThat(console.valueOfTheAccumulator(), equalTo(5))
    }

    @Test
    fun partOne() {
        val console = HandheldGamingConsole(y2020d08bootstrapCode)
        val exitCode = console.executeBootCode()
        assertThat(exitCode, equalTo(-1))
        assertThat(console.valueOfTheAccumulator(), equalTo(1753))
    }

    @Test
    fun examplePartTwo() {
        val console = HandheldGamingConsole(testInput)
        console.patchLine(7)
        val exitCode = console.executeBootCode()
        assertThat(exitCode, equalTo(0))
        assertThat(console.valueOfTheAccumulator(), equalTo(8))
    }

}


const val testInput = """nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6"""
