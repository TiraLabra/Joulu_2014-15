package reitinhaku.tietorakenteet;

import reitinhaku.logiikka.Heuristiikka;
import reitinhaku.logiikka.Solmu;

/**
 * Luokka vastaa A* haussa kÃ¤ytettÃ¤vÃ¤n prioriteettijonon toiminnasta
 * Prioriteettijono on toteutettu minimikekona
 */
public class Prioriteettijono {

    private Solmu[] keko;
    private int heapsize;
    private Heuristiikka heur;

    /**
     * @param koko prioriteettijono maksimi koko. ei muuta funktiota, kuin asettaa keon koko.
     * @param evaluoija heuristiikka olio, jota kÃ¤ytetÃ¤Ã¤n arvioimaan solmujen
     * vÃ¤listÃ¤ prioriteettia
     */
    public Prioriteettijono(int koko, Heuristiikka evaluoija) {
        this.heapsize = 0;
        this.keko = new Solmu[koko + 1];
        this.heur = evaluoija;
    }

    /**
     * lisÃ¤Ã¤ solmun prioriteettijonoon
     *
     * @param solmu lisattÃ¤vÃ¤ solmu
     */
    public void lisaa(Solmu solmu) {
        this.heapsize += 1;
        int i = this.heapsize;
        while (i > 1 && keko[parent(i)].getAlkuusPlusLoppuun() > solmu.getAlkuusPlusLoppuun()) {
            vaihda(i, parent(i));
            i = parent(i);
        }
        keko[i] = solmu;
        keko[i].setIndeksi(i);
    }

    /**
     * poistaa ja palauttaa prioriteettijonon pienimmÃ¤n kustannuksen omaavan
     * solmun
     *
     * @return
     */
    public Solmu popMin() {
        Solmu min = keko[1];
        if (!isEmpty()) {
            vaihda(1, heapsize);
            heapsize -= 1;
            heapify(1);
        }
        min.setIndeksi(-1);
        return min;
    }

    /**
     * vaihtaa kahden solmun paikkaa keossa
     *
     * @param i1 ensimmÃ¤isen solmun indeksi
     * @param i2 toisen solmun indeksi
     */
    public void vaihda(int i1, int i2) {
        Solmu vaihto = keko[i1];
        keko[i1] = keko[i2];
        keko[i2] = vaihto;
        if (keko[i1] != null) {
            keko[i1].setIndeksi(i1);
        }
        if (keko[i2] != null) {
            keko[i2].setIndeksi(i2);
        }
    }

    /**
     * korjaa kekoehdon solmun i kohdalla, jos rikki
     * toimii log(n) ajassa
     * @param i
     */
    public void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int s;
        if (r <= heapsize) {
            if (keko[l].getAlkuusPlusLoppuun()< keko[r].getAlkuusPlusLoppuun()) {
                s = l;
            } else {
                s = r;
            }
            if (keko[s].getAlkuusPlusLoppuun()< keko[i].getAlkuusPlusLoppuun()) {
                vaihda(i, s);
                heapify(s);
            }
        } else if (l == heapsize && keko[l].getAlkuusPlusLoppuun() < keko[i].getAlkuusPlusLoppuun()) {
            vaihda(l, i);

        }
    }

    /**
     * palauttaa solmun vanhemman indeksin
     *
     * @param i solmun indeksi
     * @return vanhemman indeksin
     */
    public int parent(int i) {
        return i / 2;
    }

    /**
     * palauttaa solmun vasemman lapsen indeksin
     *
     * @param i solmun indeksi
     * @return lapsen indeksin
     */
    public int left(int i) {
        return 2 * i;
    }

    /**
     * palauttaa solmun oikean lapsen indeksin
     *
     * @param i solmun indeksi
     * @return lapsen indeksin
     */
    public int right(int i) {
        return 2 * i + 1;
    }

    /**
     * tarkistaa onko keko tyhjÃ¤
     *
     * @return true jos tyhjÃ¤
     */
    public boolean isEmpty() {
        return heapsize == 0;
    }

    /**
     *
     * @return
     */
    public int getHeapSize() {
        return heapsize;
    }

    /**
     * Solmun arvon pienentämisen jälkeen asettaa solmun oikeaan paikkaan keossa
     * toimii log(n) ajassa
     * @param solmu
     */
    public void korjaaPienennys(Solmu solmu) {
        int indeksi = solmu.getIndeksi();
        while (indeksi > 1 && keko[parent(indeksi)].getAlkuusPlusLoppuun() > keko[indeksi].getAlkuusPlusLoppuun()) {
            vaihda(indeksi, parent(indeksi));
            indeksi = parent(indeksi);
        }
    }

}
