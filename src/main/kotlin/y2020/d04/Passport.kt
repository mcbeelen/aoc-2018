package y2020.d04

class PassportBuilder {

    private val passportAttributes: MutableMap<String, String> = HashMap()

    fun append(lineWithPassportDate: String): PassportBuilder {
        lineWithPassportDate.split(" ").forEach {
            val trim = it.trim()
            passportAttributes[trim.substringBefore(":")] = trim.substringAfter(":")
        }
        return this
    }

    fun buildPassport() = Passport(passportAttributes)

}

class Passport(val data: MutableMap<String, String>) {

    fun isValid(): Boolean {

        return data.keys.contains("byr") &&
                data.keys.contains("iyr") &&
                data.keys.contains("eyr") &&
                data.keys.contains("hgt") &&
                data.keys.contains("hcl") &&
                data.keys.contains("ecl") &&
                data.keys.contains("pid")
    }

    /***
     *  byr (Birth Year) - four digits; at least 1920 and at most 2002.
    iyr (Issue Year) - four digits; at least 2010 and at most 2020.
    eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
    hgt (Height) - a number followed by either cm or in:
      If cm, the number must be at least 150 and at most 193.
      If in, the number must be at least 59 and at most 76.
    ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.

    cid (Country ID) - ignored, missing or not.
     */
    fun isStrictlyValid(): Boolean {
        return hasValidBirthYear() &&
                hasValidIssuerYear() &&
                hasValidExpirationYear() &&
                hasValidHeight() &&
                hasValidHairColor() &&
                hasValidEyeColor() &&
                hasValidPassportId()
    }





    private fun hasValidBirthYear() = data.containsKey("byr") && isValidYear("byr") { it in 1920..2002 }
    private fun hasValidIssuerYear() = data.containsKey("iyr") && isValidYear("iyr") { it in 2010..2020 }
    private fun hasValidExpirationYear() = data.containsKey("eyr") && isValidYear("eyr") { it in 2020..2030 }

    private fun isValidYear(key: String, predicate: (Int) -> Boolean): Boolean {
        val value = data.getValue(key)
        return if (value.matches("^\\d{4}\$".toRegex())) {
            predicate(value.toInt())
        } else {
            false
        }
    }

    private fun hasValidHeight() = data.keys.contains("hgt") && isHeightValid()

    private fun isHeightValid(): Boolean {
        val value = data.getValue("hgt")
        return when {
            value.endsWith("cm") -> value.substringBefore("cm").toInt() in 150 .. 193
            value.endsWith("in") -> value.substringBefore("in").toInt() in 59 .. 75
            else -> return false
        }
    }

    private fun hasValidHairColor() = data.keys.contains("hcl") && isHairColorValid()
    private fun isHairColorValid() = data.getValue("hcl").matches("^#[1-9a-f]{6}".toRegex())

    private fun hasValidEyeColor() = data.keys.contains("ecl") && isEyeColorValid()
    private fun isEyeColorValid() = VALID_EYE_COLORS.contains(data.getValue("ecl"))
    private val VALID_EYE_COLORS = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

    private fun hasValidPassportId() = data.containsKey("pid") && isPassportIdValid()
    private fun isPassportIdValid() = data.getValue("pid").matches("^[0-9]{9}$".toRegex())

}

abstract class Year (val value: String) {
    fun isValid() = value.matches("^\\d{4}\$".toRegex()) && inValidRange(value.toInt())

    protected abstract fun inValidRange(year: Int): Boolean
}

class BirthYear(value: String) : Year(value) {
    override fun inValidRange(year: Int) = year in 1920 .. 2002
}

class IssueYear(value: String) : Year(value) {
    override fun inValidRange(year: Int) = year in 2010 .. 2020
}
