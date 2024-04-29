package org.mkipcak

import org.mkipcak.core.*

fun main() {
    val deck = Deck()
    deck.reset()
    deck.shuffle()

    val player1 = Hand()
    val player2 = Hand()

    for (i in 1..5) {
        player1.addCard(deck.draw())
        player2.addCard(deck.draw())
    }

    println(player1.getCards())
    println(player2.getCards())

    val p1wins = player1.score() > player2.score()
    if (p1wins) {
        println("Player 1 wins")
    } else {
        println("Player 2 wins")
    }
    println(deck)
}
