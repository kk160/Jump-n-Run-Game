package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class Main {

    private static final Logger log = LogManager.getLogger(GameDriver.class);

    /**
     * @param  args args
     * @throws IOException if there is a problem with the file ranking.txt
     * */
    public static void main (String[] args) throws IOException{

        Ranking ranking = new Ranking("src/main/resources/documents/ranking.txt");
        List<Player> players = ranking.read();

        log.info("Trying to create new GameDriver");
        try {

            GameDriver gameDriver = new GameDriver("menu", players, ranking);
            gameDriver.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameDriver.setSize(400, 400);
            gameDriver.setLayout(null);
            gameDriver.setVisible(true);
        }
        catch (final Exception e){
            log.error("An Error occurred while trying to create GameDriver");
        }
    }
}
