package y2020.d02

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.greaterThan
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import util.input.parseInput


class PasswordToPolicyValidatorTest {

    @Test
    fun twoShouldBeValidForPartOne() {
        assertTrue(isValidPartOne("1-3 a", "abcde"))
        assertFalse(isValidPartOne("1-3 b", "cdefg"))
        assertTrue(isValidPartOne("2-9 c", "ccccccccc"))
    }

    @Test
    fun twoOthersShouldBeValidForPartTwo() {
        assertTrue(isValidPartTwo("1-3 a", "abcde"))
        assertFalse(isValidPartTwo("1-3 b", "cdefg"))
        assertFalse(isValidPartTwo("2-9 c", "ccccccccc"))
        assertTrue(isValidPartTwo("9-12 t", "ttfjvvtgxtctrntnhtt"))

        val inputs = parseInput(Year2020Day02Input) { Pair(it.substringBefore(":"), it.substringAfter(":")) }.toList()
        val validPasswords = inputs.filter { isValidPartTwo(it.first, it.second) }.count()
        assertThat(validPasswords, greaterThan(452))

    }
}

private const val y2020d2example1 = """
1-3 a: abcde
1-3 b: cdefg
2-9 c: ccccccccc""";
