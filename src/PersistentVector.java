
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author laurikin
 */
public class PersistentVector<T> implements IPersistentVector<T> {

    private final int BRANCHING_FACTOR = 32;
    private int count = 0;
    private Node root;

    public PersistentVector () {
        this.root = new Node();
    }
    
    public int count() {
        return this.count;
    }

    @Override
    public PersistentVector pop() {
        PersistentVector p = new PersistentVector();
        p.root.elements = copyRoot();
        p.root.elements[this.count - 1] = null;
        p.count = this.count - 1;
        return p;
    }

    @Override
    public PersistentVector conj(T element) {
        PersistentVector p = new PersistentVector();
        p.root.elements = copyRoot();
        p.root.elements[this.count] = element;
        p.count = this.count + 1;
        return p;
    }

    @Override
    public PersistentVector assoc(int ind, T element) {
        PersistentVector p = new PersistentVector();
        p.root.elements[ind] = element;
        return p;
    }

    @Override
    public T peek() {
        if (count < 1) return null;

        return this.root.elements[this.count - 1];
    }

    @Override
    public T get(int index) {
        return this.root.elements[index];
    }

    private class Node {
        private T[] elements;
        
        public Node () {
            this.elements = (T[])(new Object[32]);
        }
    }

    private T[] copyRoot() { 
        return Arrays.copyOf(this.root.elements, BRANCHING_FACTOR);
    }
    
}
