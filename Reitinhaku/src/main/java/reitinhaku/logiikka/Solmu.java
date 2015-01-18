package reitinhaku.logiikka;

/**
 * Solmu luokka kuvaa verkon yksittäisen solmun tietoja
 */
public class Solmu {

    private int paino;// kustannus solmuun siirtymiselle 
    private int x;
    private int y;
    private int kustannus; //pienin tunnettu kustannus alkusolmusta kyseiseen solmuun
    private double arvio; // heuristinen arvio kustannuksesta kyseisestä solmusta maalisolmuun
    private Solmu polku; // solmua edeltävä solmu pienimmän tunnetun kustannuksen polussa
    private int indeksi; //solmun indeksi prioriteettijonossa;

    /**
     *
     * @param x x koordinaatti
     * @param y y koordinaatti
     * @param paino solmun paino
     */
    public Solmu(int x, int y, int paino) {
        this.x = x;
        this.y = y;
        this.paino = paino;
        this.kustannus = Integer.MAX_VALUE;
        this.arvio = Integer.MAX_VALUE;
        this.indeksi = -1;
    }

    /**
     * palauttaa x koordinaatin
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     * palauttaa indeksin keossa
     * @return
     */
    public int getIndeksi() {
        return indeksi;
    }

    /**
     * palauttaa y koordinaatin
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     * palauttaa solmun painon
     * @return
     */
    public int getPaino() {
        return paino;
    }

    /**
     * palauttaa etäisyysarvion solmusta maalisolmuun
     * @return
     */
    public double getArvio() {
        return arvio;
    }

    /**
     * palauttaa piemimmän tunnetun kustannuksen alkusolmu tähän solmuun ja heuristisen arvion tästä solmusta maalisolmuun summan
     * @return
     */
    public double getAlkuusPlusLoppuun() {
        return (arvio+ (double) kustannus);
    }

    /**
     * palauttaa kustannuksen alkusolmusta tähän solmuun
     * @return
     */
    public int getKustannus() {
        return kustannus;
    }

    /**
     * palauttaa edullisimman tunnetun polun edellisen solmun
     * @return
     */
    public Solmu getEdellinen() {
        return polku;
    }

    /**
     * asettaa kustannuksen alkusolmusta tähän solmuun
     * @param k
     */
    public void setKustannus(int k) {
        kustannus = k;
    }

    /**
     * asettaa solmua edeltävän solmun polulla
     * @param s
     */
    public void setEdellinen(Solmu s) {
        this.polku = s;
    }

    /**
     * asettaa solmun indeksin minimikeossa
     * @param i
     */
    public void setIndeksi(int i) {
        indeksi = i;
    }

    /**
     *
     * @param a
     */
    public void setArvio(double a) {
        arvio = a;
    }

}
