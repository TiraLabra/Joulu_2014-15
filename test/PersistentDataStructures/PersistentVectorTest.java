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

/**
 *
 * @author laurikin
 */
public class PersistentVectorTest {

    public PersistentVector<Integer> v;
    
    public PersistentVectorTest() {
    }
    
    @Before
    public void setUp() {
        v = new PersistentVector<>();
    }

    @Test
    public void testConstructorWithoutParameters() {
        assertEquals(0, v.count());
    }
    
    @Test
    public void testGetOnEmptyVector() {
        assertEquals(null, v.get(0));
    }

    @Test
    public void testCountOnEmptyVector() {
        assertEquals(0, v.count());
    }

    @Test
    public void testPeekOnEmptyVector() {
        assertEquals(null, v.peek());
    }

    @Test
    public void testCountAfterAddingOneItem() {
        PersistentVector<Integer> v2 = v.conj(1);
        assertEquals(1, v2.count());
    }

    @Test
    public void testAddingOneItem() {
        PersistentVector<Integer> v2 = v.conj(1);
        assertEquals(new Integer(1), v2.get(0));
    }

    @Test
    public void testPeekAfterAddingOneItem() {
        PersistentVector<Integer> v2 = v.conj(1);
        assertEquals(new Integer(1), v2.peek());
    }

    @Test
    public void testPersistencyAfterAddingOneItem() {
        PersistentVector<Integer> v2 = v.conj(1);
        assertEquals(null, v.peek());
        assertEquals(0, v.count());
    }

    @Test
    public void testAssocAfterAddingOneItem() {
        PersistentVector<Integer> v2 = v.conj(1);
        PersistentVector<Integer> v3 = v.assoc(0, 2);
        assertEquals(new Integer(2), v3.get(0));
    }

    @Test
    public void testPersistencyAfterOneAssoc() {
        PersistentVector<Integer> v2 = v.conj(1);
        PersistentVector<Integer> v3 = v.assoc(0, 2);
        assertEquals(new Integer(1), v2.get(0));
    }

    @Test
    public void testPopAfterAddingOneItem() {
        PersistentVector<Integer> v2 = v.conj(1).pop();
        assertEquals(null, v2.get(0));
        assertEquals(0, v2.count());
    }

    @Test
    public void testPersistancyAfterPoppingOneItem() {
        PersistentVector<Integer> v2 = v.conj(1);
        v2.pop();
        assertEquals(new Integer(1), v2.get(0));
        assertEquals(1, v2.count());
    }

    @Test
    public void testCountAfterAddingThreeItems() {
        PersistentVector<Integer> v2 = v.conj(1).conj(2).conj(3);
        assertEquals(3, v2.count());
    }

    @Test
    public void testAddingThreeItems() {
        PersistentVector<Integer> v2 = v.conj(1).conj(2).conj(3);
        assertEquals(new Integer(1), v2.get(0));
        assertEquals(new Integer(2), v2.get(1));
        assertEquals(new Integer(3), v2.get(2));
    }

    @Test
    public void testPopAfterAddingThreeItems() {
        PersistentVector<Integer> v2 = v.conj(1).conj(2).conj(3).pop();
        assertEquals(new Integer(1), v2.get(0));
        assertEquals(new Integer(2), v2.get(1));
        assertEquals(null, v2.get(2));
        assertEquals(2, v2.count());
    }

    @Test
    public void testPeekAfterAddingThreeItems() {
        PersistentVector<Integer> v2 = v.conj(1).conj(2).conj(3);
        assertEquals(new Integer(3), v2.peek());
    }

    @Test
    public void testAdding_33_Items() {
        for (int i = 0; i < 33; i++) {
            v = v.conj(i);
        }
        for (int i = 0; i < 33; i++) {
            assertEquals(new Integer(i), v.get(i));
        }
    }

    @Test
    public void testPopAfterAdding_33_Items() {
        for (int i = 0; i < 33; i++) {
            v = v.conj(i);
        }
        PersistentVector<Integer> v2 = v.pop();
        for (int i = 0; i < 32; i++) {
            assertEquals(new Integer(i), v2.get(i));
            assertEquals(new Integer(i), v.get(i));
        }
        assertEquals(null, v2.get(32));
        assertEquals(new Integer(32), v.get(32));
    }

    @Test
    public void testConjingAndPoppingOverDepthThreshold() {
        for (int i = 0; i < 33; i++) {
            v = v.conj(i);
        }
        v = v.pop().pop().conj(100).conj(101);
        for (int i = 0; i < 30; i++) {
            assertEquals(new Integer(i), v.get(i));
        }
        assertEquals(new Integer(100), v.get(31));
        assertEquals(new Integer(101), v.get(32));
    }

    @Test
    public void testAdding_103_Items() {
        for (int i = 0; i < 103; i++) {
            v = v.conj(i);
        }
        for (int i = 0; i < 103; i++) {
            assertEquals(new Integer(i), v.get(i));
        }
    }

    @Test
    public void testAssocAfterAdding_103_Items() {
        for (int i = 0; i < 103; i++) {
            v = v.conj(i);
        }
        PersistentVector<Integer> v2 = v.assoc(89, 42);
        assertEquals(new Integer(89), v.get(89));
        assertEquals(new Integer(42), v2.get(89));
    }

    @Test
    public void testPopAfterAdding_103_Items() {
        for (int i = 0; i < 103; i++) {
            v = v.conj(i);
        }
        PersistentVector<Integer> v2 = v.pop();
        for (int i = 0; i < 102; i++) {
            assertEquals(new Integer(i), v2.get(i));
        }
        assertEquals(null, v2.get(102));
        assertEquals(new Integer(102), v.get(102));
    }

    @Test
    public void testPeekAfterAdding_103_Items() {
        for (int i = 0; i < 103; i++) {
            v = v.conj(i);
        }
        assertEquals(new Integer(102), v.peek());
    }

    @Test
    public void testAdding_1025_Items() {
        for (int i = 0; i < 1025; i++) {
            v = v.conj(i);
        }
        for (int i = 0; i < 1025; i++) {
            assertEquals(new Integer(i), v.get(i));
        }
    }

}
