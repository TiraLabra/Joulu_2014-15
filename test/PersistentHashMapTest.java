/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author laurikin
 */
public class PersistentHashMapTest {
    private PersistentHashMap<String, String> m = new PersistentHashMap<>();
    
    public PersistentHashMapTest() {
    }
    
    @Before
    public void setUp() {
    }

    /**
     * Test of get method, of class PersistentHashMap.
     */
    @Test
    public void testCountOnEmptyString() {
        assertEquals(0, m.count());
    }

    @Test
    public void testGetOnEmptyString() {
        assertEquals(null, m.get("someKey"));
    }

    @Test
    public void testInsertOneItem() {
        PersistentHashMap<String, String> m2 = m.assoc("key", "value");
        assertEquals("value", m2.get("key"));
    }
    
    @Test
    public void testInsertThreetems() {
        PersistentHashMap<String, String> m2 = m.assoc("key", "value")
            .assoc("key2", "value2")
            .assoc("key3", "value3");

        assertEquals("value", m2.get("key"));
        assertEquals("value2", m2.get("key2"));
        assertEquals("value3", m2.get("key3"));
    }

    @Test
    public void testCountAfterInsertingThreetems() {
        m = m.assoc("key", "value")
            .assoc("key2", "value2")
            .assoc("key3", "value3");
        
        assertEquals(3, m.count());
    }

    @Test
    public void testUpdateAfterInsertingThreeItems() {
        m = m.assoc("key", "value")
            .assoc("key2", "value2")
            .assoc("key3", "value3");
        PersistentHashMap<String, String> m2 = m.assoc("key2", "newValue");
        
        assertEquals("value2", m.get("key2"));
        assertEquals("newValue", m2.get("key2"));
        assertEquals(3, m2.count());
    }

    @Test
    public void testCountAferInsertingOneItem() {
        PersistentHashMap<String, String> m2 = m.assoc("key", "value");
        assertEquals(1, m2.count());
    }

    @Test
    public void testInsertingSameKeyTwice() {
        PersistentHashMap<String, String> m2 = m.assoc("key", "value");
        PersistentHashMap<String, String> m3 = m2.assoc("key", "newValue");
        assertEquals("value", m2.get("key"));
        assertEquals("newValue", m3.get("key"));
    }

    @Test
    public void testCountAfterInsertingSameKeyTwice() {
        m = m.assoc("key", "value");
        m = m.assoc("key", "newValue");
        assertEquals(1, m.count());
    }

    @Test
    public void insert1000Integers() {
        PersistentHashMap<Integer, Integer> phm = new PersistentHashMap<>();
        ArrayList<Integer> keys = new ArrayList<>(1000);
        int[] values = new int[1000];

        for (int i = 0; i < 1000; i++) {
            keys.add(i);
            values[i] = i;
        }
        Collections.shuffle(keys);

        for (int i = 0; i < 1000; i++) {
            phm = phm.assoc(keys.get(i), values[i]);
        }

        assertEquals(1000, phm.count());

        for (int i = 0; i < 1000; i++) {
            assertEquals(new Integer(values[i]), phm.get(keys.get(i)));
        }

    }

    @Test
    public void insert1000000RandomIntegers() {
        PersistentHashMap<Integer, Integer> phm = new PersistentHashMap<>();
        ArrayList<Integer> keys = new ArrayList<>(1000000);
        int[] values = new int[1000000];

        for (int i = 0; i < 1000000; i++) {
            keys.add(i);
            values[i] = i;
        }
        Collections.shuffle(keys);

        for (int i = 0; i < 1000000; i++) {
            phm = phm.assoc(keys.get(i), values[i]);
        }

        assertEquals(1000000, phm.count());

        for (int i = 0; i < 1000000; i++) {
            assertEquals(new Integer(values[i]), phm.get(keys.get(i)));
        }
    }
}
