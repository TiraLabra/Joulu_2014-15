package demo;

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
            pixels = pixels.conj(false);
        }

        PixelCursor cursor = new PixelCursor(pixels);
        final History history = new History(10, cursor);

        final PixelsView pixelsView = new PixelsView(cursor);

        cursor.addListener(new RenderPixelsCallback(pixelsView));
        

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new MainFrame(history, pixelsView);
                frame.setSize(500, 400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
