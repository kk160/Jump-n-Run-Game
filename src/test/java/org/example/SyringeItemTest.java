package org.example;

import org.example.sprites.SyringeItem;
import org.junit.Assert;
import org.junit.Test;

public class SyringeItemTest {

    @Test
    public void syringeItemTest(){
        SyringeItem syringeItemTest = new SyringeItem();

        Assert.assertEquals(0, syringeItemTest.getScorePoint());
        Assert.assertEquals(1, syringeItemTest .getHealthPoint());
        Assert.assertEquals("/images/syringe.png", syringeItemTest .getImagePath());
        Assert.assertNotEquals(null, syringeItemTest );
        Assert.assertNotEquals(1, syringeItemTest .getScorePoint());
        Assert.assertNotEquals(3, syringeItemTest .getHealthPoint());
        Assert.assertNotEquals("/images/OP-mask.png", syringeItemTest .getImagePath());
    }
}
