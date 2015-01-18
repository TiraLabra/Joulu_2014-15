/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.tietorakenteet;

import org.junit.Test;
import static org.junit.Assert.*;
import reitinhaku.logiikka.Solmu;

/**
 *
 * @author Samuel
 */
public class LinkitettyListaTest {

    private Solmu s1;
    private Solmu s2;
    private LinkitettyLista lista;

    public LinkitettyListaTest() {
        s1 = new Solmu(0, 0, 4);
        s2 = new Solmu(1, 2, 6);
        lista = new LinkitettyLista();

    }

    @Test
    public void testaaListanKoko() {
        assertTrue(lista.getKoko() == 0);
        lista.lisaaSolmu(s1);
        lista.lisaaSolmu(s2);
        assertTrue(lista.getKoko() == 2);

    }

    @Test
    public void testaaEtsiLoytyy() {
        lista.lisaaSolmu(s1);
        assertTrue(lista.etsi(s1) == true);
    }

    @Test
    public void testaaEtsiEiLoydy() {
        assertTrue(lista.etsi(s1) == false);
    }

}
