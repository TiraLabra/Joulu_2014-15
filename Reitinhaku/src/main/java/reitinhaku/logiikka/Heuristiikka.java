package reitinhaku.logiikka;

/**
 * heuristiikka luokka vastaa kustannusarvion maalisolmuun laskemisesta ja vertailusta solmujen välillä
 */
public class Heuristiikka {

    private final Solmu maalisolmu;

    /**
     * 
     * @param maali maalisolmu
     */
    public Heuristiikka(Solmu maali) {
        this.maalisolmu = maali;
    }

    /**
     * vertaa solmujen s1 ja s2 kustannusarviota maalisolmuun
     * @param s1 solmu 1
     * @param s2 solmu 2
     * @return 1 jos kustannusarvio s1:sta maalisolmuun on pienempi kuin s2:sta
     */
    public int vertaa(Solmu s1, Solmu s2) {
        if (manhattanEtaisyys(s1, maalisolmu) + s1.getKustannus() < manhattanEtaisyys(s2, maalisolmu) + s2.getKustannus()) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * laskee kahden solmun välisen Manhattan etäisyyden
     * @param alku alkusolmu
     * @param loppu maalisolmu
     * @return manhattan etäisyys
     */
    public int manhattanEtaisyys(Solmu alku, Solmu loppu) {
        int kustannus = Math.abs(alku.getX() - loppu.getX()) + Math.abs(alku.getY() - loppu.getY());
        return kustannus;
    }

    /**
     * laskee kahden solmun välisen Euklidisen etäisyyden
     * @param alku alkusolmu
     * @param loppu maalisolmu
     * @return  euklidinen etäisyys
     */
    public double euklidinenEtaisyys(Solmu alku, Solmu loppu) {
        double kustannus = Math.sqrt(Math.pow(alku.getX() - loppu.getX(), 2) + Math.pow(alku.getY() - loppu.getY(), 2));    
        return kustannus;
    }
}
