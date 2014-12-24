
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
    private int depth = 0;
    private Node<T> root;

    public PersistentVector () {
        this.root = new LeafNode<T>();
    }
    
    public int count() {
        return this.count;
    }

    @Override
    public PersistentVector pop() {
        PersistentVector p = new PersistentVector();
        p.root.setAllValues(this.root.copyValues());
        p.root.setValue(this.count - 1, null);
        p.count = this.count - 1;
        return p;
    }

    @Override
    public PersistentVector conj(T element) {
        PersistentVector p = new PersistentVector();
        p.root.setAllValues(this.root.copyValues());
        if (this.count < BRANCHING_FACTOR) {
            p.root.setValue(this.count, element);
        } else {
            p.createNewLevel();
            p.root.getNode(1).setValue(0, element);
        }
        p.count = this.count + 1;
        return p;
    }

    @Override
    public PersistentVector assoc(int ind, T element) {
        PersistentVector p = new PersistentVector();
        p.root.setValue(ind, element);
        return p;
    }

    @Override
    public T peek() {
        if (count < 1) return null;

        return this.root.getValue(this.count - 1);
    }

    @Override
    public T get(int index) {
        return findValue(index);
    }

    private void createNewLevel() {
        Node<T> newRoot = new InternalNode<T>();
        newRoot.setNode(0, this.root);
        newRoot.setNode(1, new LeafNode<T>());
        this.root = newRoot;
        this.depth++;
    }

    private T findValue(int index) {
        Node<T> node = this.root;
        int mask = BRANCHING_FACTOR - 1;
        
        for (int level = this.depth * 5; level > 0; level -= 5) {
            int ind = (index >>> level) & mask;
            node = node.getNode(ind);
        }

        return node.getValue(index & mask);
    }

   


    private abstract class Node<T> {
        abstract T getValue(int ind);
        abstract Node<T> getNode(int ind);
        abstract void setValue(int ind, T element);
        abstract void setNode(int ind, Node node);
        abstract T[] copyValues();
        abstract void setAllValues(T[] elements);
    }

    private class LeafNode<T> extends Node {
        private T[] elements;
        
        public LeafNode () {
            this.elements = (T[])(new Object[BRANCHING_FACTOR]);
        }

        @Override
        public Node<T> getNode(int ind) {
            return null;
        }

        @Override
        public T getValue(int ind) {
            return elements[ind];
        }

        @Override
        public void setValue(int ind, Object element) {
            this.elements[ind] = (T) element;
        }

        @Override
        public T[] copyValues() {
            return Arrays.copyOf(this.elements, BRANCHING_FACTOR);
        }

        @Override
        void setAllValues(Object[] elements) {
            this.elements = (T[]) elements;
        }

        @Override
        void setNode(int ind, Node node) {
        }

    }

    private class InternalNode<T> extends Node {
        private final Node[] elements;

        public InternalNode () {
            this.elements = new Node[32];//(new Object[BRANCHING_FACTOR]);
        }

        @Override
        public Node<T> getNode(int ind) {
            return elements[ind];
        }

        @Override
        public T[] getValue(int ind) {
            return null;
        }

        @Override
        void setValue(int ind, Object element) {
        }

        @Override
        T[] copyValues() {
            return (T[])(new Object[BRANCHING_FACTOR]);
        }

        @Override
        void setAllValues(Object[] elements) {
        }

        @Override
        void setNode(int ind, Node node) {
            this.elements[ind] = node;
        }

    }

}
