/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author laurikin
 */
public interface IPersistentVector<T> {

    public PersistentVector pop(); 

    public PersistentVector conj(T element); 

    public PersistentVector assoc(int ind, T element); 

    public T peek(); 
    
    public T get(int index);
}
