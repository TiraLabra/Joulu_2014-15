/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.tietorakenteet;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import reitinhaku.logiikka.Heuristiikka;
import reitinhaku.logiikka.Solmu;

/**
 *
 * @author Samuel
 */
public class PrioriteettijonoTest {

    private Solmu s1;
    private Solmu s2;
    private Solmu s3;
    private Solmu s4;
    private Solmu s5;
    private Solmu s6;
    Heuristiikka heur;
    private Prioriteettijono keko;

    public PrioriteettijonoTest() {
        s1 = new Solmu(0, 0, 4);
        s2 = new Solmu(1, 2, 6);
        s3 = new Solmu(3, 4, 1);
        s4 = new Solmu(7, 2, 7);
        s5 = new Solmu(5, 3, 2);
        s6 = new Solmu(10, 10, 2);
        heur = new Heuristiikka(s6);
        keko = new Prioriteettijono(100, heur);
        s1.setKustannus(0);
        s2.setKustannus(5);
        s3.setKustannus(4);
        s4.setKustannus(7);
        s5.setKustannus(9);
        s6.setKustannus(12);

    }

    @Test
    public void lisaaSolmuja() {
        keko.lisaa(s1);
        keko.lisaa(s2);
        assertTrue(keko.getHeapSize() == 2);
    }

    @Test
    public void popMinimi() {
        keko.lisaa(s2);
        keko.lisaa(s1);
        keko.lisaa(s3);
        assertTrue(keko.popMin().getPaino() == 1);
        assertTrue(keko.popMin().getPaino() == 4);
        assertTrue(keko.popMin().getPaino() == 6);
    }

}
