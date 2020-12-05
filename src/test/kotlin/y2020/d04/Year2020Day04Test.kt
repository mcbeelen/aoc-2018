package y2020.d04

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class Year2020Day04Test {

    @Test
    fun itShouldSolveTheExampleForPartOne() {
        assertThat(solvePartOne(y2020d04testInput), equalTo(2))
        assertThat(solvePartOne(y2020d04input), equalTo(213))
    }

    @Test
    fun itShouldSolveTheExampleForPartTwo() {
        assertThat(solvePartTwo(y2020d04testInputPart02), equalTo(4))
        assertThat(solvePartTwo(y2020d04input), equalTo(147))
    }

    @Test
    fun `first example is valid`() {
        val firstExample = "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd\n" +
                "byr:1937 iyr:2017 cid:147 hgt:183cm"

        val passportData = parse(firstExample)
        assertTrue(passportData[0].isValid())

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
                "byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", // 0 valid
                "         iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", // 1 missing byr
                "byr:1937          eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", // 2 missing iyr
                "byr:1937 iyr:2017          hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", // 3 missing eyr
                "byr:1937 iyr:2017 eyr:2020           hcl:#fffffd ecl:gry pid:860033327 cid:147", // 4 missing hgt
                "byr:1937 iyr:2017 eyr:2020 hgt:183cm             ecl:gry pid:860033327 cid:147", // 5 missing hcl
                "byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd         pid:860033327 cid:147", // 6 missing ecl
                "byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry               cid:147", // 7 missing pid
                "byr:1947 iyr:2020 eyr:2028 hgt:150cm hcl:#341e13 ecl:hzl pid:716596533 cid:254 ",// 8
                "byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327        ") // missing cid but still valid

        val passwords = inputData.map { PassportBuilder().append(it).buildPassport() }.toList()

        assertTrue(passwords[0].isValid())
        assertFalse(passwords[1].isValid())
        assertFalse(passwords[2].isValid())
        assertFalse(passwords[3].isValid())
        assertFalse(passwords[4].isValid())
        assertFalse(passwords[5].isValid())
        assertFalse(passwords[6].isValid())
        assertFalse(passwords[7].isValid())
        assertTrue(passwords[8].isValid())
        assertTrue(passwords[8].isStrictlyValid())
        assertTrue(passwords[9].isValid())
    }

    @Test
    fun samplesForPartTwo() {
        assertFalse(BirthYear("1919").isValid())
        assertTrue(BirthYear("1920").isValid())
        assertTrue(BirthYear("2002").isValid())
        assertFalse(BirthYear("2003").isValid())

        assertFalse(IssueYear("2009").isValid())
        assertTrue(IssueYear("2010").isValid())
        assertTrue(IssueYear("2020").isValid())
        assertFalse(IssueYear("2021").isValid())

        val example = listOf(
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", true),

            // BirthYear
            Pair("byr:1919 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", false),
            Pair("byr:1920 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", true),
            Pair("byr:2002 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", true),
            Pair("byr:2003 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", false),

            // IssueYear
            Pair("byr:1990 iyr:2009 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", false),
            Pair("byr:1990 iyr:2010 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", true),
            Pair("byr:1990 iyr:2020 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", true),
            Pair("byr:1990 iyr:2021 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", false),

            // ExpirationYear
            Pair("byr:1990 iyr:2015 eyr:2019 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", false),
            Pair("byr:1990 iyr:2015 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", true),
            Pair("byr:1990 iyr:2015 eyr:2030 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", true),
            Pair("byr:1990 iyr:2015 eyr:2031 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", false),

            // Height
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183 hcl:#fffffd ecl:gry pid:860033327 cid:147", false),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:58in hcl:#fffffd ecl:gry pid:860033327 cid:147", false),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:59in hcl:#fffffd ecl:gry pid:860033327 cid:147", true),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:76in hcl:#fffffd ecl:gry pid:860033327 cid:147", true),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:77in hcl:#fffffd ecl:gry pid:860033327 cid:147", false),

            // HairColor
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", true),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:fffffd ecl:gry pid:860033327 cid:147", false),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#0123456 ecl:gry pid:860033327 cid:147", false),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#abcdefa ecl:gry pid:860033327 cid:147", false),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#abcdeg ecl:gry pid:860033327 cid:147", false),

            // Eye Color
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:amb pid:860033327 cid:147", true),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:blu pid:860033327 cid:147", true),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:brn pid:860033327 cid:147", true),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:860033327 cid:147", true),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:hzl pid:860033327 cid:147", true),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:oth pid:860033327 cid:147", true),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:xxx pid:860033327 cid:147", false),


            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:amb pid:123456789 cid:147", true),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:amb pid:000000000 cid:147", true),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:amb pid:00000000 cid:147", false),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:amb pid:0000000001 cid:147", false),
            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:amb pid:abcdefghi cid:147", false),




            Pair("eyr:1972 cid:100 hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926", false),
            Pair("iyr:2019 hcl:#602927 eyr:1967 hgt:170cm ecl:grn pid:012533040 byr:1946", false),
            Pair("hcl:dab227 iyr:2012 ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277", false),
            Pair("hgt:59cm ecl:zzz eyr:2038 hcl:74454a iyr:2023 pid:3556412378 byr:2007", false),
            Pair("hgt:59cm ecl:zzz eyr:2025 hcl:74454a iyr:2013 pid:3556412378 byr:2007", false),
            Pair("hgt:169cm ecl:brn eyr:2025 hcl:74454a iyr:2013 pid:3556412378 byr:2007", false),

            Pair("byr:1937 iyr:2017 eyr:2020 hgt:183cm hcl:#fffffd ecl:gry pid:0123456789 cid:147", false),
            Pair("cid:254 iyr:2020 pid:716596533 hcl:#341e13 byr:1947 hgt:150cm ecl:hzl eyr:2028", true),
        )
        val filter = example.map { Triple(PassportBuilder().append(it.first).buildPassport(), it.second, it.first) }
            .filter { it.second != it.first.isStrictlyValid() }

        assertThat(filter.count(), equalTo(0))
        filter.forEach { println(it.third) }

    }
}


