package org.mkipcak.core

/**
 * Represents a player hand which can evaluate the best hand out of the cards
 */
class Player {
    private val cards: MutableList<Card> = mutableListOf()

    /**
     * Adds a card to the player's hand.
     * @param card The card to be added.
     */
    fun addCard(card: Card) {
        cards.add(card)
    }

    /**
     * Clears all cards from the player's hand.
     */
    fun resetCards() {
        cards.clear()
    }

    /**
     * Set player cards to given cards
     * @param newCards new cards to set the hand to
     */
    fun setCards(newCards: List<Card>) {
        cards.clear()
        for (card in cards) {
            cards.add(card)
        }
    }

    /**
     * Returns a mutable list of cards in the hand.
     * @return MutableList of cards.
     */
    fun getCards(): MutableList<Card> {
        return cards
    }

    /**
     * Evaluates the hand and returns the best hand based on poker hand rankings.
     * @return The best hand from all possible poker hand evaluations.
     */
    fun bestHand(): Hand {
        val hands = listOf(
            straightFlushHand(), fourOfAKindHand(), fullHouseHand(),
            flushHand(), straightHand(), threeOfAKindHand(),
            twoPairHand(), pairHand(), highCardHand()
        )
        val validHands = hands.filterNotNull()

        return validHands.max()
    }

    /**
     * Calculates the score for a straight flush hand.
     * @return The score if a straight flush is found, otherwise zero.
     */
    fun straightFlushHand(): Hand?  {
        val groupedBySuit = cards.groupBy { it.suit }

        var flushSuit = groupedBySuit.values.firstOrNull { it.size >= 5 }

        if (flushSuit == null) return null

        flushSuit = flushSuit.sortedBy { it.rank }

        if (flushSuit.last().rank.value == 14) {
            flushSuit = listOf(Card(Rank.ONE, flushSuit.last().suit)) + flushSuit
        }

        var streak = mutableListOf(flushSuit[0])
        var highestStraight = mutableListOf(flushSuit[0])

        for (i in 1 ..< flushSuit.size) {
            val lastCard = flushSuit[i-1]
            val currentCard = flushSuit[i]

            if (currentCard.rank.value != lastCard.rank.value + 1) {
                streak = mutableListOf(currentCard)
            } else {
                streak.add(currentCard)
                if (streak.size >= 5) {
                    highestStraight = streak.takeLast(5).toMutableList()
                }
            }
        }

        if (highestStraight.size < 5) return null

        if (highestStraight[0].rank == Rank.ONE) {
            highestStraight[0] = Card(Rank.ACE, highestStraight[0].suit)
        }

        return Hand(HandType.STRAIGHT_FLUSH, highestStraight.toList())
    }

    /**
     * Calculates the score for a four of a kind hand.
     * @return The score if a four of a kind is found, otherwise zero.
     */
    fun fourOfAKindHand(): Hand? {
        val groupedByNumber = cards.groupBy { it.rank }

        val fourKind = groupedByNumber.values.firstOrNull { it.size >= 4 }

        if (fourKind == null) return null

        val highestKicker = cards.filter { fourKind[0].rank != it.rank }

        val hand = fourKind + highestKicker

        return Hand(HandType.FOUR_OF_A_KIND, hand)
    }

    /**
     * Calculates the score for a full house hand.
     * Identifies if the hand is a full house and returns the score based on the rank of three pair found.
     * @return The score if a full house is found, otherwise zero.
     */
    fun fullHouseHand(): Hand? {
        val groupedByNumber = cards.groupBy { it.rank }
        val threeKinds = groupedByNumber.values.filter { it.size >= 3 }
        var twoKinds = groupedByNumber.values.filter { it.size >= 2 }

        if (threeKinds.isEmpty() || twoKinds.size == 1) return null

        val bestThreeKind = threeKinds.maxBy { it[0].rank }
        twoKinds = twoKinds.filter { it[0].rank != bestThreeKind[0].rank }
        val bestTwoKind = twoKinds.maxBy { it[0].rank }

        return Hand(HandType.FULL_HOUSE, bestThreeKind + bestTwoKind)
    }

