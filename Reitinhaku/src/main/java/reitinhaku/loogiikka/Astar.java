package reitinhaku.loogiikka;

import java.util.PriorityQueue;

/**
 *
 * @author Samuel
 */
public class Astar {

    /**
     *
     */
    public Astar() {

    }

    /**
     * alustava Astar
     * @param lahto lahtosolmu
     * @param maali maalisalmu
     * @return palauttaa parhaan polum, jos sellainen löytyy
     */
    public Polku haku(Solmu lahto, Solmu maali) {
        Evaluoija evaluoija = new Evaluoija(maali);
        PriorityQueue<Polku> polut = new PriorityQueue<>(evaluoija);
        polut.add(new Polku(lahto, 0, null));
        while (!polut.isEmpty()) {
            Polku polku = polut.poll();
            if (polku.getSolmu().equals(maali)) {
                return polku;
            }
            for (Solmu s : polku.getSolmu().getNaapurit()) {
                Polku uusi = new Polku(s, polku.getKustannus() + polku.getSolmu().etaisyys(s), polku);
                polut.add(uusi);

            }

        }
        System.out.println("Polkua ei ole");
        return null;
    }
}
