package demo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author Lauri Kinnunen
 */
public class PixelsView extends JPanel {
    private final PixelCursor cursor;
    private final int PIXEL_SIZE = 30;
    private final ClickListener mouseListener;

    public PixelsView(PixelCursor cursor) {
        this.mouseListener = new ClickListener();
        addMouseListener(this.mouseListener);
        this.cursor = cursor;
        
    }

    @Override
    public void paintComponent(Graphics g) {
        setBackground(Color.GRAY);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 240, 240);
        g.setColor(Color.BLACK);

        for (int i = 0; i < 64; i++) {
            if (cursor.current().get(i) == true) {
                g.fillRect((i * PIXEL_SIZE) % 240, i / 8 * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE);
            }
        }
    }

    private class ClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            // calculate from mouse coordinantes which pixel to update
            if (e.getX() < 240 && e.getY() < HEIGHT + 240) {
                int index = (e.getX() / 30) + (8 * (e.getY() / 30));
                cursor.update(index);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
    
}
