package com.company.type;

public enum CardValue {

    TWO(2, '2'),
    THREE(3, '3'),
    FOUR(4, '4'),
    FIVE(5, '5'),
    SIX(6, '6'),
    SEVEN(7, '7'),
    EIGHT(8, '8'),
    NINE(9, '9'),
    TEN(10, 'T'),
    J(11, 'J'),
    Q(12, 'Q'),
    K(13, 'K'),
    A(14, 'A');

    private final int value;

    private final Character symbol;

    CardValue(int value, Character symbol) {
        this.value = value;
        this.symbol = symbol;
    }

    public Character getSymbol() {
        return symbol;
    }

    public int getValue() {
        return value;
    }

    public static CardValue findBySymbol(Character symbol) {
        for (CardValue card : CardValue.values()) {
            if (card.getSymbol() == symbol) {
                return card;
            }
        }
        return null;
    }

}
