package reitinhaku.tietorakenteet;

import reitinhaku.logiikka.Heuristiikka;
import reitinhaku.logiikka.Polku;

/**
 *
 * @author Samuel
 */
public class Prioriteettijono {

    private Polku[] keko;
    private int koko;
    private Heuristiikka heur;

    /**
     *
     * @param koko
     * @param evaluoija
     */
    public Prioriteettijono(int koko, Heuristiikka evaluoija) {
        this.koko = 0;
        this.keko = new Polku[koko + 1];
        this.heur = evaluoija;
    }

    /**
     *
     * @param polku
     */
    public void lisaa(Polku polku) {
        this.koko += 1;
        int i = this.koko;
        while (i > 1 && heur.compare(keko[parent(i)], polku) == -1) {
            vaihda(i, parent(i));
            i = parent(i);
        }
        keko[i] = polku;
    }

    /**
     *
     */
    public Polku popMin() {
        Polku min = keko[1];
        if (!isEmpty()) {
            vaihda(1, koko);
            koko -= 1;
            heapify(1);
        }
        return min;
    }

    public void vaihda(int s, int i) {
        Polku vaihto = keko[s];
        keko[s] = keko[i];
        keko[i] = vaihto;
    }

    private void heapify(int i) {
        int l = left(i);
        int r = right(i);
        int s;
        if (r <= koko) {
            if (heur.compare(keko[l], keko[r]) == 1) {
                s = l;
            } else {
                s = r;
            }
            if (heur.compare(keko[s], keko[i]) == 1) {
                vaihda(i, s);
                heapify(s);
            }
        } else if (l == koko && heur.compare(keko[l], keko[i]) == 1) {
            vaihda(l, i);

        }
    }

    /**
     *
     * @param i
     * @return
     */
    public int parent(int i) {
        return i/2;
    }

    private int left(int i) {
        return 2 * i;
    }

    private int right(int i) {
        return 2 * i + 1;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return koko == 0;
    }

}
