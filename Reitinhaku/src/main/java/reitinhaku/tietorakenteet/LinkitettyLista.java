/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.tietorakenteet;

import reitinhaku.logiikka.Solmu;

/**
 * Vastaa linkitetyn listan toiminnasta
 * @author Samuel
 */
public class LinkitettyLista {

    private Listasolmu eka;
    private int koko;

    /**
     *
     */
    public LinkitettyLista() {
        koko = 0;
    }

    /**
     * Palauttaa ensimmäisen listasolmun
     * @return
     */
    public Listasolmu getEka() {
        return eka;
    }

    /**
     * asettaa uuden solmun listaan
     * toimii ajassa O(1)
     * @param solmu
     */
    public void lisaaSolmu(Solmu solmu) {
        Listasolmu uusi = new Listasolmu(solmu);
        uusi.setSeuraava(this.eka);
        koko++;
        eka = uusi;

    }

    /**
     * 
     * @return
     */
    public int getKoko() {
        return koko;
    }

    /**
     * tarkastaa löytyykö solmu listlta
     * toimii O(n) ajassa
     * @param solmu
     * @return
     */
    public boolean etsi(Solmu solmu) {
        Listasolmu etsi = eka;
        while (etsi!= null) {
            if (etsi.getSolmu() == solmu) {
                return true;
            }
            etsi = etsi.getSeuraava();
        }
        return false;
    }

    /**
     * tarkistaa onko lista tyhjä
     * @return
     */
    public boolean isEmpty() {
        return eka == null;
    }

}
