package persistentDataStructures;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author laurikin
 */
public interface IPersistentCollection<K, V> {
    
    public IPersistentCollection<K, V> assoc(K key, V element); 

    public V get(K key);

    public int count();
}
