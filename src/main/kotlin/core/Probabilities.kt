package org.mkipcak.core

/**
 * Data class to store probabilities for poker outcomes for the player and other players.
 * Each field represents a specific probability as a percentage.
 */
data class Probabilities(
    val playerWinChance: Double = 0.0,
    val playerTieChance: Double = 0.0,
    val playerHighCardChance: Double = 0.0,
    val playerPairChance: Double = 0.0,
    val playerTwoPairsChance: Double = 0.0,
    val playerThreeOfAKindChance: Double = 0.0,
    val playerStraightChance: Double = 0.0,
    val playerFlushChance: Double = 0.0,
    val playerFullHouseChance: Double = 0.0,
    val playerFourOfAKindChance: Double = 0.0,
    val playerStraightFlushChance: Double = 0.0,


    val otherPlayersWinChance: Double = 0.0,
    val otherPlayersHighCardChance: Double = 0.0,
    val otherPlayersPairChance: Double = 0.0,
    val otherPlayersTwoPairsChance: Double = 0.0,
    val otherPlayersThreeOfAKindChance: Double = 0.0,
    val otherPlayersStraightChance: Double = 0.0,
    val otherPlayersFlushChance: Double = 0.0,
    val otherPlayersFullHouseChance: Double = 0.0,
    val otherPlayersFourOfAKindChance: Double = 0.0,
    val otherPlayersStraightFlushChance: Double = 0.0
) {
    override fun toString(): String {
        val reset = "\u001B[0m"
        val blue = "\u001B[34m"
        val bold = "\u001B[1m"

        return """
            |Probabilities:
            |------------------------------------------------
            |${bold}${blue}Player Win Chance         : ${String.format("%.2f", playerWinChance * 100)}%$reset
            |Player High Card Chance   : ${String.format("%.2f", playerHighCardChance * 100)}%
            |Player Pair Chance        : ${String.format("%.2f", playerPairChance * 100)}%
            |Player Two Pairs Chance   : ${String.format("%.2f", playerTwoPairsChance * 100)}%
            |Player Three of a Kind    : ${String.format("%.2f", playerThreeOfAKindChance * 100)}%
            |Player Straight Chance    : ${String.format("%.2f", playerStraightChance * 100)}%
            |Player Flush Chance       : ${String.format("%.2f", playerFlushChance * 100)}%
            |Player Full House Chance  : ${String.format("%.2f", playerFullHouseChance * 100)}%
            |Player Four of a Kind     : ${String.format("%.2f", playerFourOfAKindChance * 100)}%
            |Player Straight Flush     : ${String.format("%.2f", playerStraightFlushChance * 100)}%
            |------------------------------------------------
            |${bold}${blue}Tie Chance         : ${String.format("%.2f", playerTieChance * 100)}%$reset
            |------------------------------------------------
            |${bold}${blue}Other Players Win Chance  : ${String.format("%.2f", otherPlayersWinChance * 100)}%$reset
            |Other High Card Chance    : ${String.format("%.2f", otherPlayersHighCardChance * 100)}%
            |Other Pair Chance         : ${String.format("%.2f", otherPlayersPairChance * 100)}%
            |Other Two Pairs Chance    : ${String.format("%.2f", otherPlayersTwoPairsChance * 100)}%
            |Other Three of a Kind     : ${String.format("%.2f", otherPlayersThreeOfAKindChance * 100)}%
            |Other Straight Chance     : ${String.format("%.2f", otherPlayersStraightChance * 100)}%
            |Other Flush Chance        : ${String.format("%.2f", otherPlayersFlushChance * 100)}%
            |Other Full House Chance   : ${String.format("%.2f", otherPlayersFullHouseChance * 100)}%
            |Other Four of a Kind      : ${String.format("%.2f", otherPlayersFourOfAKindChance * 100)}%
            |Other Straight Flush      : ${String.format("%.2f", otherPlayersStraightFlushChance * 100)}%
            |------------------------------------------------
        """.trimMargin()
    }
}
