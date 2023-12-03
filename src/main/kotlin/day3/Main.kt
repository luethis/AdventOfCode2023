package day3

import java.io.File

val input = File("src/main/kotlin/day3/input.txt")
val symbols = listOf('#', '*', '-', '$', '@', '/', '+', '&', '%', '=')

fun main() {
    part2()
}

fun part2() {
    val lines = input.readLines()
    var total = 0

    lines.forEachIndexed { index, line ->
        val pattern = Regex("\\*")
        val matchResult = pattern.findAll(line)

        matchResult.forEach {
            val lastLine = lines.getOrNull(index - 1)
            val nextLine = lines.getOrNull(index + 1)

            findGearNumbers(it.range, lastLine, line, nextLine)?.let { gearNumbers ->
                total += (gearNumbers[0] * gearNumbers[1])
            }
        }
    }

    print(total)
}


private fun findGearNumbers(range: IntRange, lastLine: String?, line: String, nextLine: String?): List<Int>? {
    val start = (range.first - 1).coerceAtLeast(0)
    val end = (range.last + 1).coerceAtMost(line.length)

    val numbers = mutableListOf<Int>()

    lastLine?.getGearNumbers(start, end)?.let { numbers.addAll(it) }
    line.getGearNumbers(start, end).let { numbers.addAll(it) }
    nextLine?.getGearNumbers(start, end)?.let { numbers.addAll(it) }

    return if (numbers.size == 2) {
        numbers
    } else {
        null
    }
}

private fun String.getGearNumbers(startIndex: Int, endIndex: Int): List<Int> {
    return Regex("\\d+").findAll(this).filter {
        it.range.any { it in startIndex..endIndex }
    }.toList().map { it.value.toInt() }
}

private fun part1() {
    val lines = input.readLines()
    var total = 0

    lines.forEachIndexed() { index, line ->
        val pattern = Regex("\\d+")
        val matchResult = pattern.findAll(line)

        matchResult.forEach {
            val lastLine = lines.getOrNull(index - 1)
            val nextLine = lines.getOrNull(index + 1)

            if (checkLinesForSymbols(it.range, lastLine, line, nextLine)) {
                total = total.plus(it.value.toInt())
            }
        }
    }

    print(total)
}

private fun checkLinesForSymbols(range: IntRange, lastLine: String?, line: String, nextLine: String?): Boolean {
    val start = (range.first - 1).coerceAtLeast(0)
    val end = (range.last + 1).coerceAtMost(line.length)

    return lastLine?.hasSymbol(start, end) ?: false
            || line.hasSymbol(start, end)
            || nextLine?.hasSymbol(start, end) ?: false
}

private fun String.hasSymbol(startIndex: Int, endIndex: Int) = substring(startIndex, endIndex + 1).any { it in symbols }
