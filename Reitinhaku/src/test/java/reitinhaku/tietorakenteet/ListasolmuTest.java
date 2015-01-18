package reitinhaku.tietorakenteet;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import reitinhaku.logiikka.Solmu;

/**
 *
 * @author Samuel
 */
public class ListasolmuTest {

    private Solmu s1;
    private Solmu s2;

    public ListasolmuTest() {
        s1 = new Solmu(0, 0, 4);
        s2 = new Solmu(1, 2, 6);
    }

    @Test
    public void testaaListasolmu() {
        Listasolmu solmu1 = new Listasolmu(s1);
        Listasolmu solmu2 = new Listasolmu(s1);
        solmu1.setSeuraava(solmu2);
        assertTrue(solmu1.getSolmu()==s1);
        assertTrue(solmu1.getSeuraava()==solmu2);
    }

}