    /**
     * Calculates the score for a flush hand.
     * Identifies if the hand is a flush and returns the score based on the rank of highest card.
     * @return The score if a flush is found, otherwise zero.
     */
    fun flushHand(): Hand? {
        val groupedBySuit = cards.groupBy { it.suit }

        val flushSuit = groupedBySuit.values.firstOrNull { it.size >= 5 }

        if (flushSuit == null) return null

        val sortedFlushSuit = flushSuit.sortedBy { it.rank }

        return Hand(HandType.FLUSH, sortedFlushSuit.takeLast(5).reversed())
    }

    /**
     * Calculates the score for a straight hand.
     * @return The score if a straight is found, otherwise zero.
     */
    fun straightHand(): Hand? {
        var sortedByNumber = cards.sortedBy { it.rank }

        if (sortedByNumber.last().rank == Rank.ACE) {
            sortedByNumber = listOf(Card(Rank.ONE, sortedByNumber.last().suit)) + sortedByNumber
        }

        var streak = mutableListOf(sortedByNumber[0])
        var highestStraight = mutableListOf(sortedByNumber[0])

        for (i in 1 ..< sortedByNumber.size) {
            val lastCard = sortedByNumber[i-1]
            val currentCard = sortedByNumber[i]

            if (currentCard.rank == lastCard.rank){
                continue
            } else if (currentCard.rank.value == lastCard.rank.value + 1) {
                streak.add(currentCard)
                if (streak.size >= 5) {
                    highestStraight = streak.takeLast(5).toMutableList()
                }
            } else {
                streak = mutableListOf(currentCard)
            }
        }

        if (highestStraight.size < 5) return null

        if (highestStraight[0].rank == Rank.ONE) {
            highestStraight[0] = Card(Rank.ACE, highestStraight[0].suit)
        }

        return Hand(HandType.STRAIGHT, highestStraight.toList())
    }

    /**
     * Calculates the score for a three of a kind hand.
     * @return The score if a three of a kind is found, otherwise zero.
     */
    fun threeOfAKindHand(): Hand? {
        val groupedByNumber = cards.groupBy { it.rank }
        val threeKinds = groupedByNumber.values.filter { it.size >= 3 }

        if (threeKinds.isEmpty()) return null

        val bestThreeKind = threeKinds.maxBy { it[0].rank }

        val remainingCards = cards.filter { it !in bestThreeKind }
        val kickers = remainingCards.sortedBy { it.rank }.takeLast(2)

        return Hand(HandType.THREE_OF_A_KIND, bestThreeKind + kickers.reversed())
    }

    /**
     * Calculates the score for a two pair hand.
     * @return The score if a two pair is found, otherwise zero.
     */
    fun twoPairHand(): Hand? {
        val groupedByNumber = cards.groupBy { it.rank }
        var twoKinds = groupedByNumber.values.filter { it.size >= 2 }

        if (twoKinds.size < 2) return null

        val bestTwoKind = twoKinds.maxBy { it[0].rank }.take(2)
        twoKinds = twoKinds.filter { it[0].rank != bestTwoKind[0].rank }
        val secondBestTwoKind = twoKinds.maxBy { it[0].rank }.take(2)

        val remainingCards = cards.filter { it !in (bestTwoKind + secondBestTwoKind) }
        val kicker = remainingCards.maxBy { it.rank }

        return Hand(HandType.TWO_PAIR, bestTwoKind + secondBestTwoKind + listOf(kicker))
    }

    /**
     * Calculates the score for pair hand.
     * @return The score if pair is found, otherwise zero.
     */
    fun pairHand(): Hand? {
        val groupedByNumber = cards.groupBy { it.rank }
        val twoKinds = groupedByNumber.values.filter { it.size >= 2 }

        if (twoKinds.isEmpty()) return null

        val bestTwoKind = twoKinds.maxBy { it[0].rank }

        val remainingCards = cards.filter { it !in bestTwoKind }
        val kickers = remainingCards.sortedBy { it.rank }.takeLast(3).reversed()

        return Hand(HandType.PAIR, bestTwoKind + kickers)
    }

    /**
     * Calculates the score for highest card hand.
     * @return The rank of highest card.
     */
    fun highCardHand(): Hand {
        return Hand(HandType.HIGH_CARD, cards.sortedBy { it.rank }.takeLast(5).reversed())
    }

    override fun toString(): String {
        return cards.toString()
    }
}