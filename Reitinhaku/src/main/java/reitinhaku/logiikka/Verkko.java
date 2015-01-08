/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reitinhaku.logiikka;

import java.util.ArrayList;

/**
 *
 * @author Samuel
 */
public class Verkko {

    private Solmu[][] kartta;
    public int maxX;
    public int maxY;

    /**
     *
     * @param kartta
     */
    public Verkko(int[][] kartta) {
        this.maxX = kartta[0].length-1;
        this.maxY = kartta.length-1;
        this.kartta = new Solmu[maxY+1][maxX+1];
        luoKartta(kartta);
    }

    /**
     *
     * @param k
     */
    public void luoKartta(int[][] k) {
        for (int i = 0; i < k[0].length; i++) {
            for (int j = 0; j < k.length; j++) {
                this.kartta[j][i] = new Solmu(i, j, k[j][i]);
            }
        }
    }

    /**
     *
     * @param x
     * @param y
     * @return
     */
    public Solmu getSolmu(int x, int y) {
        return kartta[y][x];
    }

    /**
     *
     * @param solmu
     * @return
     */
    public ArrayList<Solmu> getNaapurit(Solmu solmu) {
        ArrayList<Solmu> naapurit = new ArrayList<>();

        if ((solmu.getX() - 1)>=0) {
            naapurit.add(getSolmu(solmu.getX()-1, solmu.getY()));
        }
        if ((solmu.getX() + 1)<=maxX) {
            naapurit.add(getSolmu(solmu.getX()+1, solmu.getY()));
        }
        if ((solmu.getY() + 1)<=maxY) {
            naapurit.add(getSolmu(solmu.getX(), solmu.getY()+1));
        }
        if ((solmu.getY() - 1)>=0) {
            naapurit.add(getSolmu(solmu.getX(), solmu.getY()-1));
        }

        return naapurit;
    }
    
    

}
