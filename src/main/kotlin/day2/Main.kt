package day2

import java.io.File
import java.lang.RuntimeException
import kotlin.math.max

val input = File("src/main/kotlin/day2/input.txt")

fun main() {
    game1()
    //game2()
}

private fun game1() {
    val green = 13
    val blue = 14
    val red = 12

    var sum = 0

    input.forEachLine { game ->
        val id = game.substringBefore(":").filter { it.isDigit() }.toInt()
        val info = game.substringAfter(":")

        val sets = info.split(";")

        val valid = sets.none {
            it.split(",").any { cube ->
                val amount = cube.filter { it.isDigit() }.toInt()
                when {
                    cube.contains("green") -> green < amount
                    cube.contains("blue") -> blue < amount
                    cube.contains("red") -> red < amount
                    else -> throw RuntimeException("unknown color")
                }

            }
        }

        if (valid) sum += id
    }

    println(sum)
}

private fun game2() {
    val input = input.readLines()
    var totalPower = 0

    input.forEach { game ->

        var red = 0
        var green = 0
        var blue = 0

        val info = game.substringAfter(":")

        val sets = info.split(";")
        sets.forEach { set ->

            val cubes = set.split(",")

            cubes.forEach { cube ->

                val amount = cube.filter { it.isDigit() }.toInt()
                when {
                    cube.contains("green") -> red = max(red, amount)
                    cube.contains("blue") -> green = max(green, amount)
                    cube.contains("red") -> blue = max(blue, amount)
                    else -> throw RuntimeException("unknown color")
                }
            }
        }

        totalPower += red * green * blue
    }

    println(totalPower)
}


