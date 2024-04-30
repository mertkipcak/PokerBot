# PokerBot

This is a small Kotlin project that uses Markov Chain estimation to calculate of winning hands of poker.

## Use
#### Setting up

To use the application, go into main in `src/main/kotlin/Main.kt` and change your hand, table state and player count accordingly.

An example state would be:
```kotlin
val playerCards: List<Card> = listOf(
        Card(Rank.ACE, Suit.SPADES),
        Card(Rank.ACE, Suit.CLUBS)
)
val table: List<Card> = listOf(
    Card(Rank.QUEEN, Suit.HEARTS),
    Card(Rank.TWO, Suit.CLUBS),
    Card(Rank.THREE, Suit.SPADES)
)

val totalPlayerCount = 3
```
Where the player's hand has ace of spades and clubs, and the flop is queen of hearts, two of clubs and three of spades, with total players on the table being 3.

#### Run
To run the odds calculator, simply run
```bash
gradle run
```
