package org.example.sprites;

import org.example.exceptions.CharacterDiedException;

public class Character extends Sprite {

    /**
     * Image path of the character
     */
    private static final String IMAGE_PATH = "/images/char1.png";

    /**
     * Health points of the character
     */
    private int healthPoints;

    /**
     * Score points of the character
     */
    private int scorePoint = 0;

    /**
     * Constructor for the class Character
     *
     * @param x            X-Coordinate of the character
     * @param y            Y-Coordinate of the character
     * @param width        Width of the character
     * @param height       Height of the character
     * @param healthPoints Health points of the character
     */
    public Character(int x, int y, int width, int height, int healthPoints) {
        super(x, y, width, height);
        this.healthPoints = healthPoints;
    }

    /**
     * Adds points to the score if the character collects items
     *
     * @param point point that will be added
     */
    public void addToScore(int point) {
        scorePoint += point;
    }

    /**
     * Adds one health point
     */
    public void addHealthPoint() {
        if (healthPoints < 3) {
            healthPoints += 1;
        }
    }

    /**
     * Removes one health point
     *
     * @throws CharacterDiedException if health points are zero
     */
    public void removeHealthPoint() throws CharacterDiedException {
        if (healthPoints > 0) {
            healthPoints -= 1;
            if (healthPoints == 0) {
                throw new CharacterDiedException(x);
            }
        }
    }

    /**
     * Moves the character towards the x-axis
     *
     * @param distance of the movement along the x-axis
     */
    public void moveX(int distance) {
        x += distance;
    }

    /**
     * Moves the character towards the y-axis
     *
     * @param distance of the movement along the y-axis
     */
    public void moveY(int distance) {
        y += distance;
    }

    @Override
    public String getImagePath() {
        return IMAGE_PATH;
    }

    @Override
    public int getScorePoint() {
        return scorePoint;
    }

    @Override
    public int getHealthPoint() {
        return healthPoints;
    }
}
