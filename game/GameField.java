/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmenttwoblackhole.game;

import java.awt.Color;

/**
 *
 * @author andreicristea
 */
public class GameField {
    protected int positionX;
    protected int positionY;
    protected final String name;
    
    GameField(int positionX,int positionY, String name) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.name = name;
    }
    
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
    
    public String getName() {
        return name;
    }
    
    public Color getColor() {
        if (this.name.equals(FieldType.BLACK_HOLE.getType())) { 
            return Color.BLACK;
        }
        
        return Color.WHITE;
    } 
    
    @Override
    public String toString() {
        return "GameField{" + "positionX=" + positionX + ", positionY=" + positionY + ", name=" + name + '}';
    }
}
