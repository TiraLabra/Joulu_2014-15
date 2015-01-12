/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package demo;

import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import persistentDataStructures.PersistentVector;

/**
 *
 * @author laurikin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        PersistentVector<Boolean> pixels = new PersistentVector<>();
        for (int i = 0; i < 64; i++) {
            if (i % 2 == 0) {
                pixels = pixels.conj(false);
            } else {
                pixels = pixels.conj(true);
            }
        }

        PixelCursor cursor = new PixelCursor(pixels);

        final PixelsView pixelsView = new PixelsView(cursor);
        

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new MainFrame(pixelsView);
                frame.setSize(500, 400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
