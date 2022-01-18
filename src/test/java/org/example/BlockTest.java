package org.example;

import org.example.sprites.Block;
import org.junit.Assert;
import org.junit.Test;

public class BlockTest {

    @Test
    public void blockTest(){
        Block blockTest = new Block();

        Assert.assertEquals(0, blockTest.getScorePoint());
        Assert.assertEquals(0, blockTest .getHealthPoint());
        Assert.assertEquals("/images/block.png", blockTest .getImagePath());
        Assert.assertNotEquals(null, blockTest );
        Assert.assertNotEquals(1, blockTest .getScorePoint());
        Assert.assertNotEquals(3, blockTest .getHealthPoint());
        Assert.assertNotEquals("/images/blockImage.png", blockTest .getImagePath());
    }
}
