package persistentDataStructures;

import java.util.Arrays;

/**
 *
 * @author Lauri Kinnunen
 * @param <T> item type
 */

public class PersistentVector<T> implements IPersistentVector<T> {

    private final int BRANCHING_FACTOR = 32;
    private final int SHIFT = 5;
    private final int MASK = BRANCHING_FACTOR - 1;
    private final int count;
    private final int depth;
    private final Node root;

    public PersistentVector () {
        this.root = new Node();
        this.count = 0;
        this.depth = 0;
    }

    private PersistentVector (Node root, int count, int depth) {
        this.root = root;
        this.count = count;
        this.depth = depth;
    }
    
    /**
     * returns the number of items in the vector
     * @return int
     */
    @Override
    public int count () {
        return this.count;
    }

    /**
     * returns a new PersistentVector that is the old vector minus the last value
     * @return PersistentVector
     */
    @Override
    public PersistentVector<T> pop () {
        if (this.count < 1) return this;

        Node newRoot = doAssoc(null, this.depth * SHIFT, this.root, this.count - 1);

        // check if one level can be removed
        if (this.count == (1 << (this.depth * SHIFT)) + 1) {
            return new PersistentVector<>((Node)newRoot.get(0), this.count - 1, this.depth -1);
        } else {
            return new PersistentVector<>(newRoot, this.count - 1, this.depth);
        }
    }

    /**
     * returns a new PersistentVector with the given value appended to it
     * @param element
     * @return PersistentVector
     */
    @Override
    public PersistentVector<T> conj (T element) {
        if (this.count >= 1 << (this.depth + 1) * SHIFT) {
            // full level, create new root
            Object[] rootElements = new Object[BRANCHING_FACTOR];
            rootElements[0] = this.root;
            Node newRoot = doAssoc(element, (this.depth + 1) * SHIFT, new Node(rootElements), this.count);
            return new PersistentVector<>(newRoot, this.count + 1, this.depth + 1);
        } else {
            Node newRoot = doAssoc(element, this.depth * SHIFT, this.root, this.count);
            return new PersistentVector<>(newRoot, this.count + 1, this.depth);
        }
    }

    /**
     * returns a new PersistentVector that associates the given index with the given value
     * @param ind
     * @param element
     * @return PersistentVector
     */
    @Override
    public PersistentVector<T> assoc (Integer ind, T element) {
        Node newRoot = doAssoc(element, this.depth * SHIFT, this.root, ind);
        return new PersistentVector(newRoot, this.count + 1, this.depth);
    }

    /**
     * returns the last value in vector
     * @return 
     */
    @Override
    public T peek () {
        if (count < 1) return null;

        return this.get(this.count - 1);
    }

    /**
     * returns the value associated with the given index
     * @param index
     * @return
     */
    @Override
    public T get (Integer index) {
        if (index >= count || index < 0) return null;

        return findValue(index);
    }

    /**
     * Traverse the trie to find the value at the given index
     * @param int:index
     * @return value
     */
    private T findValue (int index) {
        Node node = this.root;
        
        // for each level of the trie
        // shift and mask the 32bit index appropiately
        for (int level = this.depth * SHIFT; level > 0; level -= SHIFT) {
            int ind = (index >>> level) & MASK;
            node = (Node)node.get(ind);
        }

        return (T)node.get(index & MASK);
    }

    /**
     * recursively copy each node on the path and return the new root
     * @param newElement
     * @param int:level
     * @param Node: node
     * @param int: index
     * @return Node
     */
    private Node doAssoc(T newElement, int level, Node node, int index) {

        // get the index of the child node by shifting the integer
        // by the level number
        // mask out irrelevant bits
        int ind = (index >>> level) & MASK;
        Object[] newNodes;

        if (node != null) {
            newNodes = node.copyElements();
        } else {
            newNodes = new Object[BRANCHING_FACTOR];
        }

        // Are we at the bottom of the tree?
        if (level > 0) {
            // This is an internal node
            // recursively copy the rest of the path
            Node nextNode = (Node)newNodes[ind];
            newNodes[ind] = doAssoc(newElement, level - SHIFT, nextNode, index);
            return new Node(newNodes);
        } else {
            // This is the bottom of the tree
            // insert new element to its correct position and
            // return a new node
            newNodes[ind] = newElement;
            return new Node(newNodes);
        }

    }

    private class Node {
        private final Object[] elements;
        
        public Node () {
            this.elements = new Object[BRANCHING_FACTOR];
        }

        public Node (Object[] elements) {
            this.elements = elements;
        }

        public Object get(int ind) {
            return elements[ind];
        }

        public Object[] copyElements() {
            return Arrays.copyOf(this.elements, BRANCHING_FACTOR);
        }

    }
}
