/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author laurikin
 */
public interface IPersistentCollection<T> {
    
    public IPersistentCollection<T> assoc(int ind, T element); 

    public int count();
}
