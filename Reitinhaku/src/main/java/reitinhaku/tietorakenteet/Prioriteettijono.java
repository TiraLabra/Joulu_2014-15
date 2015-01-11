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
     * @param koko prioriteettijono maksimi koko. ei muuta funktiota, kuin antaa Solmu listalle koko.
     * @param evaluoija heuristiikka olio, jota kÃ¤ytetÃ¤Ã¤n arvioimaan solmujen vÃ¤listÃ¤ prioriteettia
     */
    public Prioriteettijono(int koko, Heuristiikka evaluoija) {
        this.heapsize = 0;
        this.keko = new Solmu[koko + 1];
        this.heur = evaluoija;
    }

    /**
     * lisÃ¤Ã¤ solmun prioriteettijonoon
     * @param solmu lisattÃ¤vÃ¤ solmu
     */
    public void lisaa(Solmu solmu) {
        this.heapsize += 1;
        int i = this.heapsize;
        while (i > 1 && heur.vertaa(keko[parent(i)], solmu) == -1) {
            vaihda(i, parent(i));
            i = parent(i);
        }
        keko[i] = solmu;
    }

    /**
     * poistaa ja palauttaa prioriteettijonon pienimmÃ¤n kustannuksen omaavan solmun
     * @return  
     */
    public Solmu popMin() {
        Solmu min = keko[1];
        if (!isEmpty()) {
            vaihda(1, heapsize);
            heapsize -= 1;
            heapify(1);
        }
        return min;
    }

    /**
     * vaihtaa kahden  solmun paikkaa keossa
     * @param s1 ensimmÃ¤isen solmun indeksi
     * @param s2 toisen solmun indeksi
     */
    public void vaihda(int s1, int s2) {
        Solmu vaihto = keko[s1];
        keko[s1] = keko[s2];
        keko[s2] = vaihto;
    }
    
    /**
     * korjaa kekoehdon solmun i kohdalla, jos rikki
     * @param i
     */
    public void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int s;
        if (r <= heapsize) {
            if (heur.vertaa(keko[l], keko[r]) == 1) {
                s = l;
            } else {
                s = r;
            }
            if (heur.vertaa(keko[s], keko[i]) == 1) {
                vaihda(i, s);
                heapify(s);
            }
        } else if (l == heapsize && heur.vertaa(keko[l], keko[i]) == 1) {
            vaihda(l, i);

        }
    }

    /**
     * palauttaa solmun vanhemman indeksin
     * @param i solmun indeksi
     * @return vanhemman indeksin
     */
    public int parent(int i) {
        return i/2;
    }

    /**
     * palauttaa solmun vasemman lapsen indeksin
     * @param i solmun indeksi
     * @return lapsen indeksin
     */
    public int left(int i) {
        return 2 * i;
    }

    /**
     * palauttaa solmun oikean lapsen indeksin
     * @param i solmun indeksi
     * @return lapsen indeksin
     */
    public int right(int i) {
        return 2 * i + 1;
    }

    /**
     * tarkistaa onko keko tyhjÃ¤
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

}
