package demo;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author laurikin
 */
public class MainFrame extends JFrame {
    History history;
    
    public MainFrame (final History history, PixelsView pixelsView) {
        super("demo");

        this.history = history;
        
        setLayout(new BorderLayout());
        
        final PixelsView paintArea = pixelsView;
        JButton undoButton = new JButton("undo");
        JButton redoButton = new JButton("redo");
        
        Container c = getContentPane();
        
        c.add(paintArea, BorderLayout.CENTER);
        c.add(undoButton, BorderLayout.WEST);
        c.add(redoButton, BorderLayout.EAST);
        
        undoButton.addActionListener(new ActionListener() {    
            @Override
            public void actionPerformed(ActionEvent e) {
                history.undo();
            }
        });

        redoButton.addActionListener(new ActionListener() {    
            @Override
            public void actionPerformed(ActionEvent e) {
                history.redo();
            }
        });
    }
    
}
