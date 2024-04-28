package core

/**
 * Represents a poker hand which can evaluate itself to determine the best poker hand score.
 */
class Hand {
    private val cards: MutableList<Card> = mutableListOf()

    /**
     * Adds a card to the hand.
     * @param card The card to be added.
     */
    fun addCard(card: Card) {
        cards.add(card)
    }

    /**
     * Clears all cards from the hand.
     */
    fun resetCards() {
        cards.clear()
    }

    /**
     * Returns a mutable list of cards in the hand.
     * @return MutableList of cards.
     */
    fun getCards(): MutableList<Card> {
        return cards
    }

    /**
     * Evaluates the hand and returns the highest score based on poker hand rankings.
     * @return The highest score from all possible poker hand evaluations.
     */
    fun score(): Int {
        return listOf(
            straightFlushScore(), fourOfAKindScore(), fullHouseScore(),
            flushScore(), straightScore(), threeOfAKindScore(),
            twoPairScore(), pairScore(), highCardScore()
        ).max()
    }

    /**
     * Calculates the score for a straight flush hand.
     * @return The score if a straight flush is found, otherwise zero.
     */
    fun straightFlushScore(): Int {
        val groupedBySuit = cards.groupBy { it.suit }

        var flushSuit = groupedBySuit.values.firstOrNull { it.size >= 5 }

        if (flushSuit == null) return 0

        flushSuit = flushSuit.sortedBy { it.rank }

        if (flushSuit.last().rank.value == 14) {
            flushSuit = listOf(Card(Rank.ONE, flushSuit.last().suit)) + flushSuit
        }

        var streak = mutableListOf(flushSuit[0])
        var highestStraight = listOf(flushSuit[0])

        for (i in 1 ..< flushSuit.size) {
            val lastCard = flushSuit[i-1]
            val currentCard = flushSuit[i]

            if (currentCard.rank.value != lastCard.rank.value + 1) {
                streak = mutableListOf(currentCard)
            } else {
                streak.add(currentCard)
                if (streak.size >= 5) {
                    highestStraight = streak.takeLast(5)
                }
            }
        }

        if (highestStraight.size < 5) return 0

        return calcStraightFlushScore(highestStraight.last().rank.value)
    }

    /**
     * Helper function to calculate straight flush score based on the highest card rank.
     * @param rank The highest card rank in the straight flush.
     * @return The calculated score.
     */
    fun calcStraightFlushScore(rank: Int): Int {
        return HandType.STRAIGHT_FLUSH.value + rank
    }

    /**
     * Calculates the score for a four of a kind hand.
     * @return The score if a four of a kind is found, otherwise zero.
     */
    fun fourOfAKindScore(): Int {
        val groupedByNumber = cards.groupBy { it.rank }

        val fourKind = groupedByNumber.values.firstOrNull { it.size >= 4 }

        if (fourKind == null) return 0

        return calcFourOfAKindScore(fourKind[0].rank.value)
    }

    /**
     * Helper function to calculate four of a kind score based on the rank.
     * @param rank The rank of the four of a kind.
     * @return The calculated score.
     */
    fun calcFourOfAKindScore(rank: Int): Int {
        return HandType.FOUR_OF_A_KIND.value + rank
    }

    /**
     * Calculates the score for a full house hand.
     * Identifies if the hand is a full house and returns the score based on the rank of three pair found.
     * @return The score if a full house is found, otherwise zero.
     */
    fun fullHouseScore(): Int {
        val groupedByNumber = cards.groupBy { it.rank }
        val threeKinds = groupedByNumber.values.filter { it.size >= 3 }
        var twoKinds = groupedByNumber.values.filter { it.size >= 2 }

        if (threeKinds.isEmpty() || twoKinds.size == 1) return 0

        val bestThreeKind = threeKinds.maxBy { it[0].rank }
        twoKinds = twoKinds.filter { it[0].rank != bestThreeKind[0].rank }
        val bestTwoKind = twoKinds.maxBy { it[0].rank }

        return calcFullHouseScore(bestThreeKind[0].rank.value, bestTwoKind[0].rank.value)
    }

    /**
     * Helper function to calculate full house score based on the ranks.
     * @param rank1 The rank of the three of a kind.
     * @param rank2 The rank of the pair.
     * @return The calculated score.
     */
    fun calcFullHouseScore(rank1: Int, rank2:Int): Int {
        return HandType.FULL_HOUSE.value + rank1 * 100 + rank2
    }

    /**
     * Calculates the score for a flush hand.
     * Identifies if the hand is a flush and returns the score based on the rank of highest card.
     * @return The score if a flush is found, otherwise zero.
     */
    fun flushScore(): Int {
        val groupedBySuit = cards.groupBy { it.suit }

        val flushSuit = groupedBySuit.values.firstOrNull { it.size >= 5 }


        if (flushSuit == null) return 0

        val highestCard = flushSuit.maxBy { it.rank }

        return calcFlushScore(highestCard.rank.value)
    }

