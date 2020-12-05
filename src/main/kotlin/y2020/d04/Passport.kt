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

        return data.containsKey("byr") &&
                data.containsKey("iyr") &&
                data.containsKey("eyr") &&
                data.containsKey("hgt") &&
                data.containsKey("hcl") &&
                data.containsKey("ecl") &&
                data.containsKey("pid")
    }

    fun isStrictlyValid(): Boolean {
        return isValid() &&
                hasValidBirthYear() &&
                hasValidIssuerYear() &&
                hasValidExpirationYear() &&
                hasValidHeight() &&
                hasValidHairColor() &&
                hasValidEyeColor() &&
                hasValidPassportId()
    }

    private fun hasValidBirthYear() = BirthYear(getValue("byr")).isValid()
    private fun hasValidIssuerYear() = IssueYear(getValue("iyr")).isValid()
    private fun hasValidExpirationYear() = ExpirationYear(getValue("eyr")).isValid()

    private fun hasValidHeight(): Boolean {
        val value = data.getValue("hgt")

        return value.matches(HeightRegex) &&
                when {
                    value.endsWith("cm") -> value.substringBefore("cm").toInt() in 150..193
                    value.endsWith("in") -> value.substringBefore("in").toInt() in 59..76
                    else -> return false
                }
    }

    private val HeightRegex = "^\\d{2,3}(in|cm)\$".toRegex()

    private fun hasValidHairColor() = getValue("hcl").matches(ValidHairColorRegex)
    private val ValidHairColorRegex = "^#[0-9a-f]{6}$".toRegex()

    private fun hasValidEyeColor() = VALID_EYE_COLORS.contains(getValue("ecl"))
    private val VALID_EYE_COLORS = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

    private fun hasValidPassportId() = getValue("pid").matches("^[0-9]{9}$".toRegex())

    private fun getValue(key: String) = data.getValue(key)

}

abstract class Year(val value: String) {
    fun isValid() = value.matches("^\\d{4}\$".toRegex()) && inValidRange(value.toInt())

    protected abstract fun inValidRange(year: Int): Boolean
}

class BirthYear(value: String) : Year(value) {
    override fun inValidRange(year: Int) = year in 1920..2002
}

class IssueYear(value: String) : Year(value) {
    override fun inValidRange(year: Int) = year in 2010..2020
}

class ExpirationYear(value: String) : Year(value) {
    override fun inValidRange(year: Int) = year in 2020..2030
}
