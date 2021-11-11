/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmenttwoblackhole.windows;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;

/**
 *
 * @author andreicristea
 */
public class MenuWindow extends Window {
    static class GameVariant {
        int size;
        String sizeString;

        public GameVariant(int size, String sizeString) {
            this.size = size;
            this.sizeString = sizeString;
        }
        
        
    }
    
    static GameVariant[] sizes = {
        new GameVariant(5, "5x5"),
        new GameVariant(7, "7x7"),
        new GameVariant(9, "9x9"),
    };
    
    private final ArrayList<Window> gameWindows;
    
    public MenuWindow() {
       super("Black Hole Game", WindowExitType.EXIT); 
       getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
       for (GameVariant size : sizes) {
           JButton button = new JButton();
           button.setText(size.sizeString);
           button.addActionListener(this.getActionListener(size.size));
           
           getContentPane().add(button);
       }
       this.gameWindows = new ArrayList<>();
       
       setVisible(true);
    }
    
    private ActionListener getActionListener(final int size) {
        return new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               Window window = new GameWindow(size, MenuWindow.this);
               window.setVisible(true);
               gameWindows.add(window);
           }
        };
    }
    
    public ArrayList<Window> getGameWindows() {
        return gameWindows;
    }
}
