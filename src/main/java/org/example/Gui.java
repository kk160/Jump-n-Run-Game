package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exceptions.CharacterDiedException;
import org.example.sprites.Character;
import org.example.sprites.*;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class Gui extends JPanel implements ActionListener {

    private static final int FPS = 25;
    private static final String SPRITES_PATH = "sprites.json";
    private static final int GAME_GROUND = 600;
    private static final int CHARACTER_SPEED = 16;
    private static final int CHARACTER_WIDTH = 60;
    private static final int CHARACTER_HEIGHT = 120;
    public static final int CHARACTER2_SPEED = 13;
    public static final int CHARACTER3_SPEED = 10;
    private final Logger log = LogManager.getLogger(GameDriver.class);
    private final Map<String, Image> pathToImageMap = new HashMap<>();
    private final Set<Integer> keysPressed = new HashSet<>();
    private final Image imgBackground;
    private final Image imgCharacter;
    private final Image imgCharacter2;
    private final Image imgCharacter3;
    private final Character character;
    private int backgroundPosition;
    private int mouseX;
    private int mouseY;
    private int distanceJumped = 0;
    private boolean showDebugInformation;
    private boolean jumpInProgress;
    private Sprite addingSprite;
    private Enemy lastEnemy;
    private List<Sprite> spriteList;


    /*
     * Gui constructor
     * */
    public Gui() {
        setFocusable(true);

        character = new Character(50, GAME_GROUND - CHARACTER_HEIGHT, CHARACTER_WIDTH, CHARACTER_HEIGHT, 3);

        log.info("Loading image of background");
        ImageIcon background = new ImageIcon((Objects.requireNonNull(getClass().getResource("/images/background.jpg"))));
        imgBackground = background.getImage();

        log.info("Loading image of character");
        ImageIcon characterImage1 = new ImageIcon((Objects.requireNonNull(getClass().getResource("/images/char1.png"))));
        characterImage1.setImage(characterImage1.getImage().getScaledInstance(character.getWidth(), character.getHeight(), Image.SCALE_DEFAULT));
        imgCharacter = characterImage1.getImage();

        log.info("Loading image of older character");
        ImageIcon characterImage2 = new ImageIcon((Objects.requireNonNull(getClass().getResource("/images/char2.png"))));
        characterImage2.setImage(characterImage2.getImage().getScaledInstance(character.getWidth(), character.getHeight(), Image.SCALE_DEFAULT));
        imgCharacter2 = characterImage2.getImage();

        log.info("Loading image of third character");
        ImageIcon characterImage3 = new ImageIcon((Objects.requireNonNull(getClass().getResource("/images/char3.png"))));
        characterImage3.setImage(characterImage3.getImage().getScaledInstance(character.getWidth(), character.getHeight(), Image.SCALE_DEFAULT));
        imgCharacter3 = characterImage3.getImage();

        addKeyListener(new KeyboardAction());
        addMouseListener((new MouseAction()));
        addMouseMotionListener((new MouseMoveAction()));


        readSpritesFromFile();

        Timer time = new Timer(1000 / FPS, this);
        time.start();
    }

    /**
     * Get the position of the background
     *
     * @return background position
     */
    public int getBackgroundPosition() {
        return backgroundPosition;
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        int backgroundWidth = imgBackground.getWidth(null);
        g2d.drawImage(imgBackground, (-1) * (getBackgroundPosition() % backgroundWidth), 0, null);
        g2d.drawImage(imgBackground, (-1) * (getBackgroundPosition() % backgroundWidth) + backgroundWidth, 0, null);

        if (character.getHealthPoint() == 3) {
            g2d.drawImage(imgCharacter, character.getX() - getBackgroundPosition(), character.getY(), null);
        } else if (character.getHealthPoint() == 2) {
            g2d.drawImage(imgCharacter2, character.getX() - getBackgroundPosition(), character.getY(), null);
        } else {
            g2d.drawImage(imgCharacter3, character.getX() - getBackgroundPosition(), character.getY(), null);
        }
        showDebug(g2d, character);

        for (Sprite sprite : spriteList) {
            g2d.drawImage(lazyLoadImage(sprite.getImagePath()), sprite.getX() - getBackgroundPosition(), sprite.getY(), null);
            showDebug(g2d, sprite);

        }
        if (addingSprite != null) {
            g2d.drawImage(lazyLoadImage(addingSprite.getImagePath()), mouseX, mouseY, null);
        }

        g2d.setColor(Color.BLACK);
        g2d.drawString("Score: " + character.getScorePoint(), 10, 15);
        g2d.drawString("Health points: " + character.getHealthPoint(), 10, 30);

        maybeShowCoordinatesOnCursor(g2d);
    }

    /**
     * Shows coordinates on the mouse cursor
     *
     * @param g2d used to render/show text
     */
    private void maybeShowCoordinatesOnCursor(Graphics2D g2d) {

        if (showDebugInformation) {
            int x = mouseX;
            int y = mouseY;

            g2d.setFont(new Font("TimesRoman", Font.PLAIN, 17));

            g2d.setColor(Color.BLACK);
            g2d.drawString(String.format("rel=(%d,%d)", x, y), x, y);

            g2d.setColor(Color.BLACK);
            g2d.drawString(String.format("abs=(%d,%d)", x + getBackgroundPosition(), y), x, y + 20);
        }
    }

    /**
     * Loads the image of the sprite in the game from hard drive, only if it wasn't loaded before
     *
     * @param imagePath path of the image
     * @return image from cache/hard drive
     */
    private Image lazyLoadImage(String imagePath) {
        return pathToImageMap.computeIfAbsent(imagePath, this::loadImage);
    }

    /**
     * Loads the image of the sprite in the game from hard drive
     *
     * @param imagePath image path of the sprite
     * @return the Image
     */
    private Image loadImage(String imagePath) {
        log.info("Loading image " + imagePath);

        ImageIcon imageIcon = new ImageIcon((Objects.requireNonNull(getClass().getResource(imagePath))));
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        return imageIcon.getImage();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            if (jumpInProgress) {
                jump();
            } else {
                gravity();
            }
            handleKeys();
        } catch (CharacterDiedException e) {
            log.info(e.getMessage());
            GameDriver.gameOver(character);
        }

        repaint();
    }

    private class KeyboardAction extends KeyAdapter {

        @Override
        public synchronized void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
                keysPressed.add(e.getKeyCode());
            } else {
                handleNormalKey(key);
            }
        }

        @Override
        public synchronized void keyReleased(KeyEvent e) {
            keysPressed.remove(e.getKeyCode());
        }
    }

    /**
     * Moves the background of the game
     *
     * @param distance of the movement
     */
    public void moveBackground(int distance) {
        backgroundPosition += distance;
    }

    /**
     * Checks if there's a collision of the character and a sprite when the character tries to move
     *
     * @param dx movement towards the x-axis
     * @param dy movement towards the y-axis
     * @throws CharacterDiedException if character dies
     */
    private void tryMoveCharacter(int dx, int dy) throws CharacterDiedException {
        if (dx < 0 && getBackgroundPosition() <= 0) {
            return;
        }

        character.moveX(dx);
        character.moveY(dy);
        moveBackground(dx);

        Sprite removedSprite = null;
        boolean collision = false;
        for (Sprite sprite : spriteList) {
            collision = character.collisionDetected(sprite);

            if (collision) {
                if (sprite instanceof Block) {
                    //Undo Movement
                    character.moveX(-dx);
                    character.moveY(-dy);
                    moveBackground(-dx);
                }
                if (sprite instanceof OPMaskItem || sprite instanceof FFP2MaskItem) {
                    removedSprite = sprite;
                    character.addToScore(sprite.getScorePoint());
                }
                if (sprite instanceof SyringeItem) {
                    removedSprite = sprite;
                    character.addHealthPoint();
                }
                if (sprite instanceof Enemy) {
                    Enemy newEnemy = (Enemy) sprite;
                    if (newEnemy != lastEnemy) {
                        log.info("Enemy collision");
                        character.removeHealthPoint();
                        lastEnemy = newEnemy;
                    } else {
                        log.info("Enemy collision (ignored)");
                    }
                }
                break;
            }
        }

        spriteList.remove(removedSprite);
        if (!collision) {
            lastEnemy = null;
        }
    }

    /**
     * Ensures that the character is falling back to the ground after a jump
     *
     * @throws CharacterDiedException if the character dies
     */
    private void gravity() throws CharacterDiedException {
        if (character.getY() + character.getHeight() < GAME_GROUND) {
            tryMoveCharacter(0, (int) (1.25 * CHARACTER_SPEED));
        }
    }

    private class MouseAction extends MouseAdapter {

        /**
         * Adds a sprite to the sprite list per mouse click
         *
         * @param m mouse event
         */
        public void mouseClicked(MouseEvent m) {
            mouseX = m.getX();
            mouseY = m.getY();
            log.info("MouseX = " + mouseX + " , MouseY = " + mouseY);
            if (addingSprite != null) {
                addingSprite.setX(mouseX + getBackgroundPosition());
                addingSprite.setY(mouseY);

                int width = lazyLoadImage(addingSprite.getImagePath()).getWidth(null);
                int height = lazyLoadImage(addingSprite.getImagePath()).getHeight(null);
                addingSprite.setWidth(width);
                addingSprite.setHeight(height);

                spriteList.add(addingSprite);
                addingSprite = null;
            }
        }
    }

    private class MouseMoveAction extends MouseMotionAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }

    /**
     * Loads sprites from the file "sprites.json"
     */
    private void readSpritesFromFile() {
        log.info("Loading sprites from file " + SPRITES_PATH);
        File file = new File(SPRITES_PATH);
        try {
            spriteList = new ObjectMapper().readValue(file, new TypeReference<>() {
            });
            log.info("Loaded " + spriteList.size() + " sprites from file " + SPRITES_PATH);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            spriteList = new ArrayList<>();
        }
    }

    /**
     * Writes sprites to the file "sprites.json"
     */
    private void writeSpritesToFile() {
        log.info("Saving " + spriteList.size() + " sprites to file " + SPRITES_PATH);
        File file = new File(SPRITES_PATH);
        try {
            //https://stackoverflow.com/questions/34193177/why-does-jackson-polymorphic-serialization-not-work-in-lists
            TypeReference<List<Sprite>> rootType = new TypeReference<>() {
            };
            new ObjectMapper().writerFor(rootType).writeValue(file, spriteList);

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Provides the jump of the character
     *
     * @throws CharacterDiedException if character dies
     */
    private void jump() throws CharacterDiedException {
        if (distanceJumped < 1.2 * character.getHeight()) {
            tryMoveCharacter(0, (int) (-1.25 * CHARACTER_SPEED));
            distanceJumped += CHARACTER_SPEED;
        } else {
            jumpInProgress = false;
            distanceJumped = 0;
        }
    }


    private void handleKeys() throws CharacterDiedException {
        for (Integer key : keysPressed) {
            handleMoveKey(key);
        }
    }

    /**
     * Handles key events of the right and left key
     *
     * @param key key pressed by the user
     * @throws CharacterDiedException if the character dies
     */
    private void handleMoveKey(Integer key) throws CharacterDiedException {
        switch (key) {
            case KeyEvent.VK_LEFT:
                if (character.getHealthPoint() == 3) {
                    tryMoveCharacter(-CHARACTER_SPEED, 0);
                } else if (character.getHealthPoint() == 2) {
                    tryMoveCharacter(-CHARACTER2_SPEED, 0);
                } else {
                    tryMoveCharacter(-CHARACTER3_SPEED, 0);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (character.getHealthPoint() == 3) {
                    tryMoveCharacter(CHARACTER_SPEED, 0);
                } else if (character.getHealthPoint() == 2) {
                    tryMoveCharacter(CHARACTER2_SPEED, 0);
                } else {
                    tryMoveCharacter(CHARACTER3_SPEED, 0);
                }
                break;
        }
    }

    /**
     * Handles key events
     *
     * @param key key pressed by the user
     */
    private void handleNormalKey(int key) {
        switch (key) {
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
            case KeyEvent.VK_SPACE:
                jumpInProgress = true;
                break;
            case KeyEvent.VK_S:
                writeSpritesToFile();
                break;
            case KeyEvent.VK_R:
                readSpritesFromFile();
                break;
            case KeyEvent.VK_C:
                showDebugInformation = !showDebugInformation;
                break;
            case KeyEvent.VK_1:
                toggleKey1();
                break;
            case KeyEvent.VK_2:
                toggleKey2();
                break;
            case KeyEvent.VK_3:
                toggleKey3();
                break;
            case KeyEvent.VK_4:
                toggleKey4();
                break;
            case KeyEvent.VK_5:
                toggleKey5();
                break;
        }
    }

    /**
     * Preview of a new block before adding it to the sprite list
     */
    private void toggleKey1() {
        if (addingSprite == null) {
            addingSprite = new Block();
        } else {
            addingSprite = null;
        }
    }

    /**
     * Preview of a new syringe before adding it to the sprite list
     */
    private void toggleKey2() {
        if (addingSprite == null) {
            addingSprite = new SyringeItem();
        } else {
            addingSprite = null;
        }
    }

    /**
     * Preview of a new OP-mask before adding it to the sprite list
     */
    private void toggleKey3() {
        if (addingSprite == null) {
            addingSprite = new OPMaskItem();
        } else {
            addingSprite = null;
        }
    }

    /**
     * Preview of a new FFP2-mask before adding it to the sprite list
     */
    private void toggleKey4() {
        if (addingSprite == null) {
            addingSprite = new FFP2MaskItem();
        } else {
            addingSprite = null;
        }
    }

    /**
     * Preview of a new virus before adding it to the sprite list
     */
    private void toggleKey5() {
        if (addingSprite == null) {
            addingSprite = new Enemy();
        } else {
            addingSprite = null;
        }
    }

    /**
     * Shows debug information of the sprite
     *
     * @param g2d    used to render/show frame of the sprite
     * @param sprite sprite
     */
    private void showDebug(Graphics2D g2d, Sprite sprite) {
        if (showDebugInformation) {
            g2d.setColor(Color.orange);
            g2d.drawRect(sprite.getX() - getBackgroundPosition(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        }
    }
}
