package com.company.type;

public enum Suit {
    HEART('H'),
    SPADE('S'),
    CROSS('C'),
    DIAMONDS('D');

    private final Character symbol;

    Suit(Character symbol) {
        this.symbol = symbol;
    }

    public Character getSymbol() {
        return symbol;
    }

    public static Suit findBySymbol(Character symbol) {
        for (Suit suit : Suit.values()) {
            if (suit.getSymbol() == symbol) {
                return suit;
            }
        }
        return null;
    }
}
