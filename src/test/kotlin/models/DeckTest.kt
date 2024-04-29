package org.mkipcak.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DeckTest {

    private lateinit var deck: Deck

    @BeforeEach
    fun setUp() {
        deck = Deck()
        deck.reset()
    }

    @Test
    fun testDeckSizeAfterReset() {
        assertEquals(52, deck.getCardCount(), "A new deck should have 52 cards.")
    }

    @Test
    fun testDeckSizeAfterClear() {
        deck.clear()
        assertEquals(0, deck.getCardCount(), "A cleared deck should have no cards.")
    }

    @Test
    fun testAddCard() {
        deck.clear()
        val card = Card(Rank.ACE, Suit.HEARTS)
        deck.addCard(card)
        assertEquals(1, deck.getCardCount(),
            "Adding a card to an empty deck should result in 1 card in the deck.")
        assertEquals(card, deck.draw(),
            "Drawn card should be the same as the added card")
    }

    @Test
    fun testDrawCard() {
        val drawnCard = deck.draw()
        assertNotNull(drawnCard, "Drawing from a non-empty deck should return a card.")
        assertEquals(51, deck.getCardCount(), "After drawing one card, 51 should remain.")
    }

    @Test
    fun testRemoveCard() {
        val cardToRemove = Card(Rank.FIVE, Suit.HEARTS)
        assertEquals(52, deck.getCardCount(), "Deck should have 53 cards after adding one.")

        deck.removeCard(cardToRemove)  // Now remove the card
        assertEquals(51, deck.getCardCount(), "Deck should have 52 cards after removing one.")
    }

    @Test
    fun testRemoveCardNotInDeck() {
        val cardNotInDeck = deck.draw()
        // Let's assume we haven't added this card, so it should not be in the deck
        deck.removeCard(cardNotInDeck)
        assertEquals(51, deck.getCardCount(), "Removing a card that is not in the deck should not change the deck size.")
    }
}
