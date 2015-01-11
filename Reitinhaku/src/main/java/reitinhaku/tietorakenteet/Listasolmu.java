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
public class Listasolmu {

    private Solmu solmu;
    private Listasolmu seuraava;

    public Listasolmu(Solmu solmu) {
        this.solmu = solmu;
    }

    public Solmu getSolmu() {
        return solmu;
    }

    public Listasolmu getSeuraava() {
        return seuraava;
    }

    public void setSeuraava(Listasolmu s) {
        seuraava = s;
    }

}
