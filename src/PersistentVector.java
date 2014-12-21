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

    private int count = 0;
    private Node root;

    public int count() {
        return this.count;
    }

    @Override
    public PersistentVector pop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PersistentVector conj(T element) {
        PersistentVector p = new PersistentVector();
        p.count = 1;
        return p;
    }

    @Override
    public PersistentVector assoc(int ind, T element) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T peek() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public T get(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class Node {
        private Node[] nodes;
    }
    
}
