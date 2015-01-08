package reitinhaku.logiikka;

/**
 *
 * @author Samuel
 */
public class Heuristiikka  {

    private final Solmu maalisolmu;

    /**
     *
     * @param maali
     */
    public Heuristiikka(Solmu maali) {
        this.maalisolmu = maali;
    }


    public int compare(Polku p1, Polku p2) {
        if (taxicabArvio(p1.getSolmu(),maalisolmu) + p1.getKustannus() < taxicabArvio(p2.getSolmu(),maalisolmu) + p2.getKustannus()) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     *
     * @param alku
     * @param loppu
     * @return
     */
    public int taxicabArvio(Solmu alku, Solmu loppu) {

        int dx = Math.abs(alku.getX() - loppu.getX());
        int dy = Math.abs(alku.getY() - loppu.getY());
        int kustannus = dx + dy;
        return kustannus;
    }

}
