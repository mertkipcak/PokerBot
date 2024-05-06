package org.mkipcak

import org.mkipcak.core.*
import org.mkipcak.services.OddsCalculatorMC

fun main() {
    val deck = Deck()
    deck.reset()
    deck.shuffle()

    val playerCards: List<Card> = listOf(
        Card(Rank.NINE, Suit.DIAMONDS),
        Card(Rank.SEVEN, Suit.CLUBS)
    )
    val table: List<Card> = listOf(
        Card(Rank.TEN, Suit.DIAMONDS),
        Card(Rank.TWO, Suit.CLUBS),
        Card(Rank.EIGHT, Suit.DIAMONDS),
        Card(Rank.TEN, Suit.HEARTS),
        Card(Rank.EIGHT, Suit.CLUBS),
    )

    val totalPlayerCount = 2

    val oddsCalculator = OddsCalculatorMC()

    val p = oddsCalculator.findProbability(playerCards, table, totalPlayerCount)

    print(p)
}
