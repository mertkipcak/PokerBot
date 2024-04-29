package org.mkipcak.core

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
        assertEquals(hand.calcStraightFlushScore(Rank.ACE.value), hand.straightFlushScore(),
            "Score should reflect a high straight flush")
    }

    @Test
    fun testStraightFlushLow() {
        val hand = Hand()
        hand.addCard(Card(Rank.ACE, Suit.CLUBS))
        hand.addCard(Card(Rank.TWO, Suit.CLUBS))
        hand.addCard(Card(Rank.THREE, Suit.CLUBS))
        hand.addCard(Card(Rank.FOUR, Suit.CLUBS))
        hand.addCard(Card(Rank.FIVE, Suit.CLUBS))
        assertEquals(hand.calcStraightFlushScore(Rank.FIVE.value), hand.straightFlushScore(),
            "Score should reflect a low straight flush (Ace through Five)")
    }

    @Test
    fun testNotAStraightFlushDueToSuits() {
        val hand = Hand()
        hand.addCard(Card(Rank.TEN, Suit.HEARTS))
        hand.addCard(Card(Rank.JACK, Suit.HEARTS))
        hand.addCard(Card(Rank.QUEEN, Suit.HEARTS))
        hand.addCard(Card(Rank.KING, Suit.SPADES))
        hand.addCard(Card(Rank.ACE, Suit.HEARTS))
        assertEquals(0, hand.straightFlushScore(),
            "Should not score as straight flush due to mismatched suits")
    }

    @Test
    fun testNotAStraightFlushDueToSequence() {
        val hand = Hand()
        hand.addCard(Card(Rank.TEN, Suit.CLUBS))
        hand.addCard(Card(Rank.JACK, Suit.CLUBS))
        hand.addCard(Card(Rank.QUEEN, Suit.CLUBS))
        hand.addCard(Card(Rank.ACE, Suit.CLUBS))
        hand.addCard(Card(Rank.NINE, Suit.CLUBS))
        assertEquals(0, hand.straightFlushScore(),
            "Should not score as straight flush due to broken sequence")
    }

    @Test
    fun testStraightFlushWithExtraCard() {
        val hand = Hand()
        hand.addCard(Card(Rank.NINE, Suit.DIAMONDS))
        hand.addCard(Card(Rank.TEN, Suit.DIAMONDS))
        hand.addCard(Card(Rank.JACK, Suit.DIAMONDS))
        hand.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        hand.addCard(Card(Rank.KING, Suit.DIAMONDS))
        hand.addCard(Card(Rank.EIGHT, Suit.DIAMONDS)) // Irrelevant card
        assertEquals(hand.calcStraightFlushScore(Rank.KING.value), hand.straightFlushScore(),
            "Score should reflect the highest straight flush regardless of extra cards")
    }

    /********************************************** FOUR OF A KIND TESTS **********************************************/

    @Test
    fun testFourOfAKindValid() {
        val hand = Hand()
        // Adding four Queens and one unrelated card
        hand.addCard(Card(Rank.QUEEN, Suit.HEARTS))
        hand.addCard(Card(Rank.QUEEN, Suit.CLUBS))
        hand.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        hand.addCard(Card(Rank.QUEEN, Suit.SPADES))
        hand.addCard(Card(Rank.THREE, Suit.HEARTS))
        // Expect the four of a kind score plus the rank value of Queen times 10
        assertEquals(hand.calcFourOfAKindScore(Rank.QUEEN.value), hand.fourOfAKindScore(),
            "Score should reflect a four of a kind of Queens")
    }

    @Test
    fun testFourOfAKindInvalid() {
        val hand = Hand()
        // Adding three Queens and two unrelated cards
        hand.addCard(Card(Rank.QUEEN, Suit.HEARTS))
        hand.addCard(Card(Rank.QUEEN, Suit.CLUBS))
        hand.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        hand.addCard(Card(Rank.THREE, Suit.SPADES))
        hand.addCard(Card(Rank.THREE, Suit.HEARTS))
        // Expect no score for four of a kind since only three Queens are present
        assertEquals(0, hand.fourOfAKindScore(),
            "Score should be zero as there is no four of a kind")
    }

    /************************************************ FULL HOUSE TESTS ************************************************/

    @Test
    fun testFullHouseValid() {
        val hand = Hand()
        // Adding four Queens and one unrelated card
        hand.addCard(Card(Rank.QUEEN, Suit.HEARTS))
        hand.addCard(Card(Rank.QUEEN, Suit.CLUBS))
        hand.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        hand.addCard(Card(Rank.THREE, Suit.SPADES))
        hand.addCard(Card(Rank.THREE, Suit.HEARTS))
        // Expect the four of a kind score plus the rank value of Queen times 10
        assertEquals(hand.calcFullHouseScore(Rank.QUEEN.value, Rank.THREE.value), hand.fullHouseScore(),
            "Score should reflect a full house of Queens and Three")
    }

    @Test
    fun testFullHouseInvalid() {
        val hand = Hand()
        // Adding three Queens and two unrelated cards
        hand.addCard(Card(Rank.QUEEN, Suit.HEARTS))
        hand.addCard(Card(Rank.QUEEN, Suit.CLUBS))
        hand.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        hand.addCard(Card(Rank.TWO, Suit.SPADES))
        hand.addCard(Card(Rank.THREE, Suit.HEARTS))
        // Expect no score for four of a kind since only three Queens are present
        assertEquals(0, hand.fullHouseScore(),
            "Score should be zero as there is no full house")
    }

    /*************************************************** FLUSH TESTS **************************************************/

    @Test
    fun testValidFlushScore() {
        val hand = Hand()
        // Create a valid flush with five hearts
        hand.addCard(Card(Rank.TWO, Suit.HEARTS))
        hand.addCard(Card(Rank.THREE, Suit.HEARTS))
        hand.addCard(Card(Rank.FOUR, Suit.HEARTS))
        hand.addCard(Card(Rank.FIVE, Suit.HEARTS))
        hand.addCard(Card(Rank.SIX, Suit.HEARTS))
        assertEquals(hand.calcFlushScore(Rank.SIX.value), hand.flushScore(),
            "Flush score should be calculated correctly for a valid flush")
    }

    @Test
    fun testValidFlushWithExtraCards() {
        val hand = Hand()
        // Create a valid flush with five hearts and add a club
        hand.addCard(Card(Rank.TWO, Suit.HEARTS))
        hand.addCard(Card(Rank.THREE, Suit.HEARTS))
        hand.addCard(Card(Rank.FOUR, Suit.HEARTS))
        hand.addCard(Card(Rank.FIVE, Suit.HEARTS))
        hand.addCard(Card(Rank.SIX, Suit.HEARTS))
        hand.addCard(Card(Rank.SIX, Suit.CLUBS))
        assertEquals(hand.calcFlushScore(Rank.SIX.value), hand.flushScore(),
            "Flush score should be calculated correctly for a valid flush")
    }

    @Test
    fun testInvalidFlushScoreDueToMixedSuits() {
        val hand = Hand()
        // Five cards of different suits
        hand.addCard(Card(Rank.TWO, Suit.HEARTS))
        hand.addCard(Card(Rank.THREE, Suit.CLUBS))
        hand.addCard(Card(Rank.FOUR, Suit.DIAMONDS))
        hand.addCard(Card(Rank.FIVE, Suit.SPADES))
        hand.addCard(Card(Rank.SIX, Suit.HEARTS))
        assertEquals(0, hand.flushScore(),
            "Flush score should be zero when cards are of mixed suits")
    }

    /************************************************* STRAIGHT TESTS *************************************************/

    @Test
    fun testStandardStraight() {
        val hand = Hand()
        hand.addCard(Card(Rank.FIVE, Suit.CLUBS))
        hand.addCard(Card(Rank.SIX, Suit.HEARTS))
        hand.addCard(Card(Rank.SEVEN, Suit.DIAMONDS))
        hand.addCard(Card(Rank.EIGHT, Suit.SPADES))
        hand.addCard(Card(Rank.NINE, Suit.CLUBS))
        assertEquals(hand.calcStraightScore(Rank.NINE.value), hand.straightScore(), "Score should reflect a standard straight 5-9")
    }

    @Test
    fun testAceHighStraight() {
        val hand = Hand()
        hand.addCard(Card(Rank.TEN, Suit.HEARTS))
        hand.addCard(Card(Rank.JACK, Suit.CLUBS))
        hand.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        hand.addCard(Card(Rank.KING, Suit.SPADES))
        hand.addCard(Card(Rank.ACE, Suit.HEARTS))
        assertEquals(hand.calcStraightScore(Rank.ACE.value), hand.straightScore(), "Score should reflect an Ace-high straight")
    }

    @Test
    fun testAceLowStraight() {
        val hand = Hand()
        hand.addCard(Card(Rank.ACE, Suit.HEARTS))
        hand.addCard(Card(Rank.TWO, Suit.CLUBS))
        hand.addCard(Card(Rank.THREE, Suit.DIAMONDS))
        hand.addCard(Card(Rank.FOUR, Suit.SPADES))
        hand.addCard(Card(Rank.FIVE, Suit.CLUBS))
        hand.addCard(Card(Rank.FIVE, Suit.DIAMONDS))
        assertEquals(hand.calcStraightScore(Rank.FIVE.value), hand.straightScore(), "Score should reflect an Ace-low straight")
    }

    @Test
    fun testNoStraightDueToBrokenSequence() {
        val hand = Hand()
        hand.addCard(Card(Rank.ACE, Suit.HEARTS))
        hand.addCard(Card(Rank.TWO, Suit.CLUBS))
        hand.addCard(Card(Rank.THREE, Suit.DIAMONDS))
        hand.addCard(Card(Rank.FIVE, Suit.SPADES))
        hand.addCard(Card(Rank.SIX, Suit.CLUBS))
        assertEquals(0, hand.straightScore(), "Score should be zero as the sequence is broken")
    }

    /********************************************* THREE OF A KIND TESTS **********************************************/

    @Test
    fun testThreeOfAKindValid() {
        val hand = Hand()
        hand.addCard(Card(Rank.SEVEN, Suit.HEARTS))
        hand.addCard(Card(Rank.SEVEN, Suit.CLUBS))
        hand.addCard(Card(Rank.SEVEN, Suit.DIAMONDS))
        hand.addCard(Card(Rank.TWO, Suit.SPADES))
        hand.addCard(Card(Rank.THREE, Suit.HEARTS))
        // Calculate score using the calc function
        assertEquals(hand.calcThreeOfAKindScore(Rank.SEVEN.value), hand.threeOfAKindScore(),
            "Score should correctly calculate a three of a kind of Sevens")
    }

    @Test
    fun testThreeOfAKindInvalid() {
        val hand = Hand()
        hand.addCard(Card(Rank.SEVEN, Suit.HEARTS))
        hand.addCard(Card(Rank.SEVEN, Suit.CLUBS))
        hand.addCard(Card(Rank.EIGHT, Suit.DIAMONDS))
        hand.addCard(Card(Rank.NINE, Suit.SPADES))
        hand.addCard(Card(Rank.TEN, Suit.HEARTS))
        assertEquals(0, hand.threeOfAKindScore(),
            "Score should be zero as there is no three of a kind")
    }

    @Test
    fun testThreeOfAKindWithMoreThanOneSET() {
        val hand = Hand()
        // Would normally be a full house, but still test
        hand.addCard(Card(Rank.SEVEN, Suit.HEARTS))
        hand.addCard(Card(Rank.SEVEN, Suit.CLUBS))
        hand.addCard(Card(Rank.SEVEN, Suit.DIAMONDS))
        hand.addCard(Card(Rank.ACE, Suit.SPADES))
        hand.addCard(Card(Rank.ACE, Suit.CLUBS))
        hand.addCard(Card(Rank.ACE, Suit.DIAMONDS))
        assertEquals(hand.calcThreeOfAKindScore(Rank.ACE.value), hand.threeOfAKindScore(),
            "Score should be the highest three of a kind")
    }

    /************************************************* TWO PAIR TESTS *************************************************/

    @Test
    fun testValidTwoPairs() {
        val hand = Hand()
        hand.addCard(Card(Rank.EIGHT, Suit.HEARTS))
        hand.addCard(Card(Rank.EIGHT, Suit.CLUBS))
        hand.addCard(Card(Rank.NINE, Suit.DIAMONDS))
        hand.addCard(Card(Rank.NINE, Suit.SPADES))
        hand.addCard(Card(Rank.KING, Suit.HEARTS))
        assertEquals(hand.calcTwoPairScore(Rank.NINE.value, Rank.EIGHT.value), hand.twoPairScore(),
            "Score should correctly calculate two pairs: Nines and Eights")
    }

    @Test
    fun testInvalidTwoPairs() {
        val hand = Hand()
        hand.addCard(Card(Rank.EIGHT, Suit.HEARTS))
        hand.addCard(Card(Rank.EIGHT, Suit.CLUBS))
        hand.addCard(Card(Rank.NINE, Suit.DIAMONDS))
        hand.addCard(Card(Rank.TEN, Suit.SPADES))
        hand.addCard(Card(Rank.KING, Suit.HEARTS))
        assertEquals(0, hand.twoPairScore(), "Score should be zero as there is only one pair")
    }

    @Test
    fun testTwoPairsWithExtraCards() {
        val hand = Hand()
        // Would normally be full house, still test
        hand.addCard(Card(Rank.EIGHT, Suit.HEARTS))
        hand.addCard(Card(Rank.EIGHT, Suit.CLUBS))
        hand.addCard(Card(Rank.NINE, Suit.DIAMONDS))
        hand.addCard(Card(Rank.NINE, Suit.SPADES))
        hand.addCard(Card(Rank.NINE, Suit.HEARTS)) // Extra Nine should not affect the two pairs score
        assertEquals(hand.calcTwoPairScore(Rank.NINE.value, Rank.EIGHT.value), hand.twoPairScore(),
            "Score should correctly calculate two pairs, despite an extra Nine")
    }

    /*************************************************** PAIR TESTS ***************************************************/

    @Test
    fun testValidPair() {
        val hand = Hand()
        hand.addCard(Card(Rank.TEN, Suit.HEARTS))
        hand.addCard(Card(Rank.TEN, Suit.CLUBS))
        hand.addCard(Card(Rank.QUEEN, Suit.DIAMONDS))
        hand.addCard(Card(Rank.KING, Suit.SPADES))
        hand.addCard(Card(Rank.ACE, Suit.HEARTS))
        // Expect the pair score for Tens
        assertEquals(hand.calcPairScore(Rank.TEN.value), hand.pairScore(),
            "Score should correctly calculate a pair of Tens")
    }

    @Test
    fun testInvalidPair() {
        val hand = Hand()
        hand.addCard(Card(Rank.TWO, Suit.HEARTS))
        hand.addCard(Card(Rank.THREE, Suit.CLUBS))
        hand.addCard(Card(Rank.FOUR, Suit.DIAMONDS))
        hand.addCard(Card(Rank.FIVE, Suit.SPADES))
        hand.addCard(Card(Rank.SIX, Suit.HEARTS))
        // Expect no score since no pairs are present
        assertEquals(0, hand.pairScore(),
            "Score should be zero as there is no pair")
    }

    @Test
    fun testPairWithHigherCards() {
        val hand = Hand()
        hand.addCard(Card(Rank.TWO, Suit.HEARTS))
        hand.addCard(Card(Rank.TWO, Suit.CLUBS))
        hand.addCard(Card(Rank.KING, Suit.DIAMONDS))
        hand.addCard(Card(Rank.ACE, Suit.SPADES))
        hand.addCard(Card(Rank.ACE, Suit.HEARTS))
        // Even though there's a higher pair of Aces, the score should only consider the highest single pair
        assertEquals(hand.calcPairScore(Rank.ACE.value), hand.pairScore(),
            "Score should correctly calculate a pair of Aces")
    }

    /************************************************ HIGH CARD TESTS *************************************************/

    @Test
    fun testHighCardValid() {
        val hand = Hand()
        hand.addCard(Card(Rank.SEVEN, Suit.HEARTS))
        hand.addCard(Card(Rank.FIVE, Suit.CLUBS))
        hand.addCard(Card(Rank.THREE, Suit.DIAMONDS))
        hand.addCard(Card(Rank.TWO, Suit.SPADES))
        hand.addCard(Card(Rank.NINE, Suit.CLUBS))
        // Expect the high card score for Nine
        assertEquals(hand.calcHighCardScore(Rank.NINE.value), hand.highCardScore(), "Score should correctly calculate high card as Nine")
    }

    @Test
    fun testHighCardWithPotentialPair() {
        val hand = Hand()
        hand.addCard(Card(Rank.FOUR, Suit.HEARTS))
        hand.addCard(Card(Rank.FOUR, Suit.CLUBS))
        hand.addCard(Card(Rank.SIX, Suit.DIAMONDS))
        hand.addCard(Card(Rank.EIGHT, Suit.SPADES))
        hand.addCard(Card(Rank.KING, Suit.CLUBS))
        // There is a pair of Fours, but we test high card calculation as well (KING should be the high card)
        assertEquals(hand.calcHighCardScore(Rank.KING.value), hand.highCardScore(), "Score should consider King as the highest card despite a lower pair")
    }

    @Test
    fun testLowestPossibleHighCard() {
        val hand = Hand()
        hand.addCard(Card(Rank.TWO, Suit.HEARTS))
        hand.addCard(Card(Rank.TWO, Suit.CLUBS))
        hand.addCard(Card(Rank.TWO, Suit.DIAMONDS))
        hand.addCard(Card(Rank.TWO, Suit.SPADES))
        hand.addCard(Card(Rank.THREE, Suit.CLUBS))
        // Even though Fours form a Four of a Kind, checking for correct high card (Three) score as a minimal valid scenario
        assertEquals(hand.calcHighCardScore(Rank.THREE.value), hand.highCardScore(), "Score should reflect Three as the highest card in presence of a four of a kind of Twos")
    }
}
