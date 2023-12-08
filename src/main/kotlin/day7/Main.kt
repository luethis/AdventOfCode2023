package day7

import java.io.File

val input = File("src/main/kotlin/day7/input.txt")

val handRanking = mapOf(
    7 to listOf(5), // five of a kind
    6 to listOf(4, 1), // four of a kind
    5 to listOf(3, 2), // full house
    4 to listOf(3, 1, 1), // three of a kind
    3 to listOf(2, 2, 1), // two pairs
    2 to listOf(2, 1, 1, 1), // one pair
    1 to listOf(1, 1, 1, 1, 1) // high card
)

val charMapping = mapOf(
    "1" to "1",
    "2" to "2",
    "3" to "3",
    "4" to "4",
    "5" to "5",
    "6" to "6",
    "7" to "7",
    "8" to "8",
    "9" to "9",
    "T" to "A",
    "J" to "B",
    "Q" to "C",
    "K" to "D",
    "A" to "E"
)

fun main() {
    val handComparator = Comparator { hand1: Hand, hand2: Hand ->
        when {
            hand1.rank != hand2.rank -> hand1.rank.compareTo(hand2.rank)
            else -> hand1.mappedCards.compareTo(hand2.mappedCards)
        }
    }

    val hands = mutableListOf<Hand>()
    input.forEachLine {
        val (cards, score) = it.split(" ")
        val rank = calculateRank(cards)
        val hand = Hand(cards, score.toInt(), rank)
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

data class Hand(val cards: String, val bid: Int, val rank: Int) {
    val mappedCards = cards.mapNotNull { charMapping[it.toString()] }.joinToString(separator = "")
}