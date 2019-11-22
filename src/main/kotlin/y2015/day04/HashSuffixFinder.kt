package y2015.day04

import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.atomic.AtomicInteger

fun main() {
    val myKey = "iwrupvqb"
    println("With 5 leading zeros: " + findSuffixThatYieldMd5HashWith5leadingZeros(myKey))
    println("With 6 leading zeros: " + findSuffixThatYieldMd5HashWith6leadingZeros(myKey))
}


fun findSuffixThatYieldMd5HashWith5leadingZeros(key: String): Int {
    return findSuffixThatMatchesPredicate(key) {
        it.startsWith("00000")
    }
}

fun findSuffixThatYieldMd5HashWith6leadingZeros(key: String): Int {
    return findSuffixThatMatchesPredicate(key) {
        it.startsWith("000000")
    }
}

fun findSuffixThatMatchesPredicate(key: String, predicate: (md5: String) -> Boolean): Int {
    val suffixCounter = AtomicInteger(0)

    while (true) {
        val combination = "${key}${suffixCounter.incrementAndGet()}"
        if (predicate(combination.md5())) {
            return suffixCounter.get()
        }
    }

}

fun String.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
}