    /**
     * Helper function to calculate flush score based on the max rank.
     * @param rank The rank of the highest card of flush.
     * @return The calculated score.
     */
    fun calcFlushScore(rank: Int): Int {
        return HandType.FLUSH.value + rank
    }

    /**
     * Calculates the score for a straight hand.
     * @return The score if a straight is found, otherwise zero.
     */
    fun straightScore(): Int {
        val groupedByNumber = cards.groupBy { it.rank }
        var numbers = groupedByNumber.keys.sorted()

        if (numbers.last().value == 14) {
            numbers = listOf(Rank.ONE) + numbers
        }

        var streak = mutableListOf(numbers[0])
        var highestStraight = listOf(numbers[0])

        for (i in 1 ..< numbers.size) {
            val lastRank = numbers[i-1]
            val currentRank = numbers[i]

            if (currentRank.value != lastRank.value + 1) {
                streak = mutableListOf(currentRank)
            } else {
                streak.add(currentRank)
                if (streak.size >= 5) {
                    highestStraight = streak.takeLast(5)
                }
            }
        }

        if (highestStraight.size < 5) return 0

        return calcStraightScore(highestStraight.last().value)
    }

    /**
     * Helper function to calculate straight score based on the max rank.
     * @param rank The rank of the highest card of straight.
     * @return The calculated score.
     */
    fun calcStraightScore(rank: Int): Int {
        return HandType.STRAIGHT.value + rank
    }

    /**
     * Calculates the score for a three of a kind hand.
     * @return The score if a three of a kind is found, otherwise zero.
     */
    fun threeOfAKindScore(): Int {
        val groupedByNumber = cards.groupBy { it.rank }
        val threeKinds = groupedByNumber.values.filter { it.size >= 3 }

        if (threeKinds.isEmpty()) return 0

        val bestThreeKind = threeKinds.maxBy { it[0].rank }

        return calcThreeOfAKindScore(bestThreeKind[0].rank.value)
    }

    /**
     * Helper function to calculate three of a kind score based on the max rank.
     * @param rank The rank of the highest three of a kind.
     * @return The calculated score.
     */
    fun calcThreeOfAKindScore(rank: Int): Int {
        return HandType.THREE_OF_A_KIND.value + rank
    }

    /**
     * Calculates the score for a two pair hand.
     * @return The score if a two pair is found, otherwise zero.
     */
    fun twoPairScore(): Int {
        val groupedByNumber = cards.groupBy { it.rank }
        var twoKinds = groupedByNumber.values.filter { it.size >= 2 }

        if (twoKinds.size < 2) return 0

        val bestTwoKind = twoKinds.maxBy { it[0].rank }
        twoKinds = twoKinds.filter { it[0].rank != bestTwoKind[0].rank }
        val secondBestTwoKind = twoKinds.maxBy { it[0].rank }

        return calcTwoPairScore(bestTwoKind[0].rank.value, secondBestTwoKind[0].rank.value)
    }

    /**
     * Helper function to calculate straight score based on the max rank.
     * @param rank1 The rank of the highest pair.
     * @param rank2 The rank of the second-highest pair.
     * @return The calculated score.
     */
    fun calcTwoPairScore(rank1: Int, rank2: Int): Int {
        return HandType.TWO_PAIRS.value + rank1 * 100 + rank2
    }

    /**
     * Calculates the score for pair hand.
     * @return The score if pair is found, otherwise zero.
     */
    fun pairScore(): Int {
        val groupedByNumber = cards.groupBy { it.rank }
        val twoKinds = groupedByNumber.values.filter { it.size >= 2 }

        if (twoKinds.isEmpty()) return 0

        val bestTwoKind = twoKinds.maxBy { it[0].rank }

        return calcPairScore(bestTwoKind[0].rank.value)
    }

    /**
     * Helper function to calculate pair score based on the max rank.
     * @param rank The rank of the highest pair.
     * @return The calculated score.
     */
    fun calcPairScore(rank: Int): Int {
        return HandType.PAIR.value + rank
    }

    /**
     * Calculates the score for highest card hand.
     * @return The rank of highest card.
     */
    fun highCardScore(): Int {
        return calcHighCardScore(cards.maxBy { it.rank }.rank.value)
    }

    /**
     * Helper function to calculate high card score based on the max rank.
     * @param rank The rank of the highest card.
     * @return The calculated score.
     */
    fun calcHighCardScore(rank: Int): Int {
        return HandType.HIGH_CARD.value + rank
    }
}