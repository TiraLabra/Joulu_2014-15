package demo;

import persistentDataStructures.PersistentVector;

/**
 *
 * @author laurikin
 */
public class RenderPixelsCallback implements Callback{
    PixelsView view;

    public RenderPixelsCallback(PixelsView view) {
        this.view = view;
    }

    @Override
    public void call(PersistentVector<Boolean> newPixels, PersistentVector<Boolean> oldPixels) {
        view.repaint();
    }
    
}
