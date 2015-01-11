package reitinhaku;

import java.util.Random;
import reitinhaku.logiikka.Astar;
import reitinhaku.logiikka.Verkko;
import reitinhaku.logiikka.Solmu;

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

    static Verkko verkko1 = new Verkko(v1);
    static Verkko verkko2 = new Verkko(v2);
    static Verkko verkko3 = new Verkko(v3);

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        polku(verkko1, verkko1.getSolmu(0, 0), verkko1.getSolmu(0, 2));
        polku(verkko2, verkko2.getSolmu(0, 0), verkko2.getSolmu(5, 3));
        polku(verkko3, verkko3.getSolmu(1, 0), verkko3.getSolmu(6, 4));

        Verkko r = randomVerkko(40, 20);
        polku(r, r.randomSolmu(), r.randomSolmu());

    }

    /**
     *
     * @param verkko
     * @param lahto
     * @param maali
     */
    public static void polku(Verkko verkko, Solmu lahto, Solmu maali) {
        System.out.println("");
        printVerkko(verkko);

        long alkuAika = System.currentTimeMillis();
        Astar a = new Astar(verkko, lahto, maali);
        Solmu p = a.haku();
        long loppuAika = System.currentTimeMillis();
        long aika = loppuAika - alkuAika;
        printPolku(p, verkko);
        System.out.println("Polun kustannus: " + p.getKustannus());
        System.out.println("Polun löytymiseen kului aikaa: " + (aika) + "ms.");
    }

    /**
     *
     * @param polku
     * @param verkko
     */
    public static void printPolku(Solmu polku, Verkko verkko) {
        String[][] p = new String[verkko.getMaxY() + 1][verkko.getMaxX() + 1];
        p[polku.getY()][polku.getX()] = "M";
        while (polku.getEdellinen() != null) {
            polku = polku.getEdellinen();
            p[polku.getY()][polku.getX()] = "P";      
        }
        p[polku.getY()][polku.getX()] = "L";
        System.out.println("Polku (L=Lahto, P=osa polkua, M=Maali):");
        for (int i = 0; i < verkko.getMaxY() + 1; i++) {
            for (int j = 0; j < verkko.getMaxX() + 1; j++) {
                if (p[i][j] != null) {
                    System.out.print(p[i][j] + " ");
                } else {
                    System.out.print("# ");
                }
            }
            System.out.println("");
        }
    }

    /**
     *
     * @param verkko
     */
    public static void printVerkko(Verkko verkko) {
        System.out.println("Verkko:");
        for (int i = 0; i < verkko.getMaxY() + 1; i++) {
            for (int j = 0; j < verkko.getMaxX() + 1; j++) {
                System.out.print(verkko.getSolmu(j, i).getPaino() + " ");
            }
            System.out.println("");
        }
    }

    /**
     *
     * @return
     */
    public static Verkko randomVerkko(int x, int y) {
        Random rand = new Random();
        int[][] v = new int[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                v[i][j] = rand.nextInt((9 - 1) + 1) + 1;
            }
        }
        Verkko verkko = new Verkko(v);
        return verkko;

    }
}
