package org.example;

public class Player {

    /**
     * Name of the player
     * */
    private final String name;
    /**
     * Score of the player*/
    private final int score;

    /**
     * Player constructor
     *
     * @param name String
     * @param score int
     * */
    public Player(String name, int score){

        this.name = name;
        this.score =score;
    }

    /**
     * Method to get the name of the player
     * */
    public String getName() {
        return name;
    }

    /**
     * Method to get the score of the player
     * */
    public int getScore() {
        return score;
    }
}
