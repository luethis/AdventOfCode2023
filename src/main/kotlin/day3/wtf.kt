package day03

// Time it took me: 1hr 22m


import java.io.File
import java.util.HashMap

const val DAY = 3
val dayZeroPadded = String.format("%02d", DAY)

fun main() {

    // Checks for digits in a string forward and backward
    // Returns x,y position for start of number and number
    fun getNumberAtPosition(input: List<String>,
                            position: Pair<Int, Int>): Pair<Pair<Int, Int>, Int> {
        val (x, y) = position
        if (!input[y][x].isDigit()) {
            throw Error("Expected '${input[y][x]}' at $x, $y to be digit")
        }

        val digits = mutableListOf<Char>()

        var lowestX = x

        var nX = x
        while (nX > -1 && input[y][nX].isDigit()) {
            digits.add(0, input[y][nX])
            lowestX = nX
            nX -= 1
        }

        nX = x + 1
        while (nX < input[y].length && input[y][nX].isDigit()) {
            digits.add(input[y][nX])
            nX += 1
        }

        val n = digits.joinToString("").toInt()
        return Pair(Pair(lowestX, y), n)
    }



    fun findNeighborNumbers(input: List<String>,
                            position: Pair<Int, Int>
        // Return item is a List of Pairs.
        // The first item is the x,y cords of the number's starting digit
        // The second item is the number
    ): List<Pair<Pair<Int, Int>, Int>> {
        val (x, y) = position
        val foundNumbers = mutableListOf<Pair<Pair<Int, Int>, Int>> ()
        // Checking N
        try {
            foundNumbers.add(getNumberAtPosition(input, Pair(x, y-1)))
        } catch (e: Error) {

        }

        // Checking NE
        try {
            foundNumbers.add(getNumberAtPosition(input, Pair(x+1, y-1)))
        } catch (e: Error) {

        }

        // Checking E
        try {
            foundNumbers.add(getNumberAtPosition(input, Pair(x+1, y)))
        } catch (e: Error) {

        }

        // Checking SE
        try {
            foundNumbers.add(getNumberAtPosition(input, Pair(x+1, y+1)))
        } catch (e: Error) {

        }

        // Checking S
        try {
            foundNumbers.add(getNumberAtPosition(input, Pair(x, y+1)))
        } catch (e: Error) {

        }

        // Checking SW
        try {
            foundNumbers.add(getNumberAtPosition(input, Pair(x-1, y+1)))
        } catch (e: Error) {

        }

        // Checking W
        try {
            foundNumbers.add(getNumberAtPosition(input, Pair(x-1, y)))
        } catch (e: Error) {

        }

        // Checking NW
        try {
            foundNumbers.add(getNumberAtPosition(input, Pair(x-1, y-1)))
        } catch (e: Error) {

        }



        return foundNumbers
    }

    fun part1(input: List<String>): Int {
        // Visited numbers are kept track by their STARTING digit's position in the x, y cords
        val visitedNumbers = HashMap<Pair<Int, Int>, Int>()

        // Iterate and find each *
        // For each star, check adjacent
        // For each point in the number, add to a map of visited points (x,y)
        // If the number has been visited, don't
        input.forEachIndexed { y, line ->
            line.forEachIndexed { x, char ->

                if (!char.isDigit() && char != '.') {

                    val neighbors = findNeighborNumbers(input, Pair(x, y))
                    neighbors.forEach {
                        val (position, value) = it
                        visitedNumbers[position] = value
                    }
                }
            }
        }



        return visitedNumbers.values.sum()
    }



    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEachIndexed { y, line ->
            println("xxx")
            line.forEachIndexed { x, char ->

                if (!char.isDigit() && char == '*') {
                    //    logger.debug("Found symbol '$char' at $x, $y")
                    val neighbors = findNeighborNumbers(input, Pair(x, y))
                    val neighborsSet = HashMap<Pair<Int, Int>, Int>()
                    neighbors.forEach {
                        val (position, value) = it
                        neighborsSet[position] = value
                    }
                    if (neighborsSet.size == 2) {
                        println(neighborsSet.values)
                        sum += neighborsSet.values.reduce { acc, i -> acc * i }
                    }
                }
            }
        }
        return sum
    }


    val testInput1 = File("src/main/kotlin/day3/input.txt").readLines()

    // getNumberAtPosition(testInput1, Pair(1, 0))


    val testInput2 = testInput1
    val testInput2Result = part2(testInput2)
    val testInput2Expected = 467835
    check(testInput2Result == testInput2Expected) { "Expected $testInput2Result to equal $testInput2Expected"}

}
