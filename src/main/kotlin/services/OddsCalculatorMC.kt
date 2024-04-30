package org.mkipcak.services

import org.mkipcak.core.*

class OddsCalculatorMC : OddsCalculator {
    private var nSamples: Int = 1000000

    fun setSampleCount(n: Int) {
        nSamples = n
    }

    override fun findProbability(playerHand: List<Card>, table: List<Card>, totalPlayers: Int): Probabilities {
        if (totalPlayers > 10) {
            println("Too many players")
            return Probabilities()
        }

        val deck = Deck()
        val player = Player()
        val otherPlayers: Array<Player> = Array(totalPlayers - 1) { Player() }
        val playerBuckets: Array<Int> = Array(HandType.entries.size) { 0 }
        val highestOtherPlayerBuckets: Array<Int> = Array(HandType.entries.size) { 0 }
        var wins = 0
        var ties = 0
        var otherWin = 0

        for (i in 1..nSamples) {
            deck.reset()
            deck.shuffle()

            player.resetCards()
            playerHand.forEach { deck.removeCard(it) }
            table.forEach { deck.removeCard(it) }

            playerHand.forEach { player.addCard(it) }
            val currentTable = table.toMutableList()

            // Complete the table with additional cards
            while (currentTable.size < 5) {
                currentTable.add(deck.draw())
            }

            // Distribute cards to other players
            var otherPlayersBestHand = Hand()
            otherPlayers.forEach { hand ->
                hand.resetCards()
                repeat(2) { hand.addCard(deck.draw()) }
                currentTable.forEach { hand.addCard(it) }
                val bestHand = hand.bestHand()
                otherPlayersBestHand = listOf(otherPlayersBestHand, bestHand).max()
            }

            // Add all table cards to player hand
            currentTable.forEach { player.addCard(it) }

            val playerBestHand = player.bestHand()
            val playerHandType = playerBestHand.type
            playerBuckets[playerHandType.ordinal]++

            val highestOtherHandType = otherPlayersBestHand.type
            highestOtherPlayerBuckets[highestOtherHandType.ordinal]++

            if (playerBestHand > otherPlayersBestHand) wins++
            else if (playerBestHand == otherPlayersBestHand) ties++

            else otherWin++
        }

        val simCount = nSamples.toDouble()

        val probabilities = Probabilities(
            playerWinChance = wins / simCount,
            playerTieChance = ties / simCount,
            playerHighCardChance = playerBuckets[HandType.HIGH_CARD.ordinal] / simCount,
            playerPairChance = playerBuckets[HandType.PAIR.ordinal] / simCount,
            playerTwoPairsChance = playerBuckets[HandType.TWO_PAIR.ordinal] / simCount,
            playerThreeOfAKindChance = playerBuckets[HandType.THREE_OF_A_KIND.ordinal] / simCount,
            playerStraightChance = playerBuckets[HandType.STRAIGHT.ordinal] / simCount,
            playerFlushChance = playerBuckets[HandType.FLUSH.ordinal] / simCount,
            playerFullHouseChance = playerBuckets[HandType.FULL_HOUSE.ordinal] / simCount,
            playerFourOfAKindChance = playerBuckets[HandType.FOUR_OF_A_KIND.ordinal] / simCount,
            playerStraightFlushChance = playerBuckets[HandType.STRAIGHT_FLUSH.ordinal] / simCount,
            otherPlayersWinChance = otherWin / simCount,
            otherPlayersHighCardChance = highestOtherPlayerBuckets[HandType.HIGH_CARD.ordinal] / simCount,
            otherPlayersPairChance = highestOtherPlayerBuckets[HandType.PAIR.ordinal] / simCount,
            otherPlayersTwoPairsChance = highestOtherPlayerBuckets[HandType.TWO_PAIR.ordinal] / simCount,
            otherPlayersThreeOfAKindChance = highestOtherPlayerBuckets[HandType.THREE_OF_A_KIND.ordinal] / simCount,
            otherPlayersStraightChance = highestOtherPlayerBuckets[HandType.STRAIGHT.ordinal] / simCount,
            otherPlayersFlushChance = highestOtherPlayerBuckets[HandType.FLUSH.ordinal] / simCount,
            otherPlayersFullHouseChance = highestOtherPlayerBuckets[HandType.FULL_HOUSE.ordinal] / simCount,
            otherPlayersFourOfAKindChance = highestOtherPlayerBuckets[HandType.FOUR_OF_A_KIND.ordinal] / simCount,
            otherPlayersStraightFlushChance = highestOtherPlayerBuckets[HandType.STRAIGHT_FLUSH.ordinal] / simCount
        )

        return probabilities
    }
}
