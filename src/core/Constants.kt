package core

enum class HandType(val value: Int) {
    HIGH_CARD(0),
    PAIR(10000),
    TWO_PAIRS(20000),
    THREE_OF_A_KIND(30000),
    STRAIGHT(40000),
    FLUSH(50000),
    FULL_HOUSE(60000),
    FOUR_OF_A_KIND(70000),
    STRAIGHT_FLUSH(80000)
}

enum class Suit(val value: Int) {
    CLUBS(1),
    DIAMONDS(2),
    HEARTS(3),
    SPADES(4)
}

enum class Rank(val value: Int) {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13),
    ACE(14),
}
