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


fun main() {
    val keyToInspect = "hgt"
    parse(y2020d04input)
        .filter { it.isValid() }
        .filter { it.isStrictlyValid() }
        .filter { it.data.getValue(keyToInspect) == "150cm"}
        .sortedBy { it.data.getValue(keyToInspect) }
        .forEach { println("${it.data.toSortedMap()} ==> ${it.isStrictlyValid()}") }
}


// Is there a valid passport with height: 150cm?
