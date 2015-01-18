package reitinhaku.logiikka;

import reitinhaku.tietorakenteet.Prioriteettijono;
import reitinhaku.tietorakenteet.Listasolmu;

/**
 * Luokka vastaa A* haun toiminnasta
 */
public class Astar {

    private Heuristiikka heuristiikka;
    private Solmu lahto;
    private Solmu maali;
    private Verkko verkko;
    private Prioriteettijono keko;

    /**
     *
     * @param verkko
     * @param lahto
     * @param maali
     */
    public Astar(Verkko verkko, Solmu lahto, Solmu maali) {
        this(verkko, lahto, maali, 0);
    }

    /**
     *
     * @param verkko
     * @param lahto
     * @param maali
     * @param heur
     */
    public Astar(Verkko verkko, Solmu lahto, Solmu maali, int heur) {
        this.verkko = verkko;
        this.lahto = lahto;
        this.maali = maali;
        this.heuristiikka = new Heuristiikka(maali, heur);
        alustaPrioriteettijono();

    }

    /**
     * lisää verkon solmut prioriteettijonoon asettaa lähtosolmun kustannukseksi
     * 0 aikavaativuus |V|*log|V|: käy jokaisen solmun kerran läpi ja lisää
     * kekoon, joka on pahimmillaan log|V| operaatio
     */
    public void alustaPrioriteettijono() {
        this.keko = new Prioriteettijono((verkko.getMaxX() + 1) * (verkko.getMaxY() + 1), heuristiikka);
        Listasolmu solmu = verkko.getKaikkiSolmut().getEka();
        lahto.setKustannus(0);
        while (solmu.getSeuraava() != null) {
            heuristiikka.arvio(solmu.getSolmu(), maali);
            keko.lisaa(solmu.getSolmu());
            solmu = solmu.getSeuraava();
        }
        heuristiikka.arvio(solmu.getSolmu(), maali);
        keko.lisaa(solmu.getSolmu());
    }

    /**
     * vaihtaa käytettyä heuristiikkafunktiota i=0: Manhattan etäisyys i=1:
     * Euklidinen etäisyys muilla arvoilla: etäisyys arvio on kaikilla solmuilla
     * 0 -> toimii kuten Dijkstran algoritmi
     *
     * @param i
     */
    public void setHeuristiikka(int i) {
        heuristiikka.setHeuristiikka(i);
        alustaPrioriteettijono();
    }

    /**
     * A* haku * 
     * Aikavaativuus on pahimmillaan O((|E| + |V|) log |V|)
     * Jokaisella solmulle V pienimmän valitseminen (|V| log |V|) ja 
     * korkeintaan jokaiselle vierussolmulle E haetaan paino ja pienennetään sen arvoa (|E| log |V|) keossa.
     * @return palauttaa parhaan polun, jos sellainen on olemassa
     */
    public Solmu haku() {
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
                    keko.korjaaPienennys(naapuri);
                }
                listanaapuri = listanaapuri.getSeuraava();
            }
        }
        return null;
    }
}
