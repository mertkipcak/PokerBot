package org.mkipcak.core

/**
 * Represents a deck of playing cards.
 */
class Deck {
    // List of cards currently in the deck.
    private val cards: MutableList<Card> = mutableListOf()

    /**
     * Resets the deck to a standard deck of 52 playing cards.
     */
    fun reset() {
        cards.clear() // Clear any existing cards

        // Populate the deck with standard playing cards from 2 to Ace (14) across all four suits
        for (i in 2..14) {
            for (j in 1..4) {
                cards.add(Card(Rank.fromInt(i)!!, Suit.fromInt(j)!!))
            }
        }
    }

    /**
     * Clears all cards from the deck.
     */
    fun clear() {
        cards.clear()
    }

    /**
     * Adds a single card to the deck.
     * @param card The card to be added to the deck.
     */
    fun addCard(card: Card) {
        cards.add(card)
    }

    /**
     * Removes a specific card from the deck.
     * @param card The card to be removed from the deck.
     */
    fun removeCard(card: Card) {
        cards.remove(card)
    }

    /**
     * Shuffles the cards in the deck to randomize their order.
     */
    fun shuffle() {
        cards.shuffle()
    }

    /**
     * Draws the top card from the deck and removes it.
     * @return The card drawn from the deck.
     * @throws NoSuchElementException if the deck is empty.
     */
    fun draw(): Card {
        val card = cards.first() // Retrieves the first card
        cards.removeFirst() // Removes the first card from the deck
        return card
    }

    /**
     * Gets the number of cards currently in the deck.
     * @return The size of the deck.
     */
    fun getCardCount(): Int {
        return cards.size
    }
}
