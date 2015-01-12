package PersistentDataStructures;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import persistentDataStructures.PersistentVector;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author laurikin
 */
public class PersistentVectorTestSlow {

    private PersistentVector<Integer> v;
    private static PersistentVector<Integer> vBig;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        vBig = new PersistentVector<>();
        for (int i = 0; i < 1000000; i++) {
            vBig = vBig.conj(i);
        }
    }

    @Before
    public void setUp() {
        v = new PersistentVector<>();
    }

    @Test(timeout=1000)
    public void testInsertingAndRemovingManyItems() {
        for (int i = 0; i < 1000; i++) {
            vBig = vBig.conj(i);
            vBig = vBig.pop();
        }
        assertEquals(1000000, vBig.count());
    }

    @Test(timeout=1000)
    public void testGettingManyIntegers() {
        for (int i = 0; i < 1000000; i++) {
            vBig.get(i);
        }
        assertEquals(1000000, vBig.count());
    }
}
