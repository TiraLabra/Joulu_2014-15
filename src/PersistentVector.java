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
    
    @Override
    public int count () {
        return this.count;
    }

    @Override
    public PersistentVector<T> pop () {
        Node newRoot = doAssoc(null, this.depth * SHIFT, this.root, this.count - 1);

        // check if one level can be removed
        if (this.count == (1 << (this.depth * SHIFT)) + 1) {
            return new PersistentVector<>((Node)newRoot.get(0), this.count - 1, this.depth -1);
        } else {
            return new PersistentVector<>(newRoot, this.count - 1, this.depth);
        }
    }

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

    @Override
    public PersistentVector<T> assoc (Integer ind, T element) {
        Node newRoot = doAssoc(element, this.depth * SHIFT, this.root, ind);
        return new PersistentVector(newRoot, this.count + 1, this.depth);
    }

    @Override
    public T peek () {
        if (count < 1) return null;

        return this.get(this.count - 1);
    }

    @Override
    public T get (Integer index) {
        if (index >= count || index < 0) return null;

        return findValue(index);
    }

    private T findValue (int index) {
        Node node = this.root;
        
        for (int level = this.depth * SHIFT; level > 0; level -= SHIFT) {
            int ind = (index >>> level) & MASK;
            node = (Node)node.get(ind);
        }

        return (T)node.get(index & MASK);
    }

    private Node doAssoc(T el, int level, Node node, int index) {
        //create a new node for each step in the path
        //recursively call doAssoc and return the root
        int ind = (index >>> level) & MASK;
        Object[] newNodes;

        if (node != null) {
            newNodes = node.copyElements();
        } else {
            newNodes = new Object[BRANCHING_FACTOR];
        }

        if (level > 0) {
            Node nextNode = (Node)newNodes[ind];
            newNodes[ind] = doAssoc(el, level - SHIFT, nextNode, index);
            return new Node(newNodes);
        } else {
            newNodes[ind] = el;
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
