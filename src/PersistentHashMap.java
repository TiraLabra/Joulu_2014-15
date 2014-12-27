
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author laurikin
 * @param <K> key
 * @param <V> value
 */
public class PersistentHashMap<K, V> implements IPersistentCollection<K, V> {

    private Node root;

    public PersistentHashMap() {
        this.root = new EmptyNode();
    }

    @Override
    public V get(K key) {
        LeafNode l = this.root.find(key.hashCode(), key);
        return l == null? null : l.value;
    }

    @Override
    public PersistentHashMap<K, V> assoc(K key, V value) {
        PersistentHashMap<K,V> p = new PersistentHashMap<>();
        p.root = this.root.assoc(key.hashCode(), 25, key, value);
        return p;
    }

    @Override
    public int count() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private abstract class Node {
        abstract Node assoc(int level, int hash, K key, V value);
        abstract LeafNode find(int hash, K key);
    }

    private class EmptyNode extends Node {

        @Override
        Node assoc(int hash, int level, K key, V value) {
            return new LeafNode(key, value, null);
        }

        @Override
        LeafNode find(int hash, K key) {
            return null;
        }

    }

    private class InternalNode extends Node {
        private Node[] nodes;

        @Override
        Node assoc(int level, int hash,K key, V value) {
            return null;
        }

        @Override
        LeafNode find(int hash, K key) {
            return null;
        }
    }

    private class LeafNode extends Node {
        private final K key;
        private final V value;
        private final LeafNode next;

        private LeafNode () {
            this.value = null;
            this.key = null;
            this.next = null;
        }

        private LeafNode (K key, V value, LeafNode next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        private LeafNode add (LeafNode first, K key, V value) {
            if (this.next == null) {
                return new LeafNode(key, value, first);
            } else {
                return this.next.add(first, key, value);
            }
        }

        @Override
        Node assoc(int hash, int level, K key, V value) {
            return add(this, key, value);
        }

        @Override
        LeafNode find(int hash, K key) {
            if (key.equals(this.key)) {
                return this;
            } else if (this.next == null) {
                return null;
            } else {
                return this.next.find(hash, key);
            }
        }

        V getValue(V value) {
            return this.value;
        }
    }
}
