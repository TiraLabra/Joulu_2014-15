/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author laurikin
 */
public class PersistentVectorTestSlow {

    public PersistentVector v;
    
    public PersistentVectorTestSlow() {
    }
    
    @Before
    public void setUp() {
        v = new PersistentVector<>();
    }

    @Test
    public void testAddingMillionItems() {
        for (int i = 0; i < 1000000; i++) {
            v = v.conj(i);
        }
        for (int i = 0; i < 1000000; i++) {
            assertEquals(i, v.get(i));
        }
    }
}
