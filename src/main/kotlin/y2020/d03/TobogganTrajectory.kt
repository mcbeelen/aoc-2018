package y2020.d03

import util.input.parseInput

fun numberOfTreesToEncounterOnSlope(terrain: String, right: Int, down: Int = 1): Int {
    val rows = parseInput(terrain).toList()
    val width = rows[0].length

    val linesToHits = rows.filterIndexed { rowIndex, _ -> (rowIndex % down) == 0 }.toList()
    return linesToHits.mapIndexed { rowIndex, line -> isThereATreeAt(line, rowIndex, right, width) }.filter { it }.count()
}

fun isThereATreeAt(line: String, index: Int, right: Int, width: Int): Boolean {
    return '#' == line[(index * right) % width]
}
