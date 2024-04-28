package core

import kotlin.test.Test
import kotlin.test.assertEquals

class HandTest {
    /************************************************* BASIC TESTS ****************************************************/

    @Test
    fun testAddCardAndReset() {
        val hand = Hand()
        hand.addCard(Card(Rank.TWO, Suit.HEARTS))
        assertEquals(1, hand.getCards().size, "Hand should contain one card")

        hand.resetCards()
        assertEquals(0, hand.getCards().size, "Hand should be empty after reset")
    }

    /********************************************** STRAIGHT FLUSH TESTS **********************************************/

    @Test
    fun testStraightFlushHigh() {
        val hand = Hand()
        hand.addCard(Card(Rank.TEN, Suit.HEARTS))
        hand.addCard(Card(Rank.JACK, Suit.HEARTS))
        hand.addCard(Card(Rank.QUEEN, Suit.HEARTS))
        hand.addCard(Card(Rank.KING, Suit.HEARTS))
        hand.addCard(Card(Rank.ACE, Suit.HEARTS))
        assertEquals(HandType.STRAIGHT_FLUSH.value + Rank.ACE.value * 10, hand.straightFlushScore(), "Score should reflect a high straight flush")
    }

    @Test
    fun testStraightFlushLow() {
        val hand = Hand()
        hand.addCard(Card(Rank.ACE, Suit.CLUBS))
        hand.addCard(Card(Rank.TWO, Suit.CLUBS))
        hand.addCard(Card(Rank.THREE, Suit.CLUBS))
        hand.addCard(Card(Rank.FOUR, Suit.CLUBS))
        hand.addCard(Card(Rank.FIVE, Suit.CLUBS))
        assertEquals(HandType.STRAIGHT_FLUSH.value + Rank.FIVE.value * 10, hand.straightFlushScore(), "Score should reflect a low straight flush (Ace through Five)")
    }

    @Test
    fun testNotAStraightFlushDueToSuits() {
        val hand = Hand()
        hand.addCard(Card(Rank.TEN, Suit.HEARTS))
        hand.addCard(Card(Rank.JACK, Suit.HEARTS))
        hand.addCard(Card(Rank.QUEEN, Suit.HEARTS))
        hand.addCard(Card(Rank.KING, Suit.SPADES))
        hand.addCard(Card(Rank.ACE, Suit.HEARTS))
        assertEquals(0, hand.straightFlushScore(), "Should not score as straight flush due to mismatched suits")
    }

    @Test
    fun testNotAStraightFlushDueToSequence() {
        val hand = Hand()
        hand.addCard(Card(Rank.TEN, Suit.CLUBS))
        hand.addCard(Card(Rank.JACK, Suit.CLUBS))
        hand.addCard(Card(Rank.QUEEN, Suit.CLUBS))
        hand.addCard(Card(Rank.ACE, Suit.CLUBS))
        hand.addCard(Card(Rank.NINE, Suit.CLUBS))
        assertEquals(0, hand.straightFlushScore(), "Should not score as straight flush due to broken sequence")
    }

    @Test
    fun testStraightFlushWithExtraCards() {
        val hand = Hand()
        hand.addCard(Card(Rank.NINE, Suit.DIAMONDS))
        hand.addCard(Card(Rank.TEN, Suit.DIAMONDS))
        hand.addCard(Card(Rank.JACK, Suit.DIAMONDS))
        hand.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        hand.addCard(Card(Rank.KING, Suit.DIAMONDS))
        hand.addCard(Card(Rank.TWO, Suit.HEARTS)) // Irrelevant card
        assertEquals(HandType.STRAIGHT_FLUSH.value + Rank.KING.value * 10, hand.straightFlushScore(), "Score should reflect the highest straight flush regardless of extra cards")
    }

    /********************************************** FOUR OF A KIND TESTS **********************************************/

    @Test
    fun testHighCardScore() {
        val hand = Hand()
        hand.addCard(Card(Rank.TEN, Suit.CLUBS))
        hand.addCard(Card(Rank.ACE, Suit.DIAMONDS))
        hand.addCard(Card(Rank.FOUR, Suit.HEARTS))
        hand.addCard(Card(Rank.KING, Suit.SPADES))
        hand.addCard(Card(Rank.THREE, Suit.CLUBS))
        assertEquals(HandType.HIGH_CARD.value + Rank.ACE.value, hand.score(), "Score should be calculated as high card")
    }

    @Test
    fun testPairScore() {
        val hand = Hand()
        hand.addCard(Card(Rank.TEN, Suit.CLUBS))
        hand.addCard(Card(Rank.TEN, Suit.DIAMONDS))
        hand.addCard(Card(Rank.FOUR, Suit.HEARTS))
        hand.addCard(Card(Rank.KING, Suit.SPADES))
        hand.addCard(Card(Rank.THREE, Suit.CLUBS))
        assertEquals(HandType.PAIR.value + Rank.TEN.value * 10, hand.score(), "Score should reflect a pair")
    }

    @Test
    fun testStraightFlushScore() {
        val hand = Hand()
        hand.addCard(Card(Rank.NINE, Suit.HEARTS))
        hand.addCard(Card(Rank.TEN, Suit.HEARTS))
        hand.addCard(Card(Rank.JACK, Suit.HEARTS))
        hand.addCard(Card(Rank.QUEEN, Suit.HEARTS))
        hand.addCard(Card(Rank.KING, Suit.HEARTS))
        assertEquals(HandType.STRAIGHT_FLUSH.value + Rank.KING.value * 10, hand.score(), "Score should reflect a straight flush")
    }

}
