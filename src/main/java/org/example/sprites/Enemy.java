package org.example.sprites;

public class Enemy extends Sprite {

    /**
     * Image path of the enemy
     */
    private static final String IMAGE_PATH = "/images/virus.png";

    @Override
    public String getImagePath() {
        return IMAGE_PATH;
    }

    @Override
    public int getScorePoint() {
        return 0;
    }

    @Override
    public int getHealthPoint() {
        return -1;
    }
}
