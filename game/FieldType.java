/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignmenttwoblackhole.game;

/**
 *
 * @author andreicristea
 */
enum FieldType {
    BLACK_HOLE("Black Hole"),
    EMPTY_FIELD("Empty Field"), 
    SPACESHIP("Spaceship");
    
    private final String name;
    
    private FieldType(final String name) {
        this.name = name;
    }
    
    public String getType() {
        return name;
    }
}