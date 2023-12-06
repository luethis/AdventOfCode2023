package day6

import java.io.File

val input = File("src/main/kotlin/day6/input.txt")
val numberPattern = Regex("\\d+") // matches any number

fun main() {
    part2()
}

private fun part2() {
    val input = input.readLines()
    val time = numberPattern.findAll(input[0]).map { it.value }.reduce { current, next -> "$current$next" }.toLong()
    val distance = numberPattern.findAll(input[1]).map { it.value }.reduce { current, next -> "$current$next" }.toLong()
    val count = (0..time).count { i -> (i * (time - i)) > distance }
    println(count)
}

private fun part1() {
    val input = input.readLines()
    val times = numberPattern.findAll(input[0]).map { it.value.toInt() }.toList()
    val distances = numberPattern.findAll(input[1]).map { it.value.toInt() }.toList()

    println(times)

    val total = mutableListOf<Int>()

    times.zip(distances).forEachIndexed { index, (time, distance) ->
        val count = (0..time).count { i -> (i * (time - i)) > distance }
        total.add(count)
        println("game ${index + 1} will be won in $count combinations")
    }

    println(total.reduce { current, next -> current * next })
}