/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.tietorakenteet;

import reitinhaku.logiikka.Solmu;

/**
 *
 * @author Samuel
 */
public class LinkitettyLista {

    private Listasolmu eka;
    private int koko;

    public LinkitettyLista() {
        koko = 0;
    }

    public Listasolmu getEka() {
        return eka;
    }

    public void lisaaSolmu(Solmu solmu) {
        Listasolmu uusi = new Listasolmu(solmu);
        uusi.setSeuraava(this.eka);
        koko++;
        eka = uusi;

    }

    public int getKoko() {
        return koko;
    }

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

    public boolean isEmpty() {
        return eka == null;
    }

}
