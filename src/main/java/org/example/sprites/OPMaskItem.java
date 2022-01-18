package org.example.sprites;

public class OPMaskItem extends Item {

    /**
     * Image path of the OP-mask
     */
    private static final String IMAGE_PATH = "/images/OP-mask.png";

    @Override
    public String getImagePath() {
        return IMAGE_PATH;
    }

    @Override
    public int getScorePoint() {
        return 1;
    }

    @Override
    public int getHealthPoint() {
        return 0;
    }
}
