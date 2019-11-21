package y2018.day19_chrono_go_with_the_flow

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import y2018.day16_chronal_classification.OpCode.ADDR
import y2018.day16_chronal_classification.OpCode.SETI
import org.junit.Test


class CompilerTest {

    @Test
    fun itShouldParseProgram() {
        val program = parseProgram(EXAMPLE_INSTRUCTIONS)
        assertThat(program.boundRegister, equalTo(0))
        assertThat(program.instructions.size, equalTo(7))

        program.instructions.let {
            assertThat(it[0].opCode, equalTo(SETI))
            assertThat(it[3].opCode, equalTo(ADDR))
        }

    }

}

