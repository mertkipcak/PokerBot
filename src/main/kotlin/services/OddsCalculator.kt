package org.mkipcak.services

import org.mkipcak.core.*

interface OddsCalculator {
    fun findProbability(playerHand: List<Card>, table: List<Card>, totalPlayers: Int): Probabilities
}