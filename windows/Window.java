/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmenttwoblackhole.windows;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author andreicristea
 */
public class Window extends JFrame {
    
    public Window(String title, int windowExit) {
        this.setTitle(title);
        this.init(windowExit);
    }
    
    public Window() {
        this.setTitle("Black Hole Game");
        this.init(WindowExitType.CONFIRM);
    }
    
    private void init(int windowExit) {
        this.setSize(400, 450);
        if (windowExit == WindowExitType.CONFIRM) {
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    showExitConfirmation();
                }
            });
        } else {
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        } 
    }
    
    private void showExitConfirmation() {
        int option = JOptionPane.showConfirmDialog(this, 
                "Your game is not finished. Are you sure that you want to exit?", 
                "Are you sure", 
                JOptionPane.YES_NO_OPTION
        );
        
        if (option == JOptionPane.YES_OPTION) {
            this.doUponExit();
        }
    }
    
    protected void doUponExit() {
        this.dispose();
    }
}
