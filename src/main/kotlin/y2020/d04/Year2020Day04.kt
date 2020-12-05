package y2020.d04



fun solvePartOne(input: String) = parse(input).count { it.isValid() }
fun solvePartTwo(input: String) = parse(input).count { it.isStrictlyValid() }


fun parse(input: String): List<Passport> {
    val passwords : MutableList<Passport> = ArrayList()

    var passportDataBuilder = PassportBuilder()
    input.lines()
            .forEach {
                if (it.isEmpty()) {
                    passwords.add(passportDataBuilder.buildPassport())
                    passportDataBuilder = PassportBuilder()
                } else {
                    passportDataBuilder.append(it)
                }
            }
    passwords.add(passportDataBuilder.buildPassport())


    return passwords
}
