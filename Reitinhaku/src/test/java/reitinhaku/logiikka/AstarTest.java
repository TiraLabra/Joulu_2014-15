/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.logiikka;

import org.junit.Test;
import static org.junit.Assert.*;
import reitinhaku.Verkkolukija;

/**
 *
 * @author Samuel
 */
public class AstarTest {

    private Solmu solmu;
    private Verkkolukija lukija;

    public AstarTest() {
        int[][] v = {
            {1, 1, 1, 9, 1, 2, 9},
            {2, 2, 1, 6, 1, 5, 3},
            {3, 2, 1, 1, 1, 2, 5},
            {4, 2, 5, 2, 7, 1, 2}};
        Verkko verkko = new Verkko(v);
        Astar astar = new Astar(verkko, verkko.getSolmu(0, 0), verkko.getSolmu(4, 0));
        solmu = astar.haku();
        this.lukija = new Verkkolukija();
    }

    @Test
    public void kustannus() {
        assertTrue(solmu.getKustannus() == 8);

    }

    @Test
    public void polku() {
        assertTrue(solmu.getX() == 4 && solmu.getY() == 0);
        solmu = solmu.getEdellinen();
        assertTrue(solmu.getX() == 4 && solmu.getY() == 1);
        solmu = solmu.getEdellinen();
        assertTrue(solmu.getX() == 4 && solmu.getY() == 2);
        solmu = solmu.getEdellinen();
        assertTrue(solmu.getX() == 3 && solmu.getY() == 2);
        solmu = solmu.getEdellinen();
        assertTrue(solmu.getX() == 2 && solmu.getY() == 2);
        solmu = solmu.getEdellinen();
        assertTrue(solmu.getX() == 2 && solmu.getY() == 1);
        solmu = solmu.getEdellinen();
        assertTrue(solmu.getX() == 2 && solmu.getY() == 0);
        solmu = solmu.getEdellinen();
        assertTrue(solmu.getX() == 1 && solmu.getY() == 0);
        solmu = solmu.getEdellinen();
        assertTrue(solmu.getX() == 0 && solmu.getY() == 0);
        assertTrue(solmu.getEdellinen() == null);

    }

    @Test
    public void testaaAikaPieniVerkko() {
        Verkko verkko = lukija.randomVerkko(10, 10);
        long alkuAika = System.currentTimeMillis();
        Astar astar = new Astar(verkko, verkko.getSolmu(3, 3), verkko.getSolmu(9, 9));
        astar.haku();
        long loppuAika = System.currentTimeMillis();
        long aika = loppuAika - alkuAika;
        assertTrue(aika < 10);
    }

    @Test
    public void testaaAikaKeskiVerkko() {
        Verkko verkko = lukija.randomVerkko(100, 100);
        long alkuAika = System.currentTimeMillis();
        Astar astar = new Astar(verkko, verkko.getSolmu(10, 10), verkko.getSolmu(99, 99));
        astar.haku();
        long loppuAika = System.currentTimeMillis();
        long aika = loppuAika - alkuAika;
        assertTrue(aika < 100);
    }

    @Test
    public void testaaAikaIsoVerkko() {
        Verkko verkko = lukija.randomVerkko(1000, 1000);
        Astar astar = new Astar(verkko, verkko.getSolmu(102, 110), verkko.getSolmu(992, 991));
        long alkuAika = System.currentTimeMillis();
        astar.haku();
        long loppuAika = System.currentTimeMillis();
        long aika = loppuAika - alkuAika;
        assertTrue(aika < 2000);
    }

    @Test
    public void testaaAikaIsoVerkkoEuk() {
        Verkko verkko = lukija.randomVerkko(1000, 1000);
        Astar astar = new Astar(verkko, verkko.getSolmu(102, 110), verkko.getSolmu(992, 991), 1);
        long alkuAika = System.currentTimeMillis();
        astar.haku();
        long loppuAika = System.currentTimeMillis();
        long aika = loppuAika - alkuAika;
        assertTrue(aika < 2000);
    }

    @Test
    public void testaaAikaIsoVerkkoDijk() {
        Verkko verkko = lukija.randomVerkko(1000, 1000);
        Astar astar = new Astar(verkko, verkko.getSolmu(102, 110), verkko.getSolmu(992, 991), 2);
        long alkuAika = System.currentTimeMillis();
        astar.haku();
        long loppuAika = System.currentTimeMillis();
        long aika = loppuAika - alkuAika;
        assertTrue(aika < 2000);
    }

    @Test
    public void testaaKeskiIsoVerkkoEuklid() {
        Verkko verkko = lukija.randomVerkko(100, 100);
        long alkuAika = System.currentTimeMillis();
        Astar astar = new Astar(verkko, verkko.getSolmu(10, 10), verkko.getSolmu(99, 99), 1);
        astar.haku();
        long loppuAika = System.currentTimeMillis();
        long aika = loppuAika - alkuAika;
        assertTrue(aika < 100);
    }

    @Test
    public void testaaAikaKeskiVerkkoDijkstra() {
        Verkko verkko = lukija.randomVerkko(100, 100);
        long alkuAika = System.currentTimeMillis();
        Astar astar = new Astar(verkko, verkko.getSolmu(10, 10), verkko.getSolmu(99, 99), 2);
        astar.haku();
        long loppuAika = System.currentTimeMillis();
        long aika = loppuAika - alkuAika;
        assertTrue(aika < 100);
    }
}
