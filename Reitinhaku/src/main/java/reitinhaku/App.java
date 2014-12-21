package reitinhaku;

import reitinhaku.loogiikka.Astar;
import reitinhaku.loogiikka.Solmu;
import reitinhaku.loogiikka.Polku;
import java.util.ArrayList;

/**
 *
 * @author Samuel
 */
public class App {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        Solmu a = new Solmu(new double[]{1, 1}, new ArrayList<Solmu>());
        Solmu b = new Solmu(new double[]{-2, 1}, new ArrayList<Solmu>());
        Solmu c = new Solmu(new double[]{3, 1}, new ArrayList<Solmu>());
        Solmu d = new Solmu(new double[]{4, 1}, new ArrayList<Solmu>());
        Solmu e = new Solmu(new double[]{5, 2}, new ArrayList<Solmu>());

        a.lisaaNaapuri(b);
        d.lisaaNaapuri(e);
        b.lisaaNaapuri(e);

        Astar haku = new Astar();
        Polku p = haku.haku(a, e);
        System.out.println(p.getKustannus());


    }
}
