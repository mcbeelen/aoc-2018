package y2020.d15

import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.ListMultimap

fun spokenNumber(startSequence: String, numberSpoken: Int): Int {

    val turnsSpokenPerNumber : ListMultimap<Int, Int> = ArrayListMultimap.create()

    val startingNumbers = startSequence.splitToSequence(',').map { it.toInt() }.toList()
    startingNumbers.forEachIndexed { index: Int, nextNumber: Int -> turnsSpokenPerNumber.put(nextNumber, index + 1) }

    var lastSpokenNumber = startingNumbers.last()
    var lastSpokenWasNew = true

    for (turn in startingNumbers.size + 1 .. numberSpoken) {
        val nextNumber = if (lastSpokenWasNew) 0 else ageOf(lastSpokenNumber, turnsSpokenPerNumber)

        lastSpokenWasNew = ! turnsSpokenPerNumber.containsKey(nextNumber)
        turnsSpokenPerNumber.put(nextNumber, turn)
        lastSpokenNumber = nextNumber
    }


    return lastSpokenNumber
}

private fun ageOf(lastSpokenNumber: Int, turnsSpokenPerNumber: ListMultimap<Int, Int>): Int {
    val turnsWhenThisNumberWasSpoken = turnsSpokenPerNumber.get(lastSpokenNumber)
    val previousTurnsSpoken = turnsWhenThisNumberWasSpoken.takeLast(2)
    return previousTurnsSpoken[1] - previousTurnsSpoken[0]
}
