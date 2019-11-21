package y2018.day04_sleeping_guards


/**
[1518-11-01 00:00] Guard #10 begins shift
[1518-11-01 00:05] falls asleep
[1518-11-01 00:25] wakes up
[1518-11-01 00:30] falls asleep
[1518-11-01 00:55] wakes up
[1518-11-01 23:58] Guard #99 begins shift
[1518-11-02 00:40] falls asleep
[1518-11-02 00:50] wakes up
[1518-11-03 00:05] Guard #10 begins shift
[1518-11-03 00:24] falls asleep
[1518-11-03 00:29] wakes up
 */
data class Record(val month : Int, val day : Int, val minute: Minute, val action : String )



fun fromInput(input: String) : Record {
    val month = input.substring(6..7).toInt()
    val day = input.substring(9..10).toInt()
    val minute = Minute(input.substring(15..16).toInt())

    return Record(month, day, minute, input.substringAfter("] "))
}
