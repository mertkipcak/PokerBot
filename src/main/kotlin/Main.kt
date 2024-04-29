package org.mkipcak

import org.mkipcak.core.*
import org.mkipcak.services.OddsCalculatorMC

fun main() {
    val deck = Deck()
    deck.reset()
    deck.shuffle()

    val playerCards: List<Card> = listOf(
        Card(Rank.ACE, Suit.SPADES),
        Card(Rank.ACE, Suit.CLUBS)
    )
    val table: List<Card> = listOf(
        Card(Rank.QUEEN, Suit.HEARTS),
        Card(Rank.TWO, Suit.CLUBS),
        Card(Rank.THREE, Suit.SPADES)
    )

    val oddsCalculator = OddsCalculatorMC()

    val p = oddsCalculator.findProbability(playerCards, table, 3)

    print("Probability of winning is: $p")
}
