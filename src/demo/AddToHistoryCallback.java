package demo;

import persistentDataStructures.PersistentVector;

/**
 *
 * @author laurikin
 */
public class AddToHistoryCallback implements Callback {

    private final History history;

    public AddToHistoryCallback (History history) {
        this.history = history;
    }

    @Override
    public void call(PersistentVector<Boolean> newPixels, PersistentVector<Boolean> oldPixels) {
        history.add(newPixels);
    }
    
}
