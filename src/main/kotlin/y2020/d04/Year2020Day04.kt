package y2020.d04



fun solveIt(input: String) : Int {
    return parse(input)
            .count { it.isValid() }
}

fun parsePassport(input: String): Passport {
    return Passport(input)
}

fun parse(input: String): List<Passport> {
    val passwords : MutableList<Passport> = ArrayList()

    var passportDataBuilder = StringBuilder()
    input.lines()
            .forEach {
                if (it.isEmpty()) {
                    passwords.add(Passport(passportDataBuilder.toString()))
                    passportDataBuilder = StringBuilder()
                } else {
                    passportDataBuilder.append(it)
                }
            }
    passwords.add(Passport(passportDataBuilder.toString()))


    return passwords
}
