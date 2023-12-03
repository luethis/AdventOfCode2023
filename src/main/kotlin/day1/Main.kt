package day1

import java.io.File

val input = File("src/main/kotlin/day1/input.txt")

fun main() {
    easy()
    //hard()
}

fun easy() {
    val total = input.readLines().sumOf { getCalibrationNumberYoutube(it) }
    print(total)
}

fun getCalibrationNumber(input: String): Int {
    val input2 = input.mapNotNull { it.digitToIntOrNull() }
    return "${input2.first()}${input2.last()}".toInt()
}

fun getCalibrationNumberYoutube(input: String): Int {
    val first = input.first { it.isDigit() }
    val last = input.last { it.isDigit() }
    return "$first$last".toInt()
}

fun hard() {
    val total = input.readLines().sumOf { getCalibrationNumberExtended(it) }
    print(total)
}

fun getCalibrationNumberExtended(input: String): Int {
    val input2 = input.fix().mapNotNull { it.digitToIntOrNull() }
    return "${input2.first()}${input2.last()}".toInt()
}

fun String.fix() = replace("one", "o1ne")
    .replace("two", "t2wo")
    .replace("three", "t3hree")
    .replace("four", "f4our")
    .replace("five", "f5ive")
    .replace("six", "s6ix")
    .replace("seven", "s7even")
    .replace("eight", "e8ight")
    .replace("nine", "n9ine")