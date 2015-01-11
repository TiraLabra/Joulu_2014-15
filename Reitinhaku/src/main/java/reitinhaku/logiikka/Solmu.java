package reitinhaku.logiikka;

/**
 * Solmu luokka kuvaa verkon yksittäisen solmun tietoja 
 */
public class Solmu {
    private int paino;// kustannus solmuun siirtymiselle 
    private int x;
    private int y;
    private int kustannus; //pienin tunnettu kustannus alkusolmusta kyseiseen solmuun
    private Solmu polku; // solmua edeltävä solmu pienimmän tunnetun kustannuksen polussa

    /**
     *
     * @param x
     * @param y
     * @param paino
     */
    public Solmu(int x, int y, int paino) {
        this.x = x;
        this.y = y;
        this.paino = paino;
        this.kustannus = Integer.MAX_VALUE;
    }

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return
     */
    public int getPaino() {
        return paino;
    }

    /**
     *
     * @return
     */
    public int getKustannus() {
        return kustannus;
    }

    /**
     *
     * @return
     */
    public Solmu getEdellinen() {
        return polku;
    }

    /**
     *
     * @param k
     */
    public void setKustannus(int k) {
        kustannus = k;
    }

    /**
     *
     * @param s
     */
    public void setEdellinen(Solmu s) {
        this.polku = s;
    }

}
