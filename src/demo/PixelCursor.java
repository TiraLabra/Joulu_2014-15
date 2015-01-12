/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import persistentDataStructures.PersistentVector;

/**
 *
 * @author laurikin
 */
public class PixelCursor {

    private PersistentVector<Boolean> pixels;

    public PixelCursor (PersistentVector<Boolean> pixels) {
        this.pixels = pixels;
    }

    public PersistentVector<Boolean> current () {
        return pixels;
    }

    public void update (int index) {
        this.pixels = this.pixels.assoc(index, !this.pixels.get(index));
    }
    
}
