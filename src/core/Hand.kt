package core

class Hand {
    private val cards: MutableList<Card> = mutableListOf()

    fun addCard(card: Card) {
        cards.add(card)
    }

    fun resetCards() {
        cards.clear()
    }

    fun getCards(): MutableList<Card> {
        return cards
    }

    fun score(): Int {
        return listOf(
            straightFlushScore(), fourOfAKindScore(), fullHouseScore(),
            flushScore(), straightScore(), threeOfAKindScore(),
            twoPairScore(), pairScore(), highCardScore()
        ).max()
    }

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

        return HandType.STRAIGHT_FLUSH.value + highestStraight.last().rank.value * 10
    }

    fun fourOfAKindScore(): Int {
        val groupedByNumber = cards.groupBy { it.rank }

        val fourKind = groupedByNumber.values.firstOrNull { it.size >= 4 }

        if (fourKind == null) return 0

        return HandType.FOUR_OF_A_KIND.value + fourKind[0].rank.value * 10
    }

    fun fullHouseScore(): Int {
        val groupedByNumber = cards.groupBy { it.rank }
        val threeKinds = groupedByNumber.values.filter { it.size >= 3 }
        var twoKinds = groupedByNumber.values.filter { it.size >= 2 }

        if (threeKinds.isEmpty() || twoKinds.size == 1) return 0

        val bestThreeKind = threeKinds.maxBy { it[0].rank }
        twoKinds = twoKinds.filter { it[0].rank != bestThreeKind[0].rank }
        val bestTwoKind = twoKinds.maxBy { it[0].rank }

        return HandType.FULL_HOUSE.value + bestThreeKind[0].rank.value * 100 + bestTwoKind[0].rank.value
    }

    fun flushScore(): Int {
        val groupedBySuit = cards.groupBy { it.suit }

        val flushSuit = groupedBySuit.values.firstOrNull { it.size >= 5 }


        if (flushSuit == null) return 0

        val highestCard = flushSuit.maxBy { it.rank }

        return HandType.FLUSH.value + highestCard.rank.value * 10
    }

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

        return HandType.STRAIGHT.value + highestStraight.last().value * 10
    }

    fun threeOfAKindScore(): Int {
        val groupedByNumber = cards.groupBy { it.rank }
        val threeKinds = groupedByNumber.values.filter { it.size >= 3 }

        if (threeKinds.isEmpty()) return 0

        val bestThreeKind = threeKinds.maxBy { it[0].rank }

        return HandType.THREE_OF_A_KIND.value + bestThreeKind[0].rank.value * 10
    }

    fun twoPairScore(): Int {
        val groupedByNumber = cards.groupBy { it.rank }
        var twoKinds = groupedByNumber.values.filter { it.size >= 2 }

        if (twoKinds.size < 2) return 0

        val bestTwoKind = twoKinds.maxBy { it[0].rank }
        twoKinds = twoKinds.filter { it[0].rank != bestTwoKind[0].rank }
        val secondBestTwoKind = twoKinds.maxBy { it[0].rank }

        return (HandType.TWO_PAIRS.value
                + bestTwoKind[0].rank.value * 100
                + secondBestTwoKind[0].rank.value)
    }

    fun pairScore(): Int {
        val groupedByNumber = cards.groupBy { it.rank }
        val twoKinds = groupedByNumber.values.filter { it.size >= 2 }

        if (twoKinds.isEmpty()) return 0

        val bestTwoKind = twoKinds.maxBy { it[0].rank }

        return HandType.PAIR.value + bestTwoKind[0].rank.value * 10
    }

    fun highCardScore(): Int {
        return HandType.HIGH_CARD.value + cards.maxBy { it.rank }.rank.value
    }
}