enum class HandType {
    HIGH_CARD,
    PAIR,
    TWO_PAIR,
    TRIO,
    FULL,
    FOUR,
    FIVE
}

data class Card(val letter: Char, val jokers: Boolean = false) : Comparable<Card> {

    private val value: Int
        get() = when (letter) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> if (jokers) 1 else 11
            'T' -> 10
            else -> letter.digitToInt()
        }

    override fun compareTo(other: Card): Int {
        return value.compareTo(other.value)
    }
}

data class Hand(
    val cards: List<Card>,
    val bid: Long,
    val jokers: Boolean = false
) : Comparable<Hand> {

    constructor(cards: String, bid: Long, jokers: Boolean) : this(cards.map { Card(it, jokers) }, bid, jokers)

    override fun toString(): String {
        return cards.map(Card::letter).joinToString("")
    }

    private fun typeNoJokers(cardList: List<Card> = cards): HandType {
        val groups = cardList
            .groupBy { it }
            .mapValues { it.value.size }

        if (groups.size == 1) return HandType.FIVE

        val maxCount = groups.values.max()

        if (maxCount == 4) return HandType.FOUR

        if (maxCount == 2 && groups.size == 3) return HandType.TWO_PAIR

        val minCount = groups.values.min()

        if (maxCount == 3 && minCount == 2) return HandType.FULL

        return when (maxCount) {
            3 -> HandType.TRIO
            2 -> HandType.PAIR
            else -> HandType.HIGH_CARD
        }
    }

    private val type: HandType by lazy {
        if (!jokers) return@lazy typeNoJokers()

        val possibleCards = cards.filter { it.letter != 'J' }.distinct()

        if (possibleCards.isEmpty()) return@lazy HandType.FIVE //All jokers

        if (possibleCards.size == 4) return@lazy HandType.PAIR

        possibleCards.maxOfOrNull { possible ->
            val newCards = cards.toMutableList()
                .apply {
                    replaceAll {
                        if (it.letter == 'J') possible else it
                    }
                }
            typeNoJokers(newCards)
        } ?: HandType.HIGH_CARD
    }

    override fun compareTo(other: Hand): Int {
        val result = type.compareTo(other.type)

        if (result != 0) return result

        return cards.zip(other.cards)
            .map { it.first.compareTo(it.second) }
            .firstOrNull { it != 0 } ?: 0
    }
}

fun main() {
    fun winnings(input: List<String>, jokers: Boolean = false): Long {
        return input.asSequence()
            .map { it.split(" ") }
            .map { Hand(it[0], it[1].toLong(), jokers) }
            .sorted()
//            .onEach {
//                println("$it ${it.bid} ${it.type}")
//            }
            .mapIndexed { index, hand -> (index + 1) * hand.bid }
            .sum()
    }

    fun part1(input: List<String>): Long {
        return winnings(input)
    }

    fun part2(input: List<String>): Long {
        return winnings(input, true)
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}