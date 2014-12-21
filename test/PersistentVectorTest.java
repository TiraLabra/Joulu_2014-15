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
public class PersistentVectorTest {

    public PersistentVector v;
    
    public PersistentVectorTest() {
    }
    
    @Before
    public void setUp() {
        v = new PersistentVector<>();
    }

    @Test
    public void callingConstructorWithNoParametersShouldInitializeAnEmptyVector() {
        assertEquals(0, v.count());
    }
    
    @Test
    public void testCountAfterAddingOneItem() {
        PersistentVector<Integer> v2 = v.conj(1);
        assertEquals(1, v2.count());
    }

    @Test
    public void testGetAfterAddingOneItem() {
        PersistentVector<Integer> v2 = v.conj(1);
        assertEquals(new Integer(1), v2.get(0));
    }

}
