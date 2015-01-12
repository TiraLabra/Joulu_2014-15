package demo;

import persistentDataStructures.PersistentVector;

/**
 *
 * @author laurikin
 */
public class PixelCursor {

    PersistentVector<Callback> callbacks;
    PersistentVector<Callback> updateCallbacks;

    private PersistentVector<Boolean> pixels;


    public PixelCursor (PersistentVector<Boolean> pixels) {
        this.pixels = pixels;
        this.callbacks = new PersistentVector<>();
        this.updateCallbacks = new PersistentVector<>();
    }

    public PersistentVector<Boolean> current () {
        return pixels;
    }

    public void update (int index) {
        PersistentVector<Boolean> old = this.pixels;
        this.pixels = this.pixels.assoc(index, !this.pixels.get(index));
        runUpdateCallbacks(old);
    }

    public void set (PersistentVector<Boolean> newPixels) {
        PersistentVector<Boolean> old = this.pixels;
        this.pixels = newPixels;
        runCallbacks(old);
    }

    public void addListener(Callback callback) {
        callbacks = callbacks.conj(callback);
    }

    public void addUpdateListener(Callback callback) {
        updateCallbacks = updateCallbacks.conj(callback);
    }

    private void runUpdateCallbacks(PersistentVector<Boolean> old) {
        for (int i = 0; i < callbacks.count(); i++) {
            updateCallbacks.get(i).call(this.pixels, old);
        }
        runCallbacks(old);
    }
    private void runCallbacks(PersistentVector<Boolean> old) {
        for (int i = 0; i < callbacks.count(); i++) {
            callbacks.get(i).call(this.pixels, old);
        }
    }
    
}
