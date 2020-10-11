package com.company.reader;

import com.company.entity.Player;
import com.company.processor.GameProcessor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HandReader {

    private static GameProcessor gameProcessor = new GameProcessor();
    private static String file = "/home/anthon/Projects/Danske/poker/src/com/company/data/cards.txt";

    public static void StartReading() {
        BufferedReader reader;
        String endMessage = "";
        try {
            reader = new BufferedReader(new FileReader(file));
            String cards = reader.readLine();
            while (cards != null) {
                gameProcessor.Play(cards);
                cards = reader.readLine();
            }
            reader.close();
            for (Player player : gameProcessor.getPlayers()) {
                endMessage = endMessage.concat(String.format("Player: %s won %s games\n", player.getName(), player.getWinCount()));
            }
            System.out.println(endMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
