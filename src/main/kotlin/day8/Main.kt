package day8

import java.io.File

val input = File("src/main/kotlin/day8/input.txt")
val charPattern = Regex("\\w+")

fun main() {
    val input = input.readLines()

    val instructions = input[0]

    val pairs = mutableMapOf<String, Pair<String, String>>()
    input.subList(2, input.size).forEach {
        val (element, left, right) = charPattern.findAll(it).map { it.value }.toList()
        pairs[element] = left to right
    }

    solve2(instructions, pairs)
}

private fun solve1(
    instructions: String,
    pairs: MutableMap<String, Pair<String, String>>
) {
    var currentIndex = 0
    var current = "AAA"
    while (current != "ZZZ") {
        val instruction = instructions[currentIndex % instructions.length]
        pairs[current]?.let { (left, right) ->
            when (instruction) {
                'L' -> current = left
                'R' -> current = right
            }
        }

        currentIndex++
    }

    println(currentIndex)
}

private fun solve2(
    instructions: String,
    pairs: MutableMap<String, Pair<String, String>>
) {
    val startingNodes = pairs.filter { it.key.endsWith("A") }

    val firstMatches = startingNodes.map {
        var currentIndex = 0L
        var current = it.key

        println(current)

        while (!current.endsWith("Z")) {
            val instruction = instructions[currentIndex.mod(instructions.length)]
            pairs[current]?.let { (left, right) ->
                when (instruction) {
                    'L' -> current = left
                    'R' -> current = right
                }
            }
            currentIndex++
        }

        currentIndex
    }.reduce { current, next -> leastCommonMultiple(current, next) }

    println(firstMatches)
}

// copied from reddit
fun leastCommonMultiple(a: Long, b: Long): Long {
    val larger = if (a > b) a else b
    val maxLcm = a * b
    var lcm = larger
    while (lcm <= maxLcm) {
        if (lcm % a == 0L && lcm % b == 0L) {
            return lcm
        }
        lcm += larger
    }
    return maxLcm
}
