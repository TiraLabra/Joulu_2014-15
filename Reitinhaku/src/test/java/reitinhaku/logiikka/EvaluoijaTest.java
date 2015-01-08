package reitinhaku.logiikka;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Samuel
 */
public class EvaluoijaTest {

    private Verkko k;

    public EvaluoijaTest() {
        int[][] t = {
            {2, 2, 7, 2, 5, 1, 2},
            {5, 2, 6, 3, 4, 2, 5},
            {7, 2, 2, 5, 2, 5, 9},
            {1, 2, 4, 7, 4, 6, 7}};
        k = new Verkko(t);

    }

    @Test
    public void etaisyysOikein() {
        Heuristiikka eva = new Heuristiikka(k.getSolmu(2, 2));
        assertEquals(eva.taxicabArvio(k.getSolmu(0, 0), k.getSolmu(2, 2)), 4);

    }

    @Test
    public void etaisyysOikein2() {
        Heuristiikka eva = new Heuristiikka(k.getSolmu(2, 2));
        assertEquals(eva.taxicabArvio(k.getSolmu(1, 1), k.getSolmu(5, 2)), 5);

    }

    @Test
    public void compareOikein() {
        Polku p1 = new Polku (k.getSolmu(0, 0),0, null);
        Polku p2 = new Polku (k.getSolmu(0, 0),4, null);      
        Heuristiikka eva = new Heuristiikka(k.getSolmu(2, 2));
        
        assertEquals(eva.compare(p1, p2),-1);
        assertEquals(eva.compare(p2, p1),1);

    }

}
