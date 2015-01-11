package reitinhaku.logiikka;

import java.util.Random;
import reitinhaku.tietorakenteet.LinkitettyLista;

/**
 * Luokka vastaa haussa käytetyn verkon toiminnasta Verkko koostuu solmuista
 */
public class Verkko {

    private Solmu[][] verkko;
    private int maxX;
    private int maxY;

    /**
     *
     * @param kartta
     */
    public Verkko(int[][] kartta) {
        this.maxX = kartta[0].length - 1;
        this.maxY = kartta.length - 1;
        this.verkko = new Solmu[maxY + 1][maxX + 1];
        luoVerkko(kartta);
    }

    /**
     *
     * @param k
     */
    public void luoVerkko(int[][] k) {
        for (int i = 0; i < k[0].length; i++) {
            for (int j = 0; j < k.length; j++) {
                this.verkko[j][i] = new Solmu(i, j, k[j][i]);
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
        return verkko[y][x];
    }

    /**
     * Hakee annetun solmun naapurisolmut.
     *
     * @param solmu
     * @return naapurisolmulista
     */
    public LinkitettyLista getNaapurit(Solmu solmu) {
        LinkitettyLista naapurit = new LinkitettyLista();
        
        if ((solmu.getX() + 1) <= maxX) {
            naapurit.lisaaSolmu(getSolmu(solmu.getX() + 1, solmu.getY()));
        }
        if ((solmu.getX() - 1) >= 0) {
            naapurit.lisaaSolmu(getSolmu(solmu.getX() - 1, solmu.getY()));
        }
        if ((solmu.getY() + 1) <= maxY) {
            naapurit.lisaaSolmu(getSolmu(solmu.getX(), solmu.getY() + 1));
        }
        if ((solmu.getY() - 1) >= 0) {
            naapurit.lisaaSolmu(getSolmu(solmu.getX(), solmu.getY() - 1));
        }

        return naapurit;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public Solmu randomSolmu() {
        Random rand = new Random();
        int x = rand.nextInt(maxX + 1);
        int y = rand.nextInt(maxY + 1);
        return verkko[y][x];

    }
}
