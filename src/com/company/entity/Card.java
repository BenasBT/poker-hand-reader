package com.company.entity;

import com.company.exception.BadCardSymbolException;
import com.company.type.CardValue;
import com.company.type.Suit;

public class Card implements Comparable<Card> {

    private Suit suit;

    private CardValue cardValue;

    public Card(String cardString) throws BadCardSymbolException {
        cardValue = ProcessValue(cardString);
        suit = ProcessSuit(cardString);
    }

    public Card(CardValue cardValue) {
        this.cardValue = cardValue;
    }

    private CardValue ProcessValue(String cardString) throws BadCardSymbolException {
        if(cardString.length() != 2){
            throw new BadCardSymbolException("Card specified incorrectly");
        }
        return CardValue.findBySymbol(cardString.charAt(0));
    }

    private Suit ProcessSuit(String cardString) throws BadCardSymbolException {
        if(cardString.length() != 2){
            throw new BadCardSymbolException("Card specified incorrectly");
        }
        return Suit.findBySymbol(cardString.charAt(1));
    }

    public Suit getSuit() {
        return suit;
    }

    public CardValue getCardValue() {
        return cardValue;
    }

    @Override
    public String toString() {
        return String.format("%s of %s", cardValue, suit);
    }

    @Override
    public int compareTo(Card card) {
        return Integer.compare(getCardValue().getValue(), card.getCardValue().getValue());
    }


}
