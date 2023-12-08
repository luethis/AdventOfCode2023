package day7

import java.io.File

val input = File("src/main/kotlin/day7/input.txt")

val isPart2 = true

private val handRanking = mapOf(
    7 to listOf(5), // five of a kind
    6 to listOf(4, 1), // four of a kind
    5 to listOf(3, 2), // full house
    4 to listOf(3, 1, 1), // three of a kind
    3 to listOf(2, 2, 1), // two pairs
    2 to listOf(2, 1, 1, 1), // one pair
    1 to listOf(1, 1, 1, 1, 1) // high card
)

private fun calculateJokerRank(rank: Int, jokerCount: Int) =
    when (rank to jokerCount) {
        1 to 1 -> 2
        1 to 2 -> 4
        1 to 3 -> 6
        1 to 4 -> 7
        1 to 5 -> 7
        2 to 1 -> 4
        2 to 2 -> 6
        2 to 3 -> 7
        3 to 1 -> 5
        4 to 1 -> 6
        4 to 2 -> 7
        6 to 1 -> 7
        else -> rank
    }

private val charMapping = mapOf(
    "2" to "2",
    "3" to "3",
    "4" to "4",
    "5" to "5",
    "6" to "6",
    "7" to "7",
    "8" to "8",
    "9" to "9",
    "T" to "A",
    "J" to if (isPart2) "1" else "B",
    "Q" to "C",
    "K" to "D",
    "A" to "E"
)


fun main() {
    val handComparator = Comparator { hand1: Hand, hand2: Hand ->
        when {
            isPart2 && hand1.jokerRank != hand2.jokerRank -> hand1.jokerRank.compareTo(hand2.jokerRank)
            !isPart2 && hand1.rank != hand2.rank -> hand1.rank.compareTo(hand2.rank)
            else -> hand1.mappedCards.compareTo(hand2.mappedCards)
        }
    }

    val hands = mutableListOf<Hand>()
    input.forEachLine {
        val (cards, score) = it.split(" ")
        val rank = calculateRank(cards)
        val jokerRank = calculateJokerRank(cards)
        val hand = Hand(cards, score.toInt(), rank, jokerRank)
        hands.add(hand)
    }

    val result = hands.sortedWith(handComparator)

    var sum = 0
    result.forEachIndexed { index, hand ->
        sum += ((index + 1) * hand.bid)
    }

    print(sum)
}

fun calculateRank(cards: String): Int {
    val comb = cards.groupBy { it }.map { it.value.size }.sortedDescending()
    return handRanking.entries.single { possibility -> possibility.value == comb }.key
}

fun calculateJokerRank(cards: String): Int {
    val jokerCount = cards.count { it == 'J' }
    val normalizedCards = cards.replace("J", "")

    val comb = normalizedCards.groupBy { it }.map { it.value.size }.sortedDescending().toMutableList()
    while (comb.sumOf { it } != 5) {
        comb.add(1)
    }
    val rank = handRanking.entries.single { possibility -> possibility.value == comb }.key
    return calculateJokerRank(rank, jokerCount)
}

data class Hand(val cards: String, val bid: Int, val rank: Int, val jokerRank: Int) {
    val mappedCards = cards.mapNotNull { charMapping[it.toString()] }.joinToString(separator = "")
}