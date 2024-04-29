package org.mkipcak.services

import org.mkipcak.core.Card
import org.mkipcak.core.Deck
import org.mkipcak.core.Hand
import org.mkipcak.core.HandType
import kotlin.math.max

class OddsCalculatorMC : OddsCalculator {
    private var nSamples: Int = 1000000

    fun setSampleCount(n: Int) {
        nSamples = n
    }

    override fun findProbability(playerHand: List<Card>, table: List<Card>, totalPlayers: Int): Double {
        if (totalPlayers > 10) {
            println("Too many players")
            return 0.0
        }

        val deck = Deck()
        val player = Hand()
        val otherPlayers: Array<Hand> = Array(totalPlayers - 1) { Hand() }
        val playerBuckets: Array<Int> = Array(HandType.entries.size) { 0 }
        val highestOtherPlayerBuckets: Array<Int> = Array(HandType.entries.size) { 0 }
        var wins = 0

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
            var highestOtherScore = 0
            otherPlayers.forEach { hand ->
                hand.resetCards()
                repeat(2) { hand.addCard(deck.draw()) }
                currentTable.forEach { hand.addCard(it) }
                val score = hand.score()
                highestOtherScore = max(highestOtherScore, score)
            }

            // Add all table cards to player hand
            currentTable.forEach { player.addCard(it) }

            val playerScore = player.score()
            val playerHandType = HandType.entries.find { it.value == playerScore / 10000 * 10000 } ?: HandType.HIGH_CARD
            playerBuckets[playerHandType.ordinal]++

            val highestOtherHandType = HandType.entries.find { it.value == highestOtherScore / 10000 * 10000 } ?: HandType.HIGH_CARD
            highestOtherPlayerBuckets[highestOtherHandType.ordinal]++

            if (playerScore > highestOtherScore) wins++
        }

        return wins / nSamples.toDouble()
    }
}
