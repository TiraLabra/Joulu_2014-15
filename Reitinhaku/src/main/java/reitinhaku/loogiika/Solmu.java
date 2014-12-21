package reitinhaku.loogiika;

import java.util.ArrayList;

/**
 * joku alkusöherrys,
 *
 * @author Samuel
 */
public class Solmu {

    private ArrayList<Solmu> naapurit;
    private final double[] koordinaatit;

    /**
     *
     * @param koordinaatit
     * @param naapurit
     */
    public Solmu(double[] koordinaatit, ArrayList<Solmu> naapurit) {
        this.koordinaatit = koordinaatit;
        this.naapurit = naapurit;
    }

    /**
     *
     * @return
     */
    public ArrayList<Solmu> getNaapurit() {
        return naapurit;
    }

    /**
     *
     * @return
     */
    public double[] getKoordinaatit() {
        return koordinaatit;
    }

    /**
     *
     * @param uusi
     */
    public void lisaaNaapuri(Solmu uusi) {
        naapurit.add(uusi);
    }

    /**
     * laskee euklidisen etäisyyden solmusta toiseen
     * @param solmu
     * @return
     */
    public double etaisyys(Solmu solmu) {
        double kustannus = 0.0;

        double[] koordinaatit2 = solmu.getKoordinaatit();
        for (int i = 0; i < koordinaatit.length; i++) {
            kustannus = Math.pow((koordinaatit[i] - koordinaatit2[i]), 2.0) + kustannus;
        }
        kustannus = Math.sqrt(kustannus);
        return kustannus;
    }

}
