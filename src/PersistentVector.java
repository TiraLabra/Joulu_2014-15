
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

        // full level
        if (this.count >= 1 << (this.depth + 1) * 5) {
            p.root = new InternalNode<>();
            p.root.setNode(0, this.root);
            p.depth = this.depth + 1;
            p.count = this.count;
            p.root = doConj(element, p.depth * 5, p.root);
        } else {
            p.root = doConj(element, this.depth * 5, this.root);
            p.depth = this.depth;
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

    private T findValue(int index) {
        Node<T> node = this.root;
        int mask = BRANCHING_FACTOR - 1;
        
        for (int level = this.depth * 5; level > 0; level -= 5) {
            int ind = (index >>> level) & mask;
            node = node.getNode(ind);
        }

        return node.getValue(index & mask);
    }

    private Node doConj(T el, int level, Node<T> node) {
        //create a new node for each step in the path
        //recursively call add return the root
        int mask = BRANCHING_FACTOR - 1;
        int index = this.count;

        if (level > 0) {
            Node<T> newNode = new InternalNode<T>();
            if (node != null) {
                newNode.setAllNodes(node.copyNodes());
            }
            int ind = (index >>> level) & mask;
            Node<T> nextNode = newNode.getNode(ind);
            newNode.setNode(ind, doConj(el, level - 5, nextNode));
            return newNode;
        } else {
            Node<T> newNode = new LeafNode<T>();
            if (node != null) {
                newNode.setAllValues(node.copyValues());
            }
            newNode.setValue(index & mask, el);
            return newNode;
        }

    }

   


    private abstract class Node<T> {
        abstract T getValue(int ind);
        abstract Node<T> getNode(int ind);
        abstract void setValue(int ind, T element);
        abstract void setNode(int ind, Node node);
        abstract T[] copyValues();
        abstract Node[] copyNodes();
        abstract void setAllValues(T[] elements);
        abstract void setAllNodes(Node[] elements);
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

        @Override
        Node[] copyNodes() { return null; }

        @Override
        void setAllNodes(Node[] elements) {
        }

    }

    private class InternalNode<T> extends Node {
        private Node[] elements;

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

        @Override
        Node[] copyNodes() {
            return Arrays.copyOf(this.elements, BRANCHING_FACTOR);
        }

        @Override
        void setAllNodes(Node[] elements) {
            this.elements = (Node[]) elements;
        }


    }

}
