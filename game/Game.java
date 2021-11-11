/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmenttwoblackhole.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author andreicristea
 */
public class Game {
    private final int size;
    private final ArrayList<ArrayList<GameField>> table;
    private final int center;
    
    private Player actualPlayer;
    
    public Game(int size) {
        this.size = size;
        actualPlayer = Player.PLAYER_ONE;
        this.center = size / 2;
        
        table = new ArrayList<>(size);
        initTable();
    }
    
    private void initTable() {
        for (int yAxis = 0; yAxis < size; yAxis++) {
           table.add(new ArrayList<>(size));
           for (int xAxis = 0; xAxis < size; xAxis++) {
               if (xAxis == center && yAxis == center) {
                   table.get(yAxis).add(xAxis, new GameField(xAxis, yAxis, FieldType.BLACK_HOLE.getType()));
               } else if (yAxis == xAxis) {
                   table.get(yAxis).add(xAxis, new GameMovableField(xAxis, yAxis, FieldType.SPACESHIP.getType(), center, Player.PLAYER_ONE));
               } else if ( size - yAxis - 1 == xAxis ) {
                   table.get(yAxis).add(xAxis, new GameMovableField(xAxis, yAxis, FieldType.SPACESHIP.getType(), center, Player.PLAYER_TWO));
               } else {
                   table.get(yAxis).add(xAxis, new GameField(xAxis, yAxis, FieldType.EMPTY_FIELD.getType()));
               }
           }
        }
    }
    
    public void resetTable() {
        this.table.clear();
        this.initTable();
    }
    
    private int findPlayerFleetSize(Player player) {
        var table = this.table.stream().flatMap(Collection::stream)
                           .collect(Collectors.toList());
        return (int)table.stream()
                .filter((gameField) -> gameField instanceof GameMovableField)
                .filter(gameField -> ((GameMovableField)gameField).getPlayer().equals(player))
                .count();
    } 
    
    public void step(int row, int column) {
        GameField gameField = table.get(row).get(column);
        System.out.println("game step on: " + gameField);
        if (gameField instanceof GameMovableField) {
            ((GameMovableField) gameField).move(table);
        }
        
        if (this.actualPlayer.equals(Player.PLAYER_ONE)) {
            this.actualPlayer = Player.PLAYER_TWO;
        } else {
            this.actualPlayer = Player.PLAYER_ONE;
        }
    }
    
    
    
    public Player findWinner() {
        var sizeOfPlayerOneFleet = this.findPlayerFleetSize(Player.PLAYER_ONE);
        var sizeOfPlayerTwoFleet = this.findPlayerFleetSize(Player.PLAYER_TWO);
        
        if (sizeOfPlayerOneFleet <= center) {
            return Player.PLAYER_ONE;
        } else if (sizeOfPlayerTwoFleet <= center) {
            return Player.PLAYER_TWO;
        }
        
        return null;
    }
    
    public int getSize() {
        return this.size;
    }

    public ArrayList<ArrayList<GameField>> getTable() {
        return table;
    }

    public int getCenter() {
        return center;
    }

    public Player getActualPlayer() {
        return actualPlayer;
    }
    
    public String getTableView() {
        String tableView = "";
        for (ArrayList<GameField> gameFields : table) {
            tableView += "[";
            for (GameField gameField : gameFields) {
                tableView += "{" + gameField.getPositionX()+ ", " + gameField.getPositionY() + "," + gameField.getName() + "}";
            }
            tableView += "]\n";
        }
        
        return tableView;
    }
}
