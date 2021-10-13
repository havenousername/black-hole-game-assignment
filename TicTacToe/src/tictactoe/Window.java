/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author andreicristea
 */
public class Window extends BaseWindow {
    private final int size;
    private final Model model;
    private final JLabel label;
    private final MainWindow mainWindow;
    
    public Window(int size, MainWindow mainWindow) {
        this.size = size;
        this.mainWindow = mainWindow;
        mainWindow.getWindowList().add(this);
        
        model = new Model(size);
        
        JPanel top = new JPanel();
        
        label = new JLabel();
//        updateLabelText()

        JButton newGameButton = new JButton();
        newGameButton.setText("New game");
        newGameButton.addActionListener(e -> newGame());
        
        top.add(label);
        top.add(newGameButton);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(size, size));
        
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                addButton(mainPanel, i, j);
            }
        }
    }
    
    
    private void addButton(JPanel panel, final int i, final int j) {
        final JButton button = new JButton();
        
        button.addActionListener(e -> {
            Player newValue = model.step(i, j);
            button.setText(newValue.name());
            
            updateLabelText();
            
            Player winner = model.findWinner();
            if (winner != Player.NOBODY) {
                showGameOverMessage(winner);
            }
        });
    }
    
    private void showGameOverMessage(Player winner) {
        JOptionPane.showMessageDialog(this, "Game is over. Winner: " + winner.name());
        newGame();
    }
    
    private void newGame() {
        Window newWindow = new Window(size, mainWindow);
        newWindow.setVisible(true);
        this.dispose();
        mainWindow.getWindowList().remove(this);
    }
    
    private void updateLabelText() {
        label.setText("Current player: " + model.getActualPlayer().name());
    }
    
    
    @Override
    protected void doUponExit() {
        this.doUponExit();
        mainWindow.getWindowList().remove(this);
    }
}