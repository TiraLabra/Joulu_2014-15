
import java.util.ArrayList;
import java.util.Collections;
import static org.junit.Assert.assertEquals;
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
public class PersistentHashMapTestSLow {
    
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

    @Test
    public void insertAndRemove1000000RandomIntegers() {
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
        
        for (int i = 0; i < 1000000; i++) {
            phm = phm.dissoc(keys.get(i));
        }

        assertEquals(0, phm.count());
    }
}
