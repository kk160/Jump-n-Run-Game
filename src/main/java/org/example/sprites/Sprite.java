package org.example.sprites;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.awt.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Block.class, name = "Block"),
        @JsonSubTypes.Type(value = Item.class, name = "Item"),
        @JsonSubTypes.Type(value = OPMaskItem.class, name = "OP-Mask"),
        @JsonSubTypes.Type(value = FFP2MaskItem.class, name = "FFP2-Mask"),
        @JsonSubTypes.Type(value = SyringeItem.class, name = "Syringe"),
        @JsonSubTypes.Type(value = Enemy.class, name = "Virus")})
// https://stackoverflow.com/questions/30362446/deserialize-json-with-jackson-into-polymorphic-types-a-complete-example-is-giv
public abstract class Sprite {

    /**
     * X-Coordinate of the sprite
     */
    protected int x;

    /**
     * Y-Coordinate of the sprite
     */
    protected int y;

    /**
     * Width of the sprite
     */
    protected int width;

    /**
     * Height of the sprite
     */
    protected int height;

    /**
     * Constructor for class Sprite
     *
     * @param x      X-Coordinate of the sprite
     * @param y      Y-Coordinate of the sprite
     * @param width  Width of the sprite
     * @param height Height of the sprite
     */
    public Sprite(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Sprite() {

    }


    /**
     * Checks if there is a collision of one sprite with another sprite
     *
     * @return false, if there's no collision with a sprite
     */
    public boolean collisionDetected(Sprite s2) {
        Rectangle r1 = new Rectangle(this.x, y, width, height);
        Rectangle r2 = new Rectangle(s2.getX(), s2.getY(), s2.getWidth(), s2.getHeight());
        return r1.intersects(r2);
    }

    /**
     * Get the X-Coordinate of the sprite
     * v
     * @return X-Coordinate of the sprite
     */
    public int getX() {
        return x;
    }

    /**
     * Set the X-Coordinate of the sprite
     *
     * @param x X-Coordinate of the sprite
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Get the Y-Coordinate of the sprite
     *
     * @return Y-Coordinate of the sprite
     */
    public int getY() {
        return y;
    }

    /**
     * Set the Y-Coordinate of the sprite
     *
     * @param y Y-Coordinate of the sprite
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Get the width of the sprite
     *
     * @return width of the sprite
     */
    public int getWidth() {
        return width;
    }

    /**
     * Set the width of the sprite
     *
     * @param width width of the sprite
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Get the height of the sprite
     *
     * @return height of the sprite
     */
    public int getHeight() {
        return height;
    }

    /**
     * Set the height of the sprite
     *
     * @param height height of the sprite
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Get the image path of the sprite
     *
     * @return the image path of the sprite
     */
    public abstract String getImagePath();

    /**
     * Get the score point of the sprite
     *
     * @return the score point of the sprite
     */
    public abstract int getScorePoint();

    /**
     * Get the health point of the sprite
     *
     * @return the health point of the sprite
     */
    public abstract int getHealthPoint();
}
