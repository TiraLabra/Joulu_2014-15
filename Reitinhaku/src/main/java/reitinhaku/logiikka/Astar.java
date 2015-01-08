package reitinhaku.logiikka;

import reitinhaku.tietorakenteet.Prioriteettijono;
import java.util.ArrayList;

/**
 *
 * @author Samuel
 */
public class Astar {

    private Verkko kartta;
    private Solmu lahto;
    private Solmu maali;

    /**
     *
     * @param k
     * @param lahto
     * @param maali
     */
    public Astar(Verkko k, Solmu lahto, Solmu maali) {
        this.kartta = k;
        this.lahto = lahto;
        this.maali = maali;

    }

    /**
     * alustava Astar
     *
     * @return palauttaa parhaan polum, jos sellainen löytyy
     */
    public Polku haku() {
        Heuristiikka heur = new Heuristiikka(maali);
        Prioriteettijono keko = new Prioriteettijono(1000, heur);
        keko.lisaa(new Polku(lahto, lahto.getPaino(), null));
        ArrayList<Solmu> kasitellyt = new ArrayList<>();

        while (!keko.isEmpty()) {
            Polku polku = keko.popMin();            
            if (!kasitellyt.contains(polku.getSolmu())) {
                kasitellyt.add(polku.getSolmu());
                if (polku.getSolmu().equals(maali)) {
                    return polku;
                }
                for (Solmu s : kartta.getNaapurit(polku.getSolmu())) {
                    Polku uusi = new Polku(s, polku.getKustannus() + s.getPaino(),  polku);
                    keko.lisaa(uusi);
                }
            }
        }

        return null;
    }
}
