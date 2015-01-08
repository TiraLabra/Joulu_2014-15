package reitinhaku;

import java.util.Random;
import reitinhaku.logiikka.Astar;
import reitinhaku.logiikka.Polku;
import reitinhaku.logiikka.Verkko;
import reitinhaku.logiikka.Solmu;
import reitinhaku.tietorakenteet.Prioriteettijono;

/**
 *
 * @author Samuel
 */
public class App {

    static int[][] v1 = {
        {1, 2, 1, 1, 1, 1, 1},
        {1, 2, 1, 1, 1, 1, 1},
        {1, 2, 1, 1, 1, 1, 1},
        {1, 2, 1, 1, 1, 1, 1}};
    static int[][] v2 = {
        {2, 2, 7, 2, 5, 1, 2},
        {5, 2, 6, 3, 4, 2, 5},
        {7, 2, 2, 5, 2, 5, 9},
        {2, 1, 5, 2, 4, 1, 1},
        {7, 2, 2, 5, 3, 2, 9},
        {1, 2, 4, 7, 4, 6, 7}};
    static int[][] v3 = {
        {1, 2, 4, 1, 1, 1, 1, 2, 5, 7, 8},
        {1, 2, 6, 8, 1, 1, 1, 5, 2, 1, 5},
        {1, 2, 1, 1, 1, 1, 1, 4, 1, 1, 4},
        {1, 2, 1, 1, 1, 1, 1, 2, 1, 4, 4},
        {1, 2, 1, 1, 1, 1, 1, 4, 1, 2, 4},
        {1, 2, 1, 1, 1, 1, 1, 1, 5, 2, 5}};
    static int[][] v4 = {
        {2, 8, 5, 6, 7},
        {6, 2, 7, 6, 1},
        {9, 3, 1, 1, 5},
        {2, 6, 1, 2, 1},
        {6, 1, 3, 5, 1}};

    static Verkko verkko1 = new Verkko(v1);
    static Verkko verkko2 = new Verkko(v2);
    static Verkko verkko3 = new Verkko(v3);
    static Verkko verkko4 = new Verkko(v4);

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        polku(verkko1, verkko1.getSolmu(0, 0), verkko1.getSolmu(6, 2));

        polku(verkko2, verkko2.getSolmu(0, 0), verkko2.getSolmu(5, 3));

        polku(verkko3, verkko3.getSolmu(1, 0), verkko3.getSolmu(6, 4));

        Verkko r = randomVerkko();
        polku(r, r.getSolmu(1, 2), r.getSolmu(19, 9));

    }

    /**
     *
     * @param v
     * @param lahto
     * @param maali
     */
    public static void polku(Verkko v, Solmu lahto, Solmu maali) {


        Astar a = new Astar(v, lahto, maali);
        Polku p = a.haku();
        System.out.println("");
        printVerkko(v);
        printPolku(p, v);
        System.out.println("Polun kustannus: " + p.getKustannus());

    }

    /**
     *
     * @param polku
     * @param k
     */
    public static void printPolku(Polku polku, Verkko k) {
        System.out.println("Polku (L=Lahto, P=osa polkua, M=Maali):");
        String[][] p = new String[k.maxY + 1][k.maxX + 1];
        for (int i = 0; i < k.maxY + 1; i++) {
            for (int j = 0; j < k.maxX + 1; j++) {
                p[i][j] = "#";
            }
        }
        p[polku.getSolmu().getY()][polku.getSolmu().getX()] = "M";
        while (polku.getEdellinen() != null) {
            polku = polku.getEdellinen();
            p[polku.getSolmu().getY()][polku.getSolmu().getX()] = "P";
            if (polku.getEdellinen() == null) {
                p[polku.getSolmu().getY()][polku.getSolmu().getX()] = "L";
            }
        }
        for (int i = 0; i < k.maxY + 1; i++) {
            for (int j = 0; j < k.maxX + 1; j++) {
                System.out.print(p[i][j] + " ");
            }
            System.out.println("");
        }
    }

    /**
     *
     * @param k
     */
    public static void printVerkko(Verkko k) {
        System.out.println("Verkko:");
        for (int i = 0; i < k.maxY + 1; i++) {
            for (int j = 0; j < k.maxX + 1; j++) {
                System.out.print(k.getSolmu(j, i).getPaino() + " ");
            }
            System.out.println("");
        }
    }

    /**
     *
     * @return
     */
    public static Verkko randomVerkko() {
        Random rand = new Random();
        int[][] v = new int[10][20];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 20; j++) {
                v[i][j] = rand.nextInt((9 - 1) + 1) + 1;
            }
        }
        Verkko verkko = new Verkko(v);
        return verkko;

    }
}
