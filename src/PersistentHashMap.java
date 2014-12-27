
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

    private final Node root;
    private final int count;

    public PersistentHashMap() {
        this.root = new EmptyNode();
        this.count = 0;
    }

    private PersistentHashMap(Node root, int count) {
        this.root = root;
        this.count = count;
    }

    @Override
    public V get(K key) {
        LeafNode l = this.root.find(key.hashCode(), key);
        return l == null? null : l.value;
    }

    @Override
    public PersistentHashMap<K, V> assoc(K key, V value) {
        Update u = this.root.assoc(key.hashCode(), 25, key, value);
        return new PersistentHashMap<>(u.root, this.count + u.countDelta);
    }

    @Override
    public int count() {
        return this.count;
    }
    
    private abstract class Node {
        abstract Update assoc(int level, int hash, K key, V value);
        abstract LeafNode find(int hash, K key);
    }

    private class EmptyNode extends Node {

        @Override
        Update assoc(int hash, int level, K key, V value) {
            return new Update(new LeafNode(key, value, null), 1);
        }

        @Override
        LeafNode find(int hash, K key) {
            return null;
        }

    }

    private class InternalNode extends Node {
        private Node[] nodes;

        @Override
        Update assoc(int level, int hash,K key, V value) {
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

        private LeafNode copy(LeafNode node, int numOfCopies, LeafNode last) {
            if (node == null) {
                return null;
            } else if (numOfCopies == 1) {
                return new LeafNode(node.key, node.value, last);
            } else {
                return new LeafNode(node.key, node.value, copy(node.next, numOfCopies - 1, last));
            }
        }

        private Update update(K key, V value, LeafNode first, int numOfCopies, LeafNode next) {
            return new Update(new LeafNode(key, value, copy(first, numOfCopies, next)), 0);
        }

        private Update add (LeafNode first, K key, V value, int rank) {
            if (this.key.equals(key)) {
                return update(key, value, first, rank, this.next);
            } else if (this.next == null) {
                return new Update(new LeafNode(key, value, first), 1);
            } else {
                return this.next.add(first, key, value, rank + 1);
            }
        }

        @Override
        Update assoc(int hash, int level, K key, V value) {
            return add(this, key, value, 0);
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

    private class Update {
        private final Node root;
        private final int countDelta;

        private Update(Node root, int delta) {
            this.root = root;
            this.countDelta = delta;
        }
    }
}
