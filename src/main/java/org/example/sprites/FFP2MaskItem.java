package org.example.sprites;

public class FFP2MaskItem extends Item {

    /**
     * Image path of the FFP2-mask
     */
    private static final String IMAGE_PATH = "/images/FFP2-mask.png";

    @Override
    public String getImagePath() {
        return IMAGE_PATH;
    }

    @Override
    public int getScorePoint() {
        return 2;
    }

    @Override
    public int getHealthPoint() {
        return 0;
    }
}
