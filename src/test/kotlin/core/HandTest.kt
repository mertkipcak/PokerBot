package org.mkipcak.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

class HandTest {

    @Test
    fun testEqualitySameRanksDifferentSuits() {
        val hand1 = Hand(
            HandType.STRAIGHT,
            listOf(Card(Rank.TEN, Suit.HEARTS), Card(Rank.JACK, Suit.CLUBS), Card(Rank.QUEEN, Suit.HEARTS), Card(Rank.KING, Suit.CLUBS), Card(Rank.ACE, Suit.HEARTS))
        )
        val hand2 = Hand(
            HandType.STRAIGHT,
            listOf(Card(Rank.TEN, Suit.SPADES), Card(Rank.JACK, Suit.DIAMONDS), Card(Rank.QUEEN, Suit.SPADES), Card(Rank.KING, Suit.DIAMONDS), Card(Rank.ACE, Suit.SPADES))
        )
        assertEquals(hand1, hand2, "Hands with same ranks but different suits should be equal")
    }

    @Test
    fun testInequalityDifferentRanks() {
        val hand1 = Hand(
            HandType.FLUSH,
            listOf(Card(Rank.TWO, Suit.HEARTS), Card(Rank.THREE, Suit.HEARTS), Card(Rank.FOUR, Suit.HEARTS), Card(Rank.FIVE, Suit.HEARTS), Card(Rank.SIX, Suit.HEARTS))
        )
        val hand2 = Hand(
            HandType.FLUSH,
            listOf(Card(Rank.SEVEN, Suit.HEARTS), Card(Rank.EIGHT, Suit.HEARTS), Card(Rank.NINE, Suit.HEARTS), Card(Rank.TEN, Suit.HEARTS), Card(Rank.JACK, Suit.HEARTS))
        )
        assertNotEquals(hand1, hand2, "Hands with different ranks should not be equal")
    }

    @Test
    fun testInequalityDifferentTypes() {
        val hand1 = Hand(
            HandType.FLUSH,
            listOf(Card(Rank.TWO, Suit.HEARTS), Card(Rank.THREE, Suit.HEARTS), Card(Rank.FOUR, Suit.HEARTS), Card(Rank.FIVE, Suit.HEARTS), Card(Rank.SIX, Suit.HEARTS))
        )
        val hand2 = Hand(
            HandType.STRAIGHT,
            listOf(Card(Rank.TWO, Suit.HEARTS), Card(Rank.THREE, Suit.HEARTS), Card(Rank.FOUR, Suit.HEARTS), Card(Rank.FIVE, Suit.HEARTS), Card(Rank.SIX, Suit.HEARTS))
        )
        assertNotEquals(hand1, hand2, "Hands with different types should not be equal")
    }

    @Test
    fun testCompareToDifferentTypes() {
        val hand1 = Hand(HandType.PAIR, listOf(Card(Rank.FOUR, Suit.CLUBS), Card(Rank.FOUR, Suit.DIAMONDS)))
        val hand2 = Hand(HandType.HIGH_CARD, listOf(Card(Rank.ACE, Suit.HEARTS)))
        assertTrue(hand1 > hand2, "Pair should rank higher than high card")
    }

    @Test
    fun testCompareToSameTypeDifferentRanks() {
        val hand1 = Hand(
            HandType.FLUSH,
            listOf(Card(Rank.TWO, Suit.HEARTS), Card(Rank.THREE, Suit.HEARTS), Card(Rank.FOUR, Suit.HEARTS), Card(Rank.FIVE, Suit.HEARTS), Card(Rank.SIX, Suit.HEARTS))
        )
        val hand2 = Hand(
            HandType.FLUSH,
            listOf(Card(Rank.SEVEN, Suit.HEARTS), Card(Rank.EIGHT, Suit.HEARTS), Card(Rank.NINE, Suit.HEARTS), Card(Rank.TEN, Suit.HEARTS), Card(Rank.JACK, Suit.HEARTS))
        )
        assertTrue(hand1 < hand2, "Hand with lower rank cards should be ranked lower")
    }

    @Test
    fun testHashCodeConsistencyWithEquals() {
        val hand1 = Hand(
            HandType.STRAIGHT,
            listOf(Card(Rank.TEN, Suit.HEARTS), Card(Rank.JACK, Suit.CLUBS), Card(Rank.QUEEN, Suit.HEARTS), Card(Rank.KING, Suit.CLUBS), Card(Rank.ACE, Suit.HEARTS))
        )
        val hand2 = Hand(
            HandType.STRAIGHT,
            listOf(Card(Rank.TEN, Suit.SPADES), Card(Rank.JACK, Suit.DIAMONDS), Card(Rank.QUEEN, Suit.SPADES), Card(Rank.KING, Suit.DIAMONDS), Card(Rank.ACE, Suit.SPADES))
        )
        assertEquals(hand1.hashCode(), hand2.hashCode(), "Hash codes should be consistent with equals for hands with same ranks and different suits")
    }
}
