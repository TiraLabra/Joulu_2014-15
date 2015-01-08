/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.logiikka;

/**
 *
 * @author Samuel
 */
public class Polku {

    private Solmu solmu;
    private int kustannus;
    private int heuristiikka;
    private Polku edellinen;

    /**
     *
     * @param solmu
     * @param kustannus
     * @param edellinen
     */
    public Polku(Solmu solmu, int kustannus, Polku edellinen) {
        this.solmu = solmu;
        this.kustannus = kustannus;
        this.edellinen = edellinen;

    }

    /**
     *
     * @return
     */
    public Solmu getSolmu() {
        return solmu;
    }

    /**
     *
     * @return
     */
    public int getKustannus() {
        return kustannus;
    }

    /**
     *
     * @param k
     */
    public void setKustannus(int k) {
        kustannus = k;
    }

    /**
     *
     * @return
     */
    public Polku getEdellinen() {
        return edellinen;
    }

}
