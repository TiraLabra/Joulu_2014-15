package reitinhaku.logiikka;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Samuel
 */
public class HeuristiikkaTest {

    private Verkko v;
    private Heuristiikka eva;

    public HeuristiikkaTest() {
        int[][] t = {
            {2, 2, 7, 2, 5, 1, 2},
            {5, 2, 6, 3, 4, 2, 5},
            {7, 2, 2, 5, 2, 5, 9},
            {1, 2, 4, 7, 4, 6, 7}};
        v = new Verkko(t);
        eva = new Heuristiikka(v.getSolmu(2, 2));

    }

    @Test
    public void comparePienempi() {
        Solmu s1 = v.getSolmu(0, 0);
        s1.setKustannus(0);
        Solmu s2 = v.getSolmu(5, 3);
        s2.setKustannus(10);
        assertEquals(eva.vertaa(s1, s2), 1);

    }

    @Test
    public void compareSuurempi() {
        Solmu s1 = v.getSolmu(0, 0);
        s1.setKustannus(10);
        Solmu s2 = v.getSolmu(5, 3);
        s2.setKustannus(10);
        assertEquals(eva.vertaa(s1, s2), -1);
    }

    @Test
    public void etaisyysOikein() {
        assertEquals(eva.manhattanEtaisyys(v.getSolmu(0, 0), v.getSolmu(2, 2)), 4);

    }

    @Test
    public void etaisyysOikein2() {
        assertTrue(Math.abs(eva.euklidinenEtaisyys(v.getSolmu(1, 1), v.getSolmu(5, 2)) - 4.123) < 0.01);

    }

}
