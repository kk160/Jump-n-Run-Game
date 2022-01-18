package org.example.sprites;

public class Block extends Sprite {

    /**
     * Image path of the block
     */
    private static final String IMAGE_PATH = "/images/block.png";

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
        return 0;
    }
}
