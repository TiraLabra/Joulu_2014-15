package reitinhaku.logiikka;

/**
 * heuristiikka luokka vastaa kustannusarvion maalisolmuun laskemisesta ja
 * vertailusta solmujen välillä
 */
public class Heuristiikka {

    private final Solmu maalisolmu;
    private int heuristiikka;

    /**
     *
     * @param maali maalisolmu
     */
    public Heuristiikka(Solmu maali) {
        this(maali, 0);
    }

    /**
     *
     * @param maali
     * @param heuristiikka
     */
    public Heuristiikka(Solmu maali, int heuristiikka) {
        this.maalisolmu = maali;
        this.heuristiikka = heuristiikka;
    }


    /**
     * Asettaa alku solmulle etäisyysarvion loppu solmuun käytetyn heuristiikan perusteella
     * Asettaa solmun arvioksi, joko manhattan etäisyyden loppusolmuun, euklidisen etäisyyden tai 0:n.
     * @param alku
     * @param loppu
     */
    public void arvio(Solmu alku, Solmu loppu) {
        double arvio = 0;
        if (heuristiikka == 0) {
            arvio = manhattanEtaisyys(alku, loppu);
        }
        if (heuristiikka == 1) {
            arvio = euklidinenEtaisyys(alku, loppu);
        }
        if (heuristiikka == 2) {
            arvio = dijkstra(alku, loppu);
        }
        alku.setArvio(arvio);
    }

    /**
     * laskee kahden solmun välisen Manhattan etäisyyden
     *
     * @param alku alkusolmu
     * @param loppu maalisolmu
     * @return manhattan etäisyys
     */
    public double manhattanEtaisyys(Solmu alku, Solmu loppu) {
        double kustannus = Math.abs(alku.getX() - loppu.getX()) + Math.abs(alku.getY() - loppu.getY());
        return kustannus;
    }

    /**
     * laskee kahden solmun välisen Euklidisen etäisyyden
     *
     * @param alku alkusolmu
     * @param loppu maalisolmu
     * @return euklidinen etäisyys
     */
    public double euklidinenEtaisyys(Solmu alku, Solmu loppu) {
        double kustannus = Math.sqrt(Math.pow(alku.getX() - loppu.getX(), 2) + Math.pow(alku.getY() - loppu.getY(), 2));
        return kustannus;
    }

    /**
     * täysin turha metodi, mutta selkeyden vuoksi lisäsin sen kuitenkin
     * @param alku
     * @param loppu
     * @return
     */
    public double dijkstra(Solmu alku, Solmu loppu) {
        return 0;
    }

    /**
     * Asettaa käytetyn heuristiikkafunktion
     * 0=Manhattan
     * 1=Euklid
     * muu=Dijkstra
     * @param heur
     */
    public void setHeuristiikka(int heur) {
        heuristiikka = heur;
    }

}
