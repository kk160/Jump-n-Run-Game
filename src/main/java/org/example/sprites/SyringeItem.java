package org.example.sprites;

public class SyringeItem extends Item {

    /**
     * Image path of the syringe
     */
    private static final String IMAGE_PATH = "/images/syringe.png";

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
        return 1;
    }
}
