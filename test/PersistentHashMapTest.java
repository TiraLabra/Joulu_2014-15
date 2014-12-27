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
    public void testInsertOneItem() {
        PersistentHashMap<String, String> m2 = m.assoc("key", "value");
        assertEquals("value", m2.get("key"));
    }
    
}
