package com.company.entity;

import com.company.type.Hand;
import com.company.processor.HandProcessor;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private HandProcessor handProcessor;

    private Hand hand;

    private List<Card> cards;

    private int cardSum;

    private Card highCard;

    private int winCount;

    private String name;

    public Player(String name, HandProcessor handProcessor) {
        this.handProcessor = handProcessor;
        this.name = name;
        cards = new ArrayList<>();
        winCount = 0;
    }

    public Player(Hand hand) {
        this.hand = hand;
    }

    public void addWin() {
        winCount++;
    }

    public Hand getHand() {
        return hand;
    }

    public int getCardSum() {
        return cardSum;
    }

    public void setCardSum(int cardSum) {
        this.cardSum = cardSum;
    }

    public Card getHighCard() {
        return highCard;
    }

    public void setHighCard(Card highCard) {
        this.highCard = highCard;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void processHand() {
        handProcessor.process(this);
    }

    public int getWinCount() {
        return winCount;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String playerData = String.format("Player %s : ", name);
        for (Card card : cards) {
            playerData = playerData.concat(String.format("%s%s ", card.getCardValue().getSymbol(), card.getSuit().getSymbol()));
        }
        playerData = playerData.concat(String.format("-- Has : %s cardSum(%s)", hand, getCardSum()));
        playerData = playerData.concat(String.format("-- Highest : %s ", getHighCard()));

        return playerData;
    }

    public int comparePlayersHandValue(Player player) {
        return Integer.compare(getHand().getValue(), player.getHand().getValue());
    }

    public int comparePlayersCardSum(Player player) {
        return Integer.compare(getCardSum(), player.getCardSum());
    }

    public int comparePlayersHighCards(Player player) {
        return Integer.compare(getHighCard().getCardValue().getValue(), player.getHighCard().getCardValue().getValue());
    }
}
