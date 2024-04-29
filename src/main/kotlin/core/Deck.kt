package org.mkipcak.core

class Deck {
    private val cards: MutableList<Card> = mutableListOf()

    fun reset() {
        cards.clear()

        for (i in 2..14) {
            for (j in 1..4) {
                cards.add(Card(Rank.fromInt(i)!!, Suit.fromInt(j)!!))
            }
        }
    }

    fun clear() {
        cards.clear()
    }

    fun addCard(card: Card) {
        cards.add(card)
    }

    fun removeCard(card: Card) {
        cards.remove(card)
    }

    fun shuffle() {
        cards.shuffle()
    }

    fun draw(): Card {
        val card = cards[0]
        cards.removeFirst()
        return card
    }

    fun getCardCount(): Int {
        return cards.size
    }
}