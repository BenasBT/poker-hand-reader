package com.company.processor;

import com.company.config.ApplicationConfiguration;
import com.company.entity.Card;
import com.company.entity.Player;
import com.company.exception.BadCardSymbolException;
import com.company.exception.NoPlayersException;
import com.company.exception.WrongCardAmountException;
import com.company.type.Hand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameProcessor {

    private static final int DISTRIBUTED_CARDS = 5;

    private List<Player> players;

    public List<Player> getPlayers() {
        return players;
    }

    public GameProcessor() {
        players = new ArrayList<>();
        HandProcessor handProcessor = new HandProcessor();
        players.add(new Player("1", handProcessor));
        players.add(new Player("2", handProcessor));
    }

    public void SpreadCards(String cards) throws WrongCardAmountException, BadCardSymbolException {
        if (ApplicationConfiguration.INFO || ApplicationConfiguration.DEBUG) {
            System.out.println("*=====================*");
            System.out.println(cards);
        }

        List<Card> splitedCards = ProcessCards(Arrays.asList(cards.split("\\s+")));
        for (Player player : players) {
            player.setCards(splitedCards.subList(players.indexOf(player) * DISTRIBUTED_CARDS, (players.indexOf(player) * DISTRIBUTED_CARDS) + DISTRIBUTED_CARDS));
        }
    }

    private List<Card> ProcessCards(List<String> carSymbols) throws WrongCardAmountException, BadCardSymbolException {
        if (carSymbols.size() != 10) {
            throw new WrongCardAmountException("Line must contain 10 cards");
        }
        List<Card> cards = new ArrayList<>();
        for (String symbol : carSymbols) {
            cards.add(new Card(symbol));
        }
        return cards;
    }

    public void Play(String cards) {
        try {
            SpreadCards(cards);
            CompareHands();
        } catch (WrongCardAmountException | BadCardSymbolException e) {
            e.printStackTrace();
        }
    }

    private void CompareHands() {
        for (Player player : players) {
            player.processHand();
        }
        if (ApplicationConfiguration.DEBUG) {
            for (Player player : players) {
                System.out.println(player.toString());
            }
        }
        try {
            completeGame();
        } catch (NoPlayersException e) {
            e.printStackTrace();
        }
    }

    private void completeGame() {
        if (players.size() == 0) {
            throw new NoPlayersException("Players are missing");
        }
        boolean draw = false;
        Player winner = new Player(Hand.NAN);
        for (Player player : players) {
            //Compare hand value
            if (player.comparePlayersHandValue(winner) > 0) {
                winner = player;
            } else if (player.comparePlayersHandValue(winner) == 0) {
                //Find winner when hand value is the same
                if (player.getHand() == Hand.FULL_HOUSE && winner.getHand() == Hand.FULL_HOUSE) {
                    winner = getWinningFullHouse(winner, player);
                } else if (player.comparePlayersCardSum(winner) > 0) {
                    winner = player;
                } else if (player.comparePlayersCardSum(winner) == 0) {
                    //Compare high card
                    if (player.comparePlayersHighCards(winner) > 0) {
                        winner = player;
                    } else if (player.comparePlayersHighCards(winner) == 0) {
                        draw = true;
                    }
                }
            }
        }
        setWinner(draw, winner);
    }

    private void setWinner(boolean draw, Player winner) {
        if (draw) {
            if (ApplicationConfiguration.INFO || ApplicationConfiguration.DEBUG) {
                System.out.println("Draw");
            }
        } else {
            winner.addWin();
            if (ApplicationConfiguration.INFO || ApplicationConfiguration.DEBUG) {
                printEndInformation(winner);
            }
        }
    }

    private void printEndInformation(Player winner) {
        String winnerCards = "";
        for (Card card : winner.getCards()) {
            winnerCards = winnerCards.concat(String.format("%s%s", card.getCardValue().getSymbol(), card.getSuit().getSymbol()));
        }
        System.out.println(String.format("Player %s: Won with %s \nHaving: %s with value(%s) and HC %s ", winner.getName(),
                winnerCards, winner.getHand(), winner.getCardSum(), winner.getHighCard()));

    }

    private Player getWinningFullHouse(Player winner, Player player) {
        int player3OfAKindCardValue = player.getCards().get(0).getCardValue().getValue();
        int winner3OfAKindCardValue = winner.getCards().get(0).getCardValue().getValue();

        if (player3OfAKindCardValue > winner3OfAKindCardValue) {
            winner = player;
        }
        return winner;
    }
}
