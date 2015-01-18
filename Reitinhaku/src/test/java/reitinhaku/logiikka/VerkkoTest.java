/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.logiikka;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Samuel
 */
public class VerkkoTest {

    private Verkko verkko;

    public VerkkoTest() {
        int[][] v = {
            {2, 2, 7, 2, 5, 1, 2},
            {5, 2, 6, 3, 4, 2, 5},
            {7, 2, 2, 5, 2, 5, 9},
            {1, 2, 4, 7, 4, 6, 7}};
        verkko = new Verkko(v);
    }

    @Test
    public void koko() {
        assertTrue(verkko.getMaxX() == 6 && verkko.getMaxY() == 3);
    }

    @Test
    public void solmut() {
        assertTrue(verkko.getSolmu(0, 0).getPaino() == 2);
    }

    @Test
    public void kulmaSolunNaapurit() {
        assertTrue(verkko.getNaapurit(verkko.getSolmu(0, 0)).getKoko() == 2);
    }

    @Test
    public void kaikkiSolmut() {
        assertTrue( verkko.getKaikkiSolmut().getKoko() == 28);
    }
}
