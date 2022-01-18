package org.example;

import org.example.sprites.OPMaskItem;
import org.junit.Assert;
import org.junit.Test;

public class OPMaskItemTest {
    @Test
    public void opMaskItemTest(){
        OPMaskItem opMaskItemTest = new OPMaskItem();

        Assert.assertEquals(1, opMaskItemTest.getScorePoint());
        Assert.assertEquals(0, opMaskItemTest.getHealthPoint());
        Assert.assertEquals("/images/OP-mask.png", opMaskItemTest.getImagePath());
        Assert.assertNotEquals(null, opMaskItemTest);
        Assert.assertNotEquals(2, opMaskItemTest.getScorePoint());
        Assert.assertNotEquals(3, opMaskItemTest.getHealthPoint());
        Assert.assertNotEquals("/styles/OP-mask.png", opMaskItemTest.getImagePath());
    }
}
