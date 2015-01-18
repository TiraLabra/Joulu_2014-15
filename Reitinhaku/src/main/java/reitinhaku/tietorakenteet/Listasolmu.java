/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.tietorakenteet;

import reitinhaku.logiikka.Solmu;

/**
 * Vastaa linkitetyssä listassa sijaitsevien solmujen toiminnasta
 * @author Samuel
 */
public class Listasolmu {

    private Solmu solmu;
    private Listasolmu seuraava;

    /**
     * Luo listasolmun solmu olion perusteella
     * @param solmu
     */
    public Listasolmu(Solmu solmu) {
        this.solmu = solmu;
    }

    /**
     * Palauttaa solmu olion
     * @return
     */
    public Solmu getSolmu() {
        return solmu;
    }

    /**
     * Palauttaa listasolmun seuraavan listasolmun
     * @return
     */
    public Listasolmu getSeuraava() {
        return seuraava;
    }

    /**
     * asettaa seuraavan listasolmun
     * @param s
     */
    public void setSeuraava(Listasolmu s) {
        seuraava = s;
    }

}
