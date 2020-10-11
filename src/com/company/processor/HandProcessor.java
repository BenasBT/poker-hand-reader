package com.company.processor;

import com.company.entity.Card;
import com.company.entity.Player;
import com.company.type.CardValue;
import com.company.type.Hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HandProcessor {

    private List<Card> handCards;

    public HandProcessor() {
        handCards = new ArrayList<>();
    }

    public void process(Player player) {
        List<Card> cards = new ArrayList<>(player.getCards());
        player.setHand(processHand(cards, player));
        cards.removeAll(handCards);
        if (player.getHand() != Hand.HIGH_CARD) {
            player.setHighCard(cards.size() > 0 ? processRemainingHighCard(cards) : null);
        }
    }

    private Hand processHand(List<Card> cards, Player player) {
        Hand hand = Hand.NAN;
        if (isFlush(cards)) {

            //Royal Flush:
            if (getHandValue() >= maxHandValue()) {
                hand = Hand.ROYAL_FLUSH;
                player.setCardSum(getHandValue());
                return hand;
            }
            //Straight Flush:
            if (isStraight(handCards)) {
                hand = Hand.STRAIGHT_FLUSH;
            }
            //true for the future flush check
            if (isHandValueLessThen(hand, Hand.FLUSH)) {
                hand = Hand.FLUSH;
            }
        }

        //Four of a Kind:
        if (isHandValueLessThen(hand, Hand.FOUR_OF_A_KIND) && isXOfTheKind(cards, 4)) {
            hand = Hand.FOUR_OF_A_KIND;
        }
        //Full House:
        if (isHandValueLessThen(hand, Hand.FULL_HOUSE) && contains2SetsOfSameValue(cards, 3, 2)) {
            player.setCards(handCards);
            hand = Hand.FULL_HOUSE;
        }

        //Straight:
        if (isHandValueLessThen(hand, Hand.STRAIGHT) && isStraight(cards)) {
            hand = Hand.STRAIGHT;
        }
        //Three of a Kind:
        if (isHandValueLessThen(hand, Hand.THREE_OF_A_KIND) && isXOfTheKind(cards, 3)) {
            hand = Hand.THREE_OF_A_KIND;
        }
        //Two Pairs:
        if (isHandValueLessThen(hand, Hand.TWO_PAIRS) && contains2SetsOfSameValue(cards, 2, 2)) {
            hand = Hand.TWO_PAIRS;
        }
        //One Pair:
        if (isHandValueLessThen(hand, Hand.ONE_PAIR) && isXOfTheKind(cards, 2)) {
            hand = Hand.ONE_PAIR;
        }

        if (isHandValueLessThen(hand, Hand.HIGH_CARD)) {
            handCards = Collections.singletonList(processRemainingHighCard(cards));
            player.setHighCard(handCards.get(0));
            hand = Hand.HIGH_CARD;
        }

        player.setCardSum(getHandValue());
        return hand;
    }

    private boolean isHandValueLessThen(Hand hand, Hand fourOfAKind) {
        return hand.getValue() < fourOfAKind.getValue();
    }


    private Card processRemainingHighCard(List<Card> cards) {
        Card highCard = new Card(CardValue.TWO);
        for (Card card : cards) {
            if (card.getCardValue().getValue() >= highCard.getCardValue().getValue()) {
                highCard = card;
            }
        }
        return highCard;
    }

    private boolean isFlush(List<Card> cards) {
        List<List<Card>> cardsBySuit = new ArrayList<>(cards.stream()
                .collect(Collectors.groupingBy(c -> c.getSuit().getSymbol()))
                .values());

        for (List<Card> cardList : cardsBySuit) {
            if (cardList.size() == 5) {
                handCards = cardList;
                return true;
            }
        }
        return false;
    }

    private boolean isStraight(List<Card> cards) {
        int count = 0;
        cards.sort(Collections.reverseOrder());
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getCardValue().getValue() -
                    cards.get(i + 1).getCardValue().getValue() == 1) {
                if (++count >= 4) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isXOfTheKind(List<Card> cards, int sameValueCount) {
        List<List<Card>> cardsByValue = new ArrayList<>(cards.stream()
                .collect(Collectors.groupingBy(c -> c.getCardValue().getValue()))
                .values());

        for (List<Card> cardList : cardsByValue) {
            if (cardList.size() == sameValueCount) {
                handCards = cardList;
                return true;
            }
        }
        return false;
    }

    private boolean contains2SetsOfSameValue(List<Card> cards, int firsSetCount, int SecondSetCount) {
        if (isXOfTheKind(cards, firsSetCount)) {
            List<Card> saved3OfAKind = handCards;
            List<Card> remaining = new ArrayList<>(cards);
            remaining.removeAll(saved3OfAKind);
            if (isXOfTheKind(remaining, SecondSetCount)) {
                handCards = Stream.concat(saved3OfAKind.stream(), handCards.stream()).collect(Collectors.toList());
                return true;
            }
        }
        return false;
    }

    private int getHandValue() {
        int sum = 0;
        for (Card handCard : handCards) {
            sum += handCard.getCardValue().getValue();
        }
        return sum;
    }

    private int maxHandValue() {
        return CardValue.A.getValue() +
                CardValue.K.getValue() +
                CardValue.Q.getValue() +
                CardValue.J.getValue() +
                CardValue.TEN.getValue();
    }
}
