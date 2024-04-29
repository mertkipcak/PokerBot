package models

import org.mkipcak.core.*

interface OddsCalculator {
    fun findProbability(playerHand: List<Card>, table: List<Card>, totalPlayers: Int): Double
}