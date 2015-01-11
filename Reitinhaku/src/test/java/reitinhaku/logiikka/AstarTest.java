/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.logiikka;

import org.junit.Test;
import static org.junit.Assert.*;
import static reitinhaku.App.randomVerkko;

/**
 *
 * @author Samuel
 */
public class AstarTest {

    private Solmu solmu;

    public AstarTest() {
        int[][] v = {
            {1, 1, 1, 9, 1, 2, 9},
            {2, 2, 1, 6, 1, 5, 3},
            {3, 2, 1, 1, 1, 2, 5},
            {4, 2, 5, 2, 7, 1, 2}};
        Verkko verkko = new Verkko(v);
        Astar astar = new Astar(verkko, verkko.getSolmu(0, 0), verkko.getSolmu(4, 0));
        solmu = astar.haku();
    }

    @Test
    public void kustannus() {
        assertTrue(solmu.getKustannus() == 8);

    }

    @Test
    public void polku() {
        assertTrue(solmu.getEdellinen().getX() == 4 && solmu.getEdellinen().getY() == 1);
        assertTrue(solmu.getEdellinen().getEdellinen().getX() == 4 && solmu.getEdellinen().getEdellinen().getY() == 2);
    }

    @Test
    public void testaaAikaPieniVerkko() {
        Verkko verkko = randomVerkko(10, 10);
        long alkuAika = System.currentTimeMillis();
        Astar astar = new Astar(verkko, verkko.getSolmu(3, 3), verkko.getSolmu(9, 9));
        astar.haku();
        long loppuAika = System.currentTimeMillis();
        long aika = loppuAika - alkuAika;
        assertTrue(aika < 10);
    }

    @Test
    public void testaaAikaIsoVerkko() {
        Verkko verkko = randomVerkko(100, 100);
        long alkuAika = System.currentTimeMillis();
        Astar astar = new Astar(verkko, verkko.getSolmu(10, 10), verkko.getSolmu(99, 99));
        astar.haku();
        long loppuAika = System.currentTimeMillis();
        long aika = loppuAika - alkuAika;
        System.out.println("aika:" + aika);
        assertTrue(aika < 100);
    }
}
