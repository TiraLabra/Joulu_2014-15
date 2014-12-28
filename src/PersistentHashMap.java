
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
        LeafNode<K,V> l = this.root.find(key.hashCode(), key, 0);
        return l == null? null : l.value;
    }

    @Override
    public PersistentHashMap<K, V> assoc(K key, V value) {
        Update u = this.root.assoc(key.hashCode(), 0, key, value);
        return new PersistentHashMap<>(u.root, this.count + u.countDelta);
    }

    @Override
    public int count() {
        return this.count;
    }
    
    static interface Node<K,V> {
        public Update assoc(int level, int hash, K key, V value);
        public LeafNode find(int hash, K key, int level);
    }

    private static class EmptyNode<K,V> implements Node<K,V> {

        @Override
        public Update assoc(int hash, int level, K key, V value) {
            return new Update(new LeafNode<>(key, value, null), 1);
        }

        @Override
        public LeafNode<K,V> find(int hash, K key, int level) {
            return null;
        }

    }

    private static class InternalNode<K,V> implements Node<K,V> {
        private Node<K,V>[] nodes;
        private final int bitmap;

        private InternalNode (int hash, Node[] nodes, int bitmap) {
            this.nodes = nodes;
            this.bitmap = bitmap;
        } 

        private InternalNode (Node<K,V> node, int hash, int level) {
            this.nodes = new Node[1];
            int pos = bitpos(hash, level);
            this.bitmap = pos;
            this.nodes[index(pos, bitmap)] = node;
        } 

        private int mask(int hash, int shift){
	        return (hash >>> shift) & 0x01f;
        }

        private int index(int bit, int bitmap){
            return Integer.bitCount(bitmap & (bit - 1));
        }

        private int bitpos(int hash, int shift){
            return 1 << mask(hash, shift);
        }

        @Override
        public Update assoc(int level, int hash, K key, V value) {
            return null;
        }

        @Override
        public LeafNode<K,V> find(int hash, K key, int level) {
            int bit = bitpos(hash, level);
            if((bitmap & bit) != 0) {
                return nodes[index(bit, this.bitmap)].find(hash, key, level + 5);
            } else {
                return null;
            }
        }
    }

    private static class LeafNode<K,V> implements Node<K,V> {
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

        private LeafNode<K,V> copy(LeafNode<K,V> node, int numOfCopies, LeafNode<K,V> last) {
            if (node == null) {
                return null;
            } else if (numOfCopies == 1) {
                return new LeafNode<>(node.key, node.value, last);
            } else {
                return new LeafNode<>(node.key, node.value, copy(node.next, numOfCopies - 1, last));
            }
        }

        private Update update(K key, V value, LeafNode<K,V> first, int numOfCopies, LeafNode<K,V> next) {
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
        public Update assoc(int hash, int level, K key, V value) {
            if (hash == this.key.hashCode()) {
                return add(this, key, value, 0);
            } else {
                Node<K,V> iNode = new InternalNode<>(new LeafNode<>(key, value, null), hash, level);
                return new Update(iNode, 1);
            }
        }

        @Override
        public LeafNode<K,V> find(int hash, K key, int level) {
            if (key.equals(this.key)) {
                return this;
            } else if (this.next == null) {
                return null;
            } else {
                return this.next.find(hash, key, level);
            }
        }

        V getValue(V value) {
            return this.value;
        }
    }

    private static class Update {
        private final Node root;
        private final int countDelta;

        private Update(Node root, int delta) {
            this.root = root;
            this.countDelta = delta;
        }
    }
}
