package org.example;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.logging.log4j.core.layout.PatternMatch;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class RankingTest {

    @Test
    public void testRead() throws Exception{
        Ranking rankinTest = new Ranking("src/test/java/resources/documents/rankingTest.txt");
        List<Player> playerTest = rankinTest.read();

        Assert.assertEquals("Kyril", playerTest.get(0).getName());
        Assert.assertEquals("Liljana", playerTest.get(3).getName());
        Assert.assertEquals(false, "Liljana" == playerTest.get(0).getName());
        Assert.assertEquals(false, "Marco" ==playerTest.get(3).getName());
        Assert.assertTrue(4 == playerTest.size());
        Assert.assertTrue(3899 == playerTest.get(3).getScore());
        Assert.assertTrue(23 == playerTest.get(1).getScore());
        Assert.assertFalse(10 == playerTest.size());
        Assert.assertFalse(234 == playerTest.get(3).getScore());
        Assert.assertFalse(4 == playerTest.get(1).getScore());
    }

    @Test
    public void testWrite() throws Exception{
        Ranking rankinTest = new Ranking("src/test/java/resources/documents/rankingTest2.txt");
        List<Player> playerTest = rankinTest.read();
        int sizeOfList = playerTest.size();
        Player newPlayerTest = new Player("Paula", 12);
        playerTest.add(newPlayerTest);

        rankinTest.write(playerTest);

        List<Player> sortedPlayerTest = rankinTest.read();

        Assert.assertTrue(sizeOfList + 1 == sortedPlayerTest.size());
        Assert.assertFalse(sizeOfList == playerTest.size());
        Assert.assertTrue(12 == playerTest.get(1).getScore());
        Assert.assertFalse(2555 == playerTest.get(1).getScore());
        Assert.assertEquals("Paula", playerTest.get(1).getName());
        Assert.assertEquals(false, "Dinah" == playerTest.get(1).getName());
    }
}
