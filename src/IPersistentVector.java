/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author laurikin
 * @param <T>
 */
public interface IPersistentVector<T> {

    public PersistentVector<T> pop(); 

    public PersistentVector<T> conj(T element); 

    public T peek(); 
    
}
