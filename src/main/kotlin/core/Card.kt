package org.mkipcak.core

class Card(val rank: Rank, val suit: Suit) {

    override fun toString(): String {
        return "$rank of $suit"
    }
}