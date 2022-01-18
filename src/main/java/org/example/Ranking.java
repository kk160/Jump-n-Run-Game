package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Ranking{

    private static final Logger log = LogManager.getLogger(Ranking.class);
    /**
     * file
     * */
    private final String document;

    /**
     * Ranking constructor
     *
     * @param document String
     * */
    public Ranking(String document) {
        this.document = document;
    }

    /**
     * Method to write in the file
     *
     * @param players List<PLayers>
     * */
    public void write(List<Player> players) {
        try {
            log.info("Write in the File");
            OutputStream outputStream = new FileOutputStream(String.valueOf(document), false);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (Player player : players) {
                bufferedWriter.write(player.getName() + ": " + player.getScore());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch(IOException e){
            log.error("Can't write rankings to file " + document, e);
        }
    }

    /**
     * Method to read the file
     *
     * @throws IOException if there is a problem with the file
     * */
    public List<Player> read() throws IOException {

        log.info("Reading the file");
        InputStream inStream = new FileInputStream(document);
        InputStreamReader reader = new InputStreamReader(inStream);
        BufferedReader bufferedReader = new BufferedReader(reader);

        List<Player> readPlayers = new ArrayList<>();
        String line;

        while ((line = bufferedReader.readLine()) != null) {
            String[] arr = line.split(":", 2);

            for (int i = 0; i < arr.length; i++) {
                arr[i] = arr[i].trim();
            }
            String name = arr[0];
            String score = arr[1];
            Player player = new Player(name, Integer.parseInt(score));
            readPlayers.add(player);
        }
        bufferedReader.close();

        log.info("Return readPlayer");
        return readPlayers.stream()
                .sorted(Comparator.comparing(Player::getScore)).collect(Collectors.toList());
    }
}
