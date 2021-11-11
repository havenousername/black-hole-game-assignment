/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmenttwoblackhole.game;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Objects;





interface PositionCalculate {
    int calculate(int a);
    Boolean condition(int a);
}
/**
 *
 * @author andreicristea
 */
public class GameMovableField extends GameField {
    private final int center;
    private final Player player; 
    
    public GameMovableField(int positionX, int positionY, String name, int center, Player player) {
        super(positionX, positionY, name);
        this.center = center;
        this.player = player;
    }
    
    class PositionPossibility {
        int positionX;
        int positionY;
        float possibility;

        public PositionPossibility(int positionX, int positionY, float possibility) {
            this.positionX = positionX;
            this.positionY = positionY;
            this.possibility = possibility;
        }
    }
    
    
    private PositionPossibility moveLeft(ArrayList<ArrayList<GameField>> map) {
        var expectedPositionX = this.positionX;
        while (expectedPositionX != 0) {
            var positionX = expectedPositionX - 1;
            
            if (map.get(this.positionY).get(positionX).getName().equals(FieldType.BLACK_HOLE.getType())) {
                return new PositionPossibility(center, center, 1);
            }
            
            if (map.get(this.positionY).get(positionX).getName().equals(FieldType.SPACESHIP.getType())) {
                break;
            }
            
             expectedPositionX--;
        }
        
        float possibility = this.calculateBlackHolePossibility(expectedPositionX, this.positionY);
        
        return new PositionPossibility(expectedPositionX, this.positionY, possibility);
    }
    
    private PositionPossibility moveRight(ArrayList<ArrayList<GameField>> map) {
        var expectedPositionX = this.positionX;
        while (expectedPositionX != map.get(this.positionY).size() - 1) {
            var positionX = expectedPositionX + 1;

            if (map.get(this.positionY).get(positionX).getName().equals(FieldType.BLACK_HOLE.getType())) {
               return new PositionPossibility(center, center, 1);
            }
            
            if (map.get(this.positionY).get(positionX).getName().equals(FieldType.SPACESHIP.getType())) {
                break;
            }
            
             expectedPositionX = positionX;
        }
        
        float possibility = this.calculateBlackHolePossibility(expectedPositionX, this.positionY);
        
        return new PositionPossibility(expectedPositionX, this.positionY ,  possibility);
    }
    
    private PositionPossibility moveTop(ArrayList<ArrayList<GameField>> map) {
        var expectedPositionY = this.positionY;
        while (expectedPositionY != 0) {
            var positionY = expectedPositionY - 1;
            
            if (map.get(positionY).get(this.positionX).getName().equals(FieldType.BLACK_HOLE.getType())) {
                return new PositionPossibility(center, center, 1);
            }
            
            if (map.get(positionY).get(this.positionX).getName().equals(FieldType.SPACESHIP.getType())) {
                break;
            }
            
             expectedPositionY = positionY;
        }
        
        float possibility = this.calculateBlackHolePossibility(this.positionX, expectedPositionY);
        
        return new PositionPossibility(this.positionX, expectedPositionY, possibility);
    }
    
    private PositionPossibility moveBottom(ArrayList<ArrayList<GameField>> map) {
        var expectedPositionY = this.positionY;
        while (expectedPositionY != map.size() - 1) {
            var positionY = expectedPositionY + 1;
            
            if (map.get(positionY).get(this.positionX).getName().equals(FieldType.BLACK_HOLE.getType())) {
                return new PositionPossibility(center, center, 1);
            }
            
            if (map.get(positionY).get(this.positionX).getName().equals(FieldType.SPACESHIP.getType())) {
                break;
            }
            
             expectedPositionY = positionY; 
            
        }
        
        float possibility = this.calculateBlackHolePossibility(this.positionX, expectedPositionY);
        
        return new PositionPossibility(this.positionX, expectedPositionY, possibility);
    }
    
    private float calculateBlackHolePossibility(int positionX, int positionY) {
        var possibilityX = positionX > center ? (float)( 2* center - positionX) : (float)(positionX);
        var possibilityY = positionY > center ?  (float)(2* center - positionY) : (float)(positionY);
        return ((possibilityX + possibilityY) / (2 * this.center));
    }
    
    private DoubleTuple<Integer> findMaxPossibility(ArrayList<PositionPossibility> positions, DoubleTuple lastPosition) {
        DoubleTuple<Integer> bestMatch = new DoubleTuple<>(-10, -10);
        float bestPossibility = 0;
        
        for (PositionPossibility position : positions) {
            if (bestPossibility < position.possibility && 
                    !lastPosition.equals(position.positionX, position.positionY)) {
                bestPossibility = position.possibility;
                bestMatch.first = position.positionX;
                bestMatch.last = position.positionY;
            }
        }
        
        return bestMatch;
    }
    
    
    public DoubleTuple<DoubleTuple<Integer>> move(ArrayList<ArrayList<GameField>> map) {
       ArrayList<PositionPossibility> positions = new ArrayList<>();
       positions.add(this.moveLeft(map));
       positions.add(this.moveRight(map));
       positions.add(this.moveTop(map));
       positions.add(this.moveBottom(map));
       
       var lastPosition = new DoubleTuple<Integer>(this.positionX, this.positionY);
       DoubleTuple<Integer> bestMatch = this.findMaxPossibility(positions, lastPosition);
       
//        System.out.println("Best match: " +  bestMatch.toString());
//        System.out.println("Last position: " +  lastPosition.toString());
     
       this.positionX = bestMatch.first;
       this.positionY = bestMatch.last;
       if (!lastPosition.equals(bestMatch)) {
           map.get(lastPosition.last).set(lastPosition.first, new GameField(lastPosition.first, lastPosition.last, FieldType.EMPTY_FIELD.getType()));
           
           if (!bestMatch.equals(center, center)) {
               map.get(bestMatch.last).set(bestMatch.first, this);
           }
       }
       
       return new DoubleTuple(lastPosition, bestMatch);
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    @Override
    public Color getColor() {
        if (this.name.equals(FieldType.SPACESHIP.getType()) && player.equals(Player.PLAYER_ONE)) {
            return Color.CYAN;
        } else if (this.name.equals(FieldType.SPACESHIP.getType()) && player.equals(Player.PLAYER_TWO)) {
            return Color.PINK;
        } else {
            return super.getColor();
        }
    }
    
    @Override
    public String toString() {
        return "GameMovableField{" + "positionX=" + positionX + ", positionY=" + positionY + ", name=" + name + ", center=" + center + ", player=" + player + '}';
    }
}
