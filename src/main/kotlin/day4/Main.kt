package day4

import java.io.File

val input = File("src/main/kotlin/day4/input.txt")

fun main() {
    hard()
}

fun easy() {
    var score = 0

    input.readLines().forEach { card ->
        val winningNumbers = card.split("|")[0].split(":")[1].split(" ").mapNotNull { it.toIntOrNull() }
        val cardNumbers = card.split("|")[1].split(" ").mapNotNull { it.toIntOrNull() }

        var cardScore = 0
        winningNumbers.forEach { currentNumber ->
            if (cardNumbers.contains(currentNumber)) {
                cardScore = (cardScore * 2).coerceAtLeast(1)
            }
        }
        score += cardScore
    }
    println(score)
}

var total = 0

fun hard() {
    val input = input.readLines()

    input.forEachIndexed { index, _ ->
        evaluate(input, index)
    }
}

private fun evaluate(input: List<String>, index: Int) {
    input.getOrNull(index)?.let {

        total++
        println("proceeded card $index")

        val winningNumbers = it.split("|")[0].split(":")[1].split(" ").mapNotNull { it.toIntOrNull() }
        val cardNumbers = it.split("|")[1].split(" ").mapNotNull { it.toIntOrNull() }

        winningNumbers.forEach { currentNumber ->
            if (cardNumbers.contains(currentNumber)) {
                evaluate(input, index + 1)
            }
        }
    }
}

fun faebeli() {
    val input = input.readLines()
    var score = 0

    input.forEach { card ->
        val input = card.split("|")
        val winningNumbers = input[0].split(":")[1].split(" ").mapNotNull { it.toIntOrNull() }
        val cardNumbers = input[1].split(" ").mapNotNull { it.toIntOrNull() }

        var cardCounter = 0
        winningNumbers.forEach { currentNumber ->
            val match = cardNumbers.any { cardNumber ->
                currentNumber == cardNumber
            }
            if (match && cardCounter == 0) {
                cardCounter = 1
            } else if (match && cardCounter > 0) {
                cardCounter *= 2
            }

        }
        score += cardCounter
    }
    println(score)
}
