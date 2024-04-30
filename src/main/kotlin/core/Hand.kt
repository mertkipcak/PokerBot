package org.mkipcak.core

class Hand(
    val type: HandType = HandType.HIGH_CARD,
    cards: List<Card> = listOf()
) : Comparable<Hand> {

    val cards: List<Card> = cards.toList()

    override fun compareTo(other: Hand): Int {
        return if (this.type.value != other.type.value) {
            this.type.value.compareTo(other.type.value)
        } else {
            this.compareCardRanks(other)
        }
    }

    private fun compareCardRanks(other: Hand): Int {
        for (i in this.cards.indices) {
            val rankComparison = this.cards[i].rank.compareTo(other.cards[i].rank)
            if (rankComparison != 0) {
                return rankComparison
            }
        }
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Hand

        if (type != other.type) return false
        if (cards.map { it.rank } != other.cards.map { it.rank }) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + cards.hashCode()
        return result
    }
}
