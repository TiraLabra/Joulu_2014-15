package demo;

import persistentDataStructures.PersistentVector;

/**
 *
 * @author laurikin
 */
public interface Callback {
    public void call(PersistentVector<Boolean> newPixels, PersistentVector<Boolean> oldPixels);
}
