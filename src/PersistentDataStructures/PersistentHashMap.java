package persistentDataStructures;


import java.util.Arrays;

/**
 *
 * @author Lauri Kinnunen
 * @param <K> key
 * @param <V> value
 */

public class PersistentHashMap<K, V> implements IPersistentHashMap<K, V> {

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

    /**
     * returns the number of items stored in this PersistentHashMap
     * @return int
     */
    @Override
    public int count() {
        return this.count;
    }

    /**
     *
     * @param key
     * @return PersistentHashMap
     */
    @Override
    public V get(K key) {
        LeafNode<K,V> l = this.root.find(key.hashCode(), key, 0);
        return l == null? null : l.value;
    }

    /**
     * returns a new PersistentHashMap that associates the given index with the given value
     * @param key
     * @param value
     * @return PersistentHashMap
     */
    @Override
    public PersistentHashMap<K, V> assoc(K key, V value) {
        Update u = this.root.assoc(key.hashCode(), 0, key, value);
        return new PersistentHashMap<>(u.root, this.count + u.countDelta);
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    public PersistentHashMap dissoc(K key) {
        Update u = this.root.dissoc(key.hashCode(), 0, key);
        if (u.root == null) {
            return new PersistentHashMap<>();
        } else {
            return new PersistentHashMap<>(u.root, this.count + u.countDelta);
        }
    }

    static interface Node<K,V> {
        public Update assoc(int hash, int level, K key, V value);
        public Update dissoc(int hash, int level, K key);
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

        @Override
        public Update dissoc(int hash, int level, K key) {
            return new Update(this, 0);
        }

    }

    private static class InternalNode<K,V> implements Node<K,V> {
        private final Node<K,V>[] nodes;
        private final int bitmap;

        private InternalNode (Node[] nodes, int bitmap) {
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
	        return ((hash >>> shift) & 31);
        }

        private int index(int bit){
            return Integer.bitCount(this.bitmap & (bit - 1));
        }

        private int index(int bit, int bitmap){
            return Integer.bitCount(bitmap & (bit - 1));
        }

        private int bitpos(int hash, int shift){
            return 1 << mask(hash, shift);
        }

        private InternalNode<K,V> copyWith(Node<K,V> newLeaf, int pos) {
            Node[] newNodes = Arrays.copyOf(this.nodes, this.nodes.length + 1);
            int newBitmap = this.bitmap | pos;
            int index = index(pos, newBitmap);
            System.arraycopy(this.nodes, 0, newNodes, 0, index);
            System.arraycopy(this.nodes, index, newNodes, index + 1, this.nodes.length - index);
            newNodes[index] = newLeaf;
            return new InternalNode<>(newNodes, newBitmap);
        }

        private InternalNode<K,V> copyWithout(int pos) {
            Node[] newNodes = new Node[this.nodes.length - 1];
            int newBitmap = this.bitmap & (~pos);
            int index = index(pos, this.bitmap);
            System.arraycopy(this.nodes, 0, newNodes, 0, index);
            System.arraycopy(this.nodes, index + 1, newNodes, index, this.nodes.length - index - 1);
            return new InternalNode<>(newNodes, newBitmap);
        }

        @Override
        public Update assoc(int hash, int level, K key, V value) {
            int pos = bitpos(hash, level);
            if ((this.bitmap & pos) != 0) {
                // node exists
                Update u = this.nodes[index(pos)].assoc(hash, level + 5, key, value);
                Node[] newNodes = Arrays.copyOf(this.nodes, this.nodes.length);
                newNodes[index(pos)] = u.root;
                InternalNode<K,V> newNode = new InternalNode<> (newNodes, this.bitmap);
                u.root = newNode;
                return u;
            } else {
                // node does not exist, create new
                LeafNode<K,V> leaf = new LeafNode<>(key, value, null);
                InternalNode<K,V> newNode = this.copyWith(leaf, pos);
                return new Update(newNode, 1);
            }
        } 

        @Override
        public Update dissoc(int hash, int level, K key) {
            int pos = bitpos(hash, level);
            if ((this.bitmap & pos) != 0) {
                // node exists
                Update u = this.nodes[index(pos)].dissoc(hash, level + 5, key);
                if (u.root == null) {
                    if (this.nodes.length > 0) {
                        InternalNode<K,V> newNode = this.copyWithout(pos);
                        u.root = newNode;
                        return u;
                    } else {
                        return u;
                    }
                } else {
                    Node[] newNodes = Arrays.copyOf(this.nodes, this.nodes.length);
                    newNodes[index(pos)] = u.root;
                    InternalNode<K,V> newNode = new InternalNode<> (newNodes, this.bitmap);
                    u.root = newNode;
                    return u;
                }
            } else {
                // node does not exist
                return new Update(this, 0);
            }
        }

        @Override
        public LeafNode<K,V> find(int hash, K key, int level) {
            int pos = bitpos(hash, level);
            if((this.bitmap & pos) != 0) {
                return this.nodes[index(pos)].find(hash, key, level + 5);
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

        private Update remove (LeafNode first, K key, V value, int rank) {
            if (this.key.equals(key)) {
                if (rank == 0) {
                    return new Update(this.next, -1);
                } else {
                    return new Update(copy(first, rank, this.next), -1);
                }
            } else if (this.next == null) {
                return new Update(this, 0);
            } else {
                return this.next.remove(first, key, value, rank + 1);
            }
        }

        @Override
        public Update assoc(int hash, int level, K key, V value) {
            if (hash == this.key.hashCode()) {
                return add(this, key, value, 0);
            } else {
                InternalNode<K,V> iNode = new InternalNode<>(new Node[0], 0);
                Update u = iNode.assoc(this.key.hashCode(), level, this.key, this.value);
                return new Update(u.root.assoc(hash, level, key, value).root, 1);
            }
        }

        @Override
        public Update dissoc(int hash, int level, K key) {
            if (hash == this.key.hashCode()) {
                return remove(this, key, value, 0);
            } else {
                return new Update(this, 0);
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

    }

    private static class Update {
        private Node root;
        private final int countDelta;

        private Update(Node root, int delta) {
            this.root = root;
            this.countDelta = delta;
        }
    }
}
