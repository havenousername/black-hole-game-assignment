/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmenttwoblackhole.windows;

import assignmenttwoblackhole.game.Game;
import assignmenttwoblackhole.game.GameField;
import assignmenttwoblackhole.game.Player;
import assignmenttwoblackhole.game.GameMovableField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author andreicristea
 */
public class GameWindow extends Window {
    private final int size;
    private final Game game;
    private final JLabel gameMessage;
    private final MenuWindow menuWindow;
    private ArrayList<ArrayList<JButton>> gameButtons;
    private final JPanel panel;
    private Player winner;
    
    public GameWindow(int size, MenuWindow menuWindow) {
        this.size = size;
        this.menuWindow = menuWindow;
        this.gameButtons = new ArrayList<>(5);
        
        this.menuWindow.getGameWindows().add(this);
        this.game = new Game(size);
        this.winner = null;
        
        JPanel top = new JPanel();   
       
        gameMessage = new JLabel();
        this.updateGameMessage();
        
        JButton newGameButton = new JButton();
        newGameButton.setText("New game");
        newGameButton.addActionListener(e -> this.startNewGame());
        
        top.add(gameMessage);
        top.add(newGameButton);
        
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(size, size));
        
        
        this.generateButtons();
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(panel, BorderLayout.CENTER);
    }
    
    public void generateButtons() {
        for (ArrayList<GameField> gameFields : game.getTable()) {
            this.gameButtons.add(new ArrayList<>());
            
            for (GameField gameField : gameFields) {
                this.gameButtons
                        .get(gameField.getPositionY())
                        .add(
                                gameField.getPositionX(), 
                                this.addButton(gameField.getPositionY(), gameField.getPositionX()));
            }   
        }
    }
    
    public void updateButtons() {
        var i = 0;
        for(ArrayList<JButton> jButtons : gameButtons) {
            var j = 0;
            for (JButton jButton : jButtons) {
                var currentField = game.getTable().get(i).get(j);
                setButtonView(jButton, currentField.getColor());
                
                if (currentField instanceof GameMovableField) {
                    this.addListener(jButton, (GameMovableField)currentField);
                }
                j++;
            }
            i++;
        }
    }
    
    private void setButtonView(JButton button,Color color) {
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(color);
        button.setFocusPainted(false);
    }
    
    private JButton addButton(final int i,
            final int j) {
        var currentField = this.game.getTable().get(i).get(j);
        JButton button = new JButton();
        button.setName(currentField.getName());
        this.setButtonView(button, currentField.getColor());
        if (currentField instanceof GameMovableField) {
           this.addListener(button, (GameMovableField)currentField);
        }

        panel.add(button);
        
        return button;
    }
    
    private void addListener(JButton button, GameMovableField currentField) {
        button.addActionListener(e -> {
            if (this.winner != null) {
                return; 
            }
            
          
            
            if (this.game.getActualPlayer() == ((GameMovableField) currentField).getPlayer()) {
                this.game.step(currentField.getPositionY(), currentField.getPositionX());
                this.updateGameMessage();
                this.updateButtons();
            }
            
            this.winner = game.findWinner();

            if (this.winner != null) {
                showGameOverMessage(winner);
            }
        });
    }
     
    private void showGameOverMessage(Player player) {
        JOptionPane.showMessageDialog(this, "Game is over. Winner is "
                + player.name());
        startNewGame();
    }
    
    
    public void startNewGame() {
        GameWindow newWindow = new GameWindow(size, menuWindow);
        newWindow.setVisible(true);
        this.dispose();
 
        menuWindow.getGameWindows().remove(this);
    }
    
    public void updateGameMessage() {
        gameMessage.setText("Current player: " 
                + this.game.getActualPlayer().name());
    }
    
    @Override
    protected void doUponExit() {
        super.doUponExit();
        menuWindow.getGameWindows().remove(this);
    }
}
