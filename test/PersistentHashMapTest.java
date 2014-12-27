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
}
