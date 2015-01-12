/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package demo;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import persistentDataStructures.PersistentVector;

/**
 *
 * @author laurikin
 */
public class MainFrame extends JFrame {
    
    public MainFrame (final PixelsView pixelsView) {
        super("demo");
        
        setLayout(new BorderLayout());
        
        final PixelsView paintArea = pixelsView;
        JButton button = new JButton("click me");
        
        Container c = getContentPane();
        
        c.add(paintArea, BorderLayout.CENTER);
        c.add(button, BorderLayout.EAST);
        
        // Add behaviour 
    }
    
}
