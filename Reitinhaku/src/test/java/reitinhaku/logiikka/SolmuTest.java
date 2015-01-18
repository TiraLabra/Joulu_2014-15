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
public class SolmuTest {

    @Test
    public void testaaSolmu() {
        Solmu solmu = new Solmu(5, 2, 1);
        Solmu maali = new Solmu(10, 2, 2);
        assertTrue(solmu.getArvio() == Integer.MAX_VALUE);
        Heuristiikka uusi = new Heuristiikka(maali);
        solmu.setKustannus(10);
        uusi.arvio(solmu, maali);
        assertTrue(solmu.getArvio() == 5);
        assertTrue(solmu.getAlkuusPlusLoppuun() == 15);
    }
}
