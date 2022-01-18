package org.example;

import org.example.sprites.Enemy;
import org.junit.Assert;
import org.junit.Test;

public class EnemyTest {

    @Test
    public void enemyTest(){
        Enemy enemyTest = new Enemy();

        Assert.assertEquals(-1, enemyTest.getHealthPoint());
        Assert.assertEquals(0, enemyTest.getScorePoint());
        Assert.assertEquals("/images/virus.png", enemyTest.getImagePath());
        Assert.assertNotEquals(null, enemyTest);
        Assert.assertNotEquals(5, enemyTest.getHealthPoint());
        Assert.assertNotEquals(2, enemyTest.getScorePoint());
        Assert.assertNotEquals("/documents/virus.png", enemyTest.getImagePath());
    }
}
