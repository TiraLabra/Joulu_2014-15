package reitinhaku.logiikka;

import reitinhaku.tietorakenteet.Prioriteettijono;
import reitinhaku.tietorakenteet.Listasolmu;

/**
 * Luokka vastaa A* haun toiminnasta
 */
public class Astar {

    private Verkko verkko;
    private Solmu lahto;
    private Solmu maali;

    /**
     *
     * @param verkko haussa käytetty verkko
     * @param lahto lahtosolmu
     * @param maali maalisolmu
     */
    public Astar(Verkko verkko, Solmu lahto, Solmu maali) {
        this.verkko = verkko;
        this.lahto = lahto;
        this.maali = maali;

    }

    /**
     * A* haku
     *
     * @return palauttaa parhaan polum, jos sellainen löytyy
     */
    public Solmu haku() {
        Heuristiikka heur = new Heuristiikka(maali);
        Prioriteettijono keko = new Prioriteettijono(1000, heur);
        keko.lisaa(lahto);
        lahto.setKustannus(0);
        while (!keko.isEmpty()) {
            Solmu solmu = keko.popMin();
                if (solmu.equals(maali)) {
                    return solmu;
                }
                    Listasolmu listanaapuri = verkko.getNaapurit(solmu).getEka();
                    while (listanaapuri != null) {
                        Solmu naapuri = listanaapuri.getSolmu();
                        if (naapuri.getKustannus() > solmu.getKustannus() + naapuri.getPaino()) {
                            naapuri.setKustannus(solmu.getKustannus() + naapuri.getPaino());
                            naapuri.setEdellinen(solmu);
                            keko.lisaa(naapuri);
                        }
                        listanaapuri = listanaapuri.getSeuraava();
                    }
                }
            
            return null;
        }
    }
