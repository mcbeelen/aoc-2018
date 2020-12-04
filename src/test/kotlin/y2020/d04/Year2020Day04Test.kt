package y2020.d04

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class Year2020Day04Test {

    @Test
    fun itShouldSolveTheExample() {
        assertThat(solveIt(y2020d04testInput), equalTo(2))
        assertThat(solveIt(y2020d04input), equalTo(213))
        assertThat(solveIt(y2020d04inputSvh), equalTo(202))
    }

    @Test
    fun `first example is valid`() {
        val firstExample = "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\n" +
                "byr:1937 iyr:2017 cid:147 hgt:183cm"

        val passportData = parsePassport(firstExample)
        assertTrue(passportData.isValid())

    }


    @Test
    fun `input has 4 passwords of varying validity`() {
        val testPassports = parse(y2020d04testInput)
        assertThat(testPassports, hasSize(equalTo(4)))

        assertTrue("First is valid", testPassports[0].isValid())
        assertFalse("Fourth is invalid", testPassports[3].isValid())
    }

    @Test
    fun `define 7 required fields`() {
        val inputData = listOf(
                "byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", // valid
                "         iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", // missing byr
                "byr:1937          eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", // missing iyr
                "byr:1937 iyr:2017          hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", // missing eyr
                "byr:1937 iyr:2017 eyr:2020           hcl:#fffffd ecl:gry pid:860033327 cid:147", // missing hgt
                "byr:1937 iyr:2017 eyr:2020 hgt:183cm             ecl:gry pid:860033327 cid:147", // missing hcl
                "byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd         pid:860033327 cid:147", // missing ecl
                "byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry               cid:147", // missing pid
                "byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327        ") // missing cid but still valid

        val passwords = inputData.map { Passport(it) }.toList()

        assertTrue(passwords[0].isValid())
        assertFalse(passwords[1].isValid())
        assertFalse(passwords[2].isValid())
        assertFalse(passwords[3].isValid())
        assertFalse(passwords[4].isValid())
        assertFalse(passwords[5].isValid())
        assertFalse(passwords[6].isValid())
        assertFalse(passwords[7].isValid())
        assertTrue(passwords[8].isValid())


    }
}
