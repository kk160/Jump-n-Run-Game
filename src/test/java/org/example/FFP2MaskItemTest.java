package org.example;

import org.example.sprites.FFP2MaskItem;
import org.junit.Assert;
import org.junit.Test;

public class FFP2MaskItemTest {

    @Test
    public void ffp2MaskItemTest(){
        FFP2MaskItem ffp2MaskItemTest = new FFP2MaskItem();

        Assert.assertEquals(2, ffp2MaskItemTest.getScorePoint());
        Assert.assertEquals(0, ffp2MaskItemTest.getHealthPoint());
        Assert.assertEquals("/images/FFP2-mask.png", ffp2MaskItemTest.getImagePath());
        Assert.assertNotEquals(null, ffp2MaskItemTest);
        Assert.assertNotEquals(1, ffp2MaskItemTest.getScorePoint());
        Assert.assertNotEquals(3, ffp2MaskItemTest.getHealthPoint());
        Assert.assertNotEquals("/images/OP-mask.png", ffp2MaskItemTest.getImagePath());
    }
}
