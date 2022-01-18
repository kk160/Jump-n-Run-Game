package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exceptions.CharacterDiedException;
import org.example.sprites.Character;
import org.junit.Assert;
import org.junit.Test;

public class CharacterTest {

    private static final Logger log = LogManager.getLogger(GameDriver.class);

    @Test
    public void characterTest(){
        Character characterTest = new Character(50, 10, 200, 300, 3);

        Assert.assertEquals(3, characterTest.getHealthPoint());
        Assert.assertEquals(50, characterTest.getX());
        Assert.assertEquals(10, characterTest.getY());
        Assert.assertEquals(200, characterTest.getWidth());
        Assert.assertEquals(300, characterTest.getHeight());
        Assert.assertEquals("/images/char1.png", characterTest.getImagePath());
        Assert.assertNotEquals(5, characterTest.getHealthPoint());
        Assert.assertNotEquals(680, characterTest.getX());
        Assert.assertNotEquals(25, characterTest.getY());
        Assert.assertNotEquals(300, characterTest.getWidth());
        Assert.assertNotEquals(200, characterTest.getHeight());
        Assert.assertNotEquals("/images/background.png", characterTest.getImagePath());
        Assert.assertNotEquals(null, characterTest);
    }

    @Test
    public void addToScoreTest(){
        Character characterTest = new Character(50, 10, 200, 300, 3);

        Assert.assertEquals(0, characterTest.getScorePoint());
        Assert.assertNotEquals(2, characterTest.getScorePoint());

        characterTest.addToScore(2);

        Assert.assertEquals(2, characterTest.getScorePoint());
        Assert.assertNotEquals(0, characterTest.getScorePoint());
    }

    @Test
    public void addHealthPointTest(){
        Character characterTest1 = new Character(50, 10, 200, 300, 3);
        Character characterTest2 = new Character(50, 10, 200, 300, 2);
        Character characterTest3 = new Character(50, 10, 200, 300, 1);

        characterTest1.addHealthPoint();
        characterTest2.addHealthPoint();
        characterTest3.addHealthPoint();

        Assert.assertEquals(3, characterTest1.getHealthPoint());
        Assert.assertEquals(3, characterTest2.getHealthPoint());
        Assert.assertEquals(2, characterTest3.getHealthPoint());
        Assert.assertNotEquals(4, characterTest1.getHealthPoint());
        Assert.assertNotEquals(1, characterTest2.getHealthPoint());
        Assert.assertNotEquals(3, characterTest3.getHealthPoint());
    }

    @Test
    public void removeHealthPointTest(){
        Character characterTest1 = new Character(50, 10, 200, 300, 3);
        Character characterTest2 = new Character(50, 10, 200, 300, 2);
        Character characterTest3 = new Character(50, 10, 200, 300, 1);
        Character characterTest4 = new Character(50, 10, 200, 300, 0);

        try {
            characterTest1.removeHealthPoint();
            characterTest2.removeHealthPoint();
            characterTest3.removeHealthPoint();
            characterTest4.removeHealthPoint();
        } catch (CharacterDiedException e) {
            log.info(e.getMessage());
        }

        Assert.assertEquals(2, characterTest1.getHealthPoint());
        Assert.assertEquals(1, characterTest2.getHealthPoint());
        Assert.assertEquals(0, characterTest3.getHealthPoint());
        Assert.assertEquals(0, characterTest4.getHealthPoint());
        Assert.assertNotEquals(3, characterTest1.getHealthPoint());
        Assert.assertNotEquals(2, characterTest2.getHealthPoint());
        Assert.assertNotEquals(1, characterTest3.getHealthPoint());
        Assert.assertNotEquals(1, characterTest4.getHealthPoint());
    }
}
