package reitinhaku.loogiikka;

import java.util.Comparator;

/**
 *
 * @author Samuel
 */
public class Evaluoija implements Comparator<Polku> {

    private final Solmu maalisolmu;

    /**
     *
     * @param maali
     */
    public Evaluoija(Solmu maali) {
        this.maalisolmu = maali;
    }



    @Override
    public int compare(Polku p1, Polku p2) {
        if (p1.getSolmu().etaisyys(maalisolmu) + p1.getKustannus() >= p2.getSolmu().etaisyys(maalisolmu) + p2.getKustannus()) {
            return 1;
        } else {
            return -1;
        }
    }
}
