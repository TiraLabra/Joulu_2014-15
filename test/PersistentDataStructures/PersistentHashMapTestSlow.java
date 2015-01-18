package PersistentDataStructures;


import persistentDataStructures.PersistentHashMap;
import java.util.ArrayList;
import java.util.Collections;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author laurikin
 */
public class PersistentHashMapTestSlow {
   
    private static ArrayList<Integer> keys;
    private static int[] values;
    private PersistentHashMap<Integer, Integer> phm;

    @BeforeClass
    public static void beforeClass() {
        keys = new ArrayList<>();
        values = new int[1000000];
        for (int i = 0; i < 1000000; i++) {
            // create values that are distributed over a large range
            keys.add(i * 2);
            values[i] = i * 2;
        }
        Collections.shuffle(keys);
    }

    @Before
    public void beforeEach() {
        phm = new PersistentHashMap<>();
    }
    
    @Test(timeout=1000)
    public void insertManyIntegers() {
        int rep = 200000;

        for (int i = 0; i < rep; i++) {
            phm = phm.assoc(keys.get(i), values[i]);
        }

        assertEquals(rep, phm.count());
    }

    @Test(timeout=1000)
    public void insertAndRemoveManyIntegers() {
        int rep = 100000;

        for (int i = 0; i < rep; i++) {
            phm = phm.assoc(keys.get(i), values[i]);
        }
        
        for (int i = 0; i < rep; i++) {
            phm = phm.dissoc(keys.get(i));
        }

        assertEquals(0, phm.count());
    }

    @Test(timeout=1000)
    public void getManyIntegers() {
        int rep = 200000;

        for (int i = 0; i < rep; i++) {
            phm = phm.assoc(keys.get(i), values[i]);
        }

        for (int i = 0; i < rep; i++) {
            phm.get(keys.get(i));
        }

        assertEquals(rep, phm.count());
    }
}
