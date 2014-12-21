/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.loogiikka;

/**
 *
 * @author Samuel
 */
public class Polku {
    private Solmu solmu;
    private double kustannus;
    private Polku edellinen;
    
    /**
     *
     * @param solmu
     * @param kustannus
     * @param edellinen
     */
    public Polku(Solmu solmu, double kustannus, Polku edellinen) {
        this.solmu=solmu;
        this.kustannus=kustannus;
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
    public double getKustannus() {
        return kustannus;
    }

    /**
     *
     * @return
     */
    public Polku getEdellinen() {
        return edellinen;
    }
    
}
