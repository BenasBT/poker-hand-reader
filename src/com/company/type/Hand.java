package com.company.type;

import com.company.entity.Card;

public enum Hand implements Comparable<Hand>{
    NAN(0),
    HIGH_CARD(1),
    ONE_PAIR(2),
    TWO_PAIRS(3),
    THREE_OF_A_KIND(4),
    STRAIGHT(5),
    FLUSH(6),
    FULL_HOUSE(7),
    FOUR_OF_A_KIND(8),
    STRAIGHT_FLUSH(9),
    ROYAL_FLUSH(10);

    private int value;

    Hand(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
