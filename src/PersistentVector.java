
import java.util.Arrays;

/**
 *
 * @author Lauri Kinnunen
 * @param <T> item type
 */

public class PersistentVector<T> implements IPersistentVector<T> {

    private final int BRANCHING_FACTOR = 32;
    private final int SHIFT = 5;
    private int count = 0;
    private int depth = 0;
    private Node root;

    public PersistentVector () {
        this.root = new Node();
    }
    
    @Override
    public int count () {
        return this.count;
    }

    @Override
    public PersistentVector<T> pop () {
        PersistentVector p = new PersistentVector();
        p.root = doAssoc(null, this.depth * SHIFT, this.root, this.count - 1);

        // check if one level can be removed
        if (this.count == (1 << (this.depth * SHIFT)) + 1) {
            p.root = (Node)p.root.get(0);
            p.depth = this.depth - 1;
        } else {
            p.depth = this.depth;
        }

        p.count = this.count - 1;
        return p;
    }

    @Override
    public PersistentVector<T> conj (T element) {
        PersistentVector p = new PersistentVector();

        // full level
        if (this.count >= 1 << (this.depth + 1) * SHIFT) {
            Object[] rootElements = new Object[BRANCHING_FACTOR];
            rootElements[0] = this.root;
            p.depth = this.depth + 1;
            p.count = this.count;
            p.root = doAssoc(element, p.depth * SHIFT, new Node(rootElements), p.count);
        } else {
            p.root = doAssoc(element, this.depth * SHIFT, this.root, this.count);
            p.depth = this.depth;
        }

        p.count = this.count + 1;

        return p;
    }

    @Override
    public PersistentVector<T> assoc (Integer ind, T element) {
        PersistentVector p = new PersistentVector();
        p.root = doAssoc(element, this.depth * SHIFT, this.root, ind);
        p.count = this.count + 1;
        p.depth = this.depth;
        return p;
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
        int mask = BRANCHING_FACTOR - 1;
        
        for (int level = this.depth * SHIFT; level > 0; level -= SHIFT) {
            int ind = (index >>> level) & mask;
            node = (Node)node.get(ind);
        }

        return (T)node.get(index & mask);
    }

    private Node doAssoc(T el, int level, Node node, int index) {
        //create a new node for each step in the path
        //recursively call and return the root
        int mask = BRANCHING_FACTOR - 1;
        int ind = (index >>> level) & mask;
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
        private Object[] elements;
        
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
