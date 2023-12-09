package day9

import java.io.File

val input = File("src/main/kotlin/day9/input.txt")

val partTwo = true

// to low: 1938800260
// too h : 1938800269

fun main() {
    val numbers = input.readLines().map { it.split(" ").map { it.toInt() } }

    val result = numbers.sumOf { it ->
        val seq = mutableListOf(it)
        while (!seq.last().all { it == 0 }) {
            val latestDiffs = seq.last().mapIndexedNotNull { index, value ->
                seq.last().getOrNull(index + 1)?.let {
                    it - value
                }
            }
            seq.add(latestDiffs)
        }

        if (partTwo) {
            seq.reversed().map { it.first() }.reduce { acc, new -> new - acc }
        } else {
            seq.sumOf { it.last() }
        }
    }

    println(result)
}

