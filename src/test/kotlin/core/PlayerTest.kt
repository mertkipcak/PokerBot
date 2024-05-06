package org.mkipcak.core

import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerTest {
    /************************************************* BASIC TESTS ****************************************************/

    @Test
    fun testAddCardAndReset() {
        val player = Player()
        player.addCard(Card(Rank.TWO, Suit.HEARTS))
        assertEquals(1, player.getCards().size, "Hand should contain one card")

        player.resetCards()
        assertEquals(0, player.getCards().size, "Hand should be empty after reset")
    }

    /********************************************** STRAIGHT FLUSH TESTS **********************************************/

    @Test
    fun testStraightFlushHigh() {
        val player = Player()
        player.addCard(Card(Rank.TEN, Suit.HEARTS))
        player.addCard(Card(Rank.JACK, Suit.HEARTS))
        player.addCard(Card(Rank.QUEEN, Suit.HEARTS))
        player.addCard(Card(Rank.KING, Suit.HEARTS))
        player.addCard(Card(Rank.ACE, Suit.HEARTS))

        val expectedHand = Hand(
            HandType.STRAIGHT_FLUSH,
            listOf(
                Card(Rank.TEN, Suit.HEARTS),
                Card(Rank.JACK, Suit.HEARTS),
                Card(Rank.QUEEN, Suit.HEARTS),
                Card(Rank.KING, Suit.HEARTS),
                Card(Rank.ACE, Suit.HEARTS)
            )
        )

        assertEquals(expectedHand, player.straightFlushHand(),
            "Hand should reflect a high straight flush")
    }

    @Test
    fun testStraightFlushLow() {
        val player = Player()
        player.addCard(Card(Rank.ACE, Suit.CLUBS))
        player.addCard(Card(Rank.TWO, Suit.CLUBS))
        player.addCard(Card(Rank.THREE, Suit.CLUBS))
        player.addCard(Card(Rank.FOUR, Suit.CLUBS))
        player.addCard(Card(Rank.FIVE, Suit.CLUBS))

        val expectedHand = Hand(
            HandType.STRAIGHT_FLUSH,
            listOf(
                Card(Rank.ACE, Suit.CLUBS),
                Card(Rank.TWO, Suit.CLUBS),
                Card(Rank.THREE, Suit.CLUBS),
                Card(Rank.FOUR, Suit.CLUBS),
                Card(Rank.FIVE, Suit.CLUBS),
            )
        )

        assertEquals(expectedHand, player.straightFlushHand(),
            "Hand should reflect a low straight flush (Ace through Five)")
    }

    @Test
    fun testNotAStraightFlushDueToSuits() {
        val player = Player()
        player.addCard(Card(Rank.TEN, Suit.HEARTS))
        player.addCard(Card(Rank.JACK, Suit.HEARTS))
        player.addCard(Card(Rank.QUEEN, Suit.HEARTS))
        player.addCard(Card(Rank.KING, Suit.SPADES))
        player.addCard(Card(Rank.ACE, Suit.HEARTS))

        val expectedHand = null

        assertEquals(expectedHand, player.straightFlushHand(),
            "Should not score as straight flush due to mismatched suits")
    }

    @Test
    fun testNotAStraightFlushDueToSequence() {
        val player = Player()
        player.addCard(Card(Rank.TEN, Suit.CLUBS))
        player.addCard(Card(Rank.JACK, Suit.CLUBS))
        player.addCard(Card(Rank.QUEEN, Suit.CLUBS))
        player.addCard(Card(Rank.ACE, Suit.CLUBS))
        player.addCard(Card(Rank.NINE, Suit.CLUBS))

        val expectedHand = null

        assertEquals(expectedHand, player.straightFlushHand(),
            "Should not score as straight flush due to broken sequence")
    }

    @Test
    fun testStraightFlushWithExtraCard() {
        val player = Player()
        player.addCard(Card(Rank.NINE, Suit.DIAMONDS))
        player.addCard(Card(Rank.TEN, Suit.DIAMONDS))
        player.addCard(Card(Rank.JACK, Suit.DIAMONDS))
        player.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        player.addCard(Card(Rank.KING, Suit.DIAMONDS))
        player.addCard(Card(Rank.EIGHT, Suit.DIAMONDS)) // Irrelevant card

        val expectedHand = Hand(
            HandType.STRAIGHT_FLUSH,
            listOf(
                Card(Rank.NINE, Suit.DIAMONDS),
                Card(Rank.TEN, Suit.DIAMONDS),
                Card(Rank.JACK, Suit.DIAMONDS),
                Card(Rank.QUEEN, Suit.DIAMONDS),
                Card(Rank.KING, Suit.DIAMONDS)
            )
        )

        assertEquals(expectedHand, player.straightFlushHand(),
            "Hand should reflect the highest straight flush regardless of extra cards")
    }

    /********************************************** FOUR OF A KIND TESTS **********************************************/

    @Test
    fun testFourOfAKindValid() {
        val player = Player()

        player.addCard(Card(Rank.QUEEN, Suit.HEARTS))
        player.addCard(Card(Rank.QUEEN, Suit.CLUBS))
        player.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        player.addCard(Card(Rank.QUEEN, Suit.SPADES))
        player.addCard(Card(Rank.THREE, Suit.HEARTS))

        val expectedHand = Hand(
            HandType.FOUR_OF_A_KIND,
            listOf(
                Card(Rank.QUEEN, Suit.HEARTS),
                Card(Rank.QUEEN, Suit.CLUBS),
                Card(Rank.QUEEN, Suit.DIAMONDS),
                Card(Rank.QUEEN, Suit.SPADES),
                Card(Rank.THREE, Suit.HEARTS)
            )
        )

        assertEquals(expectedHand, player.fourOfAKindHand(),
            "Hand should reflect a four of a kind of Queens")
    }

    @Test
    fun testFourOfAKindInvalid() {
        val player = Player()

        player.addCard(Card(Rank.QUEEN, Suit.HEARTS))
        player.addCard(Card(Rank.QUEEN, Suit.CLUBS))
        player.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        player.addCard(Card(Rank.THREE, Suit.SPADES))
        player.addCard(Card(Rank.THREE, Suit.HEARTS))

        val expectedHand = null

        assertEquals(expectedHand, player.fourOfAKindHand(),
            "Hand should be zero as there is no four of a kind")
    }

    /************************************************ FULL HOUSE TESTS ************************************************/

    @Test
    fun testFullHouseValid() {
        val player = Player()

        player.addCard(Card(Rank.QUEEN, Suit.HEARTS))
        player.addCard(Card(Rank.QUEEN, Suit.CLUBS))
        player.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        player.addCard(Card(Rank.THREE, Suit.SPADES))
        player.addCard(Card(Rank.THREE, Suit.HEARTS))

        val expectedHand = Hand(
            HandType.FULL_HOUSE,
            listOf(
                Card(Rank.QUEEN, Suit.HEARTS),
                Card(Rank.QUEEN, Suit.CLUBS),
                Card(Rank.QUEEN, Suit.DIAMONDS),
                Card(Rank.THREE, Suit.SPADES),
                Card(Rank.THREE, Suit.HEARTS)
            )
        )

        assertEquals(expectedHand, player.fullHouseHand(),
            "Hand should reflect a full house of Queens and Three")
    }

    @Test
    fun testFullHouseInvalid() {
        val player = Player()

        player.addCard(Card(Rank.QUEEN, Suit.HEARTS))
        player.addCard(Card(Rank.QUEEN, Suit.CLUBS))
        player.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        player.addCard(Card(Rank.TWO, Suit.SPADES))
        player.addCard(Card(Rank.THREE, Suit.HEARTS))

        assertEquals(null, player.fullHouseHand(),
            "Hand should be zero as there is no full house")
    }

    /*************************************************** FLUSH TESTS **************************************************/

    @Test
    fun testValidFlushScore() {
        val player = Player()

        player.addCard(Card(Rank.TWO, Suit.HEARTS))
        player.addCard(Card(Rank.THREE, Suit.HEARTS))
        player.addCard(Card(Rank.FOUR, Suit.HEARTS))
        player.addCard(Card(Rank.FIVE, Suit.HEARTS))
        player.addCard(Card(Rank.SIX, Suit.HEARTS))

        val expectedHand = Hand(
            HandType.FLUSH,
            listOf(
                Card(Rank.SIX, Suit.HEARTS),
                Card(Rank.FIVE, Suit.HEARTS),
                Card(Rank.FOUR, Suit.HEARTS),
                Card(Rank.THREE, Suit.HEARTS),
                Card(Rank.TWO, Suit.HEARTS),
            )
        )

        assertEquals(expectedHand, player.flushHand(),
            "Flush hand should be calculated correctly for a valid flush")
    }

    @Test
    fun testValidFlushWithExtraCards() {
        val player = Player()

        player.addCard(Card(Rank.TWO, Suit.HEARTS))
        player.addCard(Card(Rank.THREE, Suit.HEARTS))
        player.addCard(Card(Rank.FOUR, Suit.HEARTS))
        player.addCard(Card(Rank.FIVE, Suit.HEARTS))
        player.addCard(Card(Rank.SIX, Suit.HEARTS))
        player.addCard(Card(Rank.SIX, Suit.CLUBS))

        val expectedHand = Hand(
            HandType.FLUSH,
            listOf(
                Card(Rank.SIX, Suit.HEARTS),
                Card(Rank.FIVE, Suit.HEARTS),
                Card(Rank.FOUR, Suit.HEARTS),
                Card(Rank.THREE, Suit.HEARTS),
                Card(Rank.TWO, Suit.HEARTS),
            )
        )

        assertEquals(expectedHand, player.flushHand(),
            "Flush hand should be calculated correctly for a valid flush")
    }

    @Test
    fun testInvalidFlushScoreDueToMixedSuits() {
        val player = Player()

        player.addCard(Card(Rank.TWO, Suit.HEARTS))
        player.addCard(Card(Rank.THREE, Suit.CLUBS))
        player.addCard(Card(Rank.FOUR, Suit.DIAMONDS))
        player.addCard(Card(Rank.FIVE, Suit.SPADES))
        player.addCard(Card(Rank.SIX, Suit.HEARTS))

        assertEquals(null, player.flushHand(),
            "Flush hand should be zero when cards are of mixed suits")
    }

    /************************************************* STRAIGHT TESTS *************************************************/

    @Test
    fun testStandardStraight() {
        val player = Player()
        player.addCard(Card(Rank.FIVE, Suit.CLUBS))
        player.addCard(Card(Rank.SIX, Suit.HEARTS))
        player.addCard(Card(Rank.SEVEN, Suit.DIAMONDS))
        player.addCard(Card(Rank.EIGHT, Suit.SPADES))
        player.addCard(Card(Rank.NINE, Suit.CLUBS))

        val expectedHand = Hand(
            HandType.STRAIGHT,
            listOf(
                Card(Rank.FIVE, Suit.CLUBS),
                Card(Rank.SIX, Suit.HEARTS),
                Card(Rank.SEVEN, Suit.DIAMONDS),
                Card(Rank.EIGHT, Suit.SPADES),
                Card(Rank.NINE, Suit.CLUBS)
            )
        )

        assertEquals(expectedHand, player.straightHand(), "Score should reflect a standard straight 5-9")
    }

    @Test
    fun testAceHighStraight() {
        val player = Player()
        player.addCard(Card(Rank.TEN, Suit.HEARTS))
        player.addCard(Card(Rank.JACK, Suit.CLUBS))
        player.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        player.addCard(Card(Rank.KING, Suit.SPADES))
        player.addCard(Card(Rank.ACE, Suit.HEARTS))

        val expectedHand = Hand(
            HandType.STRAIGHT,
            listOf(
                Card(Rank.TEN, Suit.HEARTS),
                Card(Rank.JACK, Suit.CLUBS),
                Card(Rank.QUEEN, Suit.DIAMONDS),
                Card(Rank.KING, Suit.SPADES),
                Card(Rank.ACE, Suit.HEARTS)
            )
        )

        assertEquals(expectedHand, player.straightHand(), "Score should reflect an Ace-high straight")
    }

    @Test
    fun testAceLowStraight() {
        val player = Player()
        player.addCard(Card(Rank.ACE, Suit.HEARTS))
        player.addCard(Card(Rank.TWO, Suit.CLUBS))
        player.addCard(Card(Rank.THREE, Suit.DIAMONDS))
        player.addCard(Card(Rank.FOUR, Suit.SPADES))
        player.addCard(Card(Rank.FIVE, Suit.CLUBS))
        player.addCard(Card(Rank.FIVE, Suit.DIAMONDS))

        val expectedHand = Hand(
            HandType.STRAIGHT,
            listOf(
                Card(Rank.ACE, Suit.HEARTS),
                Card(Rank.TWO, Suit.CLUBS),
                Card(Rank.THREE, Suit.DIAMONDS),
                Card(Rank.FOUR, Suit.SPADES),
                Card(Rank.FIVE, Suit.CLUBS),
            )
        )

        assertEquals(expectedHand, player.straightHand(), "Score should reflect an Ace-low straight")
    }

    @Test
    fun testNoStraightDueToBrokenSequence() {
        val player = Player()
        player.addCard(Card(Rank.ACE, Suit.HEARTS))
        player.addCard(Card(Rank.TWO, Suit.CLUBS))
        player.addCard(Card(Rank.THREE, Suit.DIAMONDS))
        player.addCard(Card(Rank.FIVE, Suit.SPADES))
        player.addCard(Card(Rank.SIX, Suit.CLUBS))
        assertEquals(null, player.straightHand(), "Score should be zero as the sequence is broken")
    }

    /********************************************* THREE OF A KIND TESTS **********************************************/

    @Test
    fun testThreeOfAKindValid() {
        val player = Player()
        player.addCard(Card(Rank.SEVEN, Suit.HEARTS))
        player.addCard(Card(Rank.SEVEN, Suit.CLUBS))
        player.addCard(Card(Rank.SEVEN, Suit.DIAMONDS))
        player.addCard(Card(Rank.TWO, Suit.SPADES))
        player.addCard(Card(Rank.THREE, Suit.HEARTS))

        val expectedHand = Hand(
            HandType.THREE_OF_A_KIND,
            listOf(
                Card(Rank.SEVEN, Suit.HEARTS),
                Card(Rank.SEVEN, Suit.CLUBS),
                Card(Rank.SEVEN, Suit.DIAMONDS),
                Card(Rank.THREE, Suit.HEARTS),
                Card(Rank.TWO, Suit.SPADES),
            )
        )

        // Calculate score using the calc function
        assertEquals(expectedHand, player.threeOfAKindHand(),
            "Score should correctly calculate a three of a kind of Sevens")
    }

    @Test
    fun testThreeOfAKindInvalid() {
        val player = Player()
        player.addCard(Card(Rank.SEVEN, Suit.HEARTS))
        player.addCard(Card(Rank.SEVEN, Suit.CLUBS))
        player.addCard(Card(Rank.EIGHT, Suit.DIAMONDS))
        player.addCard(Card(Rank.NINE, Suit.SPADES))
        player.addCard(Card(Rank.TEN, Suit.HEARTS))
        assertEquals(null, player.threeOfAKindHand(),
            "Score should be zero as there is no three of a kind")
    }

    @Test
    fun testThreeOfAKindWithMoreThanOneSET() {
        val player = Player()
        // Would normally be a full house, but still test
        player.addCard(Card(Rank.SEVEN, Suit.HEARTS))
        player.addCard(Card(Rank.SEVEN, Suit.CLUBS))
        player.addCard(Card(Rank.SEVEN, Suit.DIAMONDS))
        player.addCard(Card(Rank.ACE, Suit.SPADES))
        player.addCard(Card(Rank.ACE, Suit.CLUBS))
        player.addCard(Card(Rank.ACE, Suit.DIAMONDS))

        val expectedHand = Hand(
            HandType.THREE_OF_A_KIND,
            listOf(
                Card(Rank.ACE, Suit.SPADES),
                Card(Rank.ACE, Suit.CLUBS),
                Card(Rank.ACE, Suit.DIAMONDS),
                Card(Rank.SEVEN, Suit.CLUBS),
                Card(Rank.SEVEN, Suit.HEARTS)
            )
        )

        assertEquals(expectedHand, player.threeOfAKindHand(),
            "Score should be the highest three of a kind")
    }

    /************************************************* TWO PAIR TESTS *************************************************/

    @Test
    fun testValidTwoPairs() {
        val player = Player()
        player.addCard(Card(Rank.EIGHT, Suit.HEARTS))
        player.addCard(Card(Rank.EIGHT, Suit.CLUBS))
        player.addCard(Card(Rank.NINE, Suit.DIAMONDS))
        player.addCard(Card(Rank.NINE, Suit.SPADES))
        player.addCard(Card(Rank.KING, Suit.HEARTS))

        val expectedHand = Hand(
            HandType.TWO_PAIR,
            listOf(
                Card(Rank.NINE, Suit.DIAMONDS),
                Card(Rank.NINE, Suit.SPADES),
                Card(Rank.EIGHT, Suit.CLUBS),
                Card(Rank.EIGHT, Suit.HEARTS),
                Card(Rank.KING, Suit.HEARTS)
            )
        )

        assertEquals(expectedHand, player.twoPairHand(),
            "Score should correctly calculate two pairs: Nines and Eights")
    }

    @Test
    fun testInvalidTwoPairs() {
        val player = Player()
        player.addCard(Card(Rank.EIGHT, Suit.HEARTS))
        player.addCard(Card(Rank.EIGHT, Suit.CLUBS))
        player.addCard(Card(Rank.NINE, Suit.DIAMONDS))
        player.addCard(Card(Rank.TEN, Suit.SPADES))
        player.addCard(Card(Rank.KING, Suit.HEARTS))
        assertEquals(null, player.twoPairHand(), "Score should be zero as there is only one pair")
    }

    @Test
    fun testTwoPairsWithExtraCards() {
        val player = Player()
        // Would normally be full house, still test
        player.addCard(Card(Rank.EIGHT, Suit.HEARTS))
        player.addCard(Card(Rank.EIGHT, Suit.CLUBS))
        player.addCard(Card(Rank.NINE, Suit.DIAMONDS))
        player.addCard(Card(Rank.NINE, Suit.SPADES))
        player.addCard(Card(Rank.NINE, Suit.HEARTS))

        val expectedHand = Hand(
            HandType.TWO_PAIR,
            listOf(
                Card(Rank.NINE, Suit.DIAMONDS),
                Card(Rank.NINE, Suit.SPADES),
                Card(Rank.EIGHT, Suit.CLUBS),
                Card(Rank.EIGHT, Suit.HEARTS),
                Card(Rank.NINE, Suit.HEARTS),
            )
        )

        assertEquals(expectedHand, player.twoPairHand(),
            "Score should correctly calculate two pairs, despite an extra Nine")
    }

    /*************************************************** PAIR TESTS ***************************************************/

    @Test
    fun testValidPair() {
        val player = Player()
        player.addCard(Card(Rank.TEN, Suit.HEARTS))
        player.addCard(Card(Rank.TEN, Suit.CLUBS))
        player.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        player.addCard(Card(Rank.KING, Suit.SPADES))
        player.addCard(Card(Rank.ACE, Suit.HEARTS))

        val expectedHand = Hand(
            HandType.PAIR,
            listOf(
                Card(Rank.TEN, Suit.HEARTS),
                Card(Rank.TEN, Suit.CLUBS),
                Card(Rank.ACE, Suit.HEARTS),
                Card(Rank.KING, Suit.SPADES),
                Card(Rank.QUEEN, Suit.DIAMONDS),
            )
        )

        assertEquals(expectedHand, player.pairHand(),
            "Score should correctly calculate a pair of Tens")
    }

    @Test
    fun testInvalidPair() {
        val player = Player()
        player.addCard(Card(Rank.TWO, Suit.HEARTS))
        player.addCard(Card(Rank.THREE, Suit.CLUBS))
        player.addCard(Card(Rank.FOUR, Suit.DIAMONDS))
        player.addCard(Card(Rank.FIVE, Suit.SPADES))
        player.addCard(Card(Rank.SIX, Suit.HEARTS))
        // Expect no score since no pairs are present
        assertEquals(null, player.pairHand(),
            "Score should be zero as there is no pair")
    }

    @Test
    fun testPairWithHigherCards() {
        val player = Player()
        player.addCard(Card(Rank.TWO, Suit.HEARTS))
        player.addCard(Card(Rank.TWO, Suit.CLUBS))
        player.addCard(Card(Rank.KING, Suit.DIAMONDS))
        player.addCard(Card(Rank.ACE, Suit.SPADES))
        player.addCard(Card(Rank.ACE, Suit.HEARTS))

        val expectedHand = Hand(
            HandType.PAIR,
            listOf(
                Card(Rank.ACE, Suit.SPADES),
                Card(Rank.ACE, Suit.HEARTS),
                Card(Rank.KING, Suit.DIAMONDS),
                Card(Rank.TWO, Suit.HEARTS),
                Card(Rank.TWO, Suit.CLUBS),
            )
        )

        assertEquals(expectedHand, player.pairHand(),
            "Score should correctly calculate a pair of Aces")
    }

    /************************************************ HIGH CARD TESTS *************************************************/

    @Test
    fun testHighCardValid() {
        val player = Player()
        player.addCard(Card(Rank.SEVEN, Suit.HEARTS))
        player.addCard(Card(Rank.FIVE, Suit.CLUBS))
        player.addCard(Card(Rank.THREE, Suit.DIAMONDS))
        player.addCard(Card(Rank.TWO, Suit.SPADES))
        player.addCard(Card(Rank.NINE, Suit.CLUBS))

        val expectedHand = Hand(
            HandType.HIGH_CARD,
            listOf(
                Card(Rank.NINE, Suit.CLUBS),
                Card(Rank.SEVEN, Suit.SPADES),
                Card(Rank.FIVE, Suit.HEARTS),
                Card(Rank.THREE, Suit.DIAMONDS),
                Card(Rank.TWO, Suit.HEARTS),
            )
        )

        val hand = player.highCardHand()

        assertEquals(expectedHand, player.highCardHand(), "Score should correctly calculate high card as Nine")
    }
}
