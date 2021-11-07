/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package lexic;

/**
 *
 * @author elsho
 */
public enum Token {
    INTEGER(Parent.TYPE),
    STRING,
    BOOLEAN,
    IDENTIFIER, // Variable, function names
    ASSIGNMENT, // =
    RESERVED,   // Keywords
    ADDITION,   // +
    SUBTRACTION,// - 
    EQUAL,      // == 
    NOT_EQUAL,  // !=
    AND,        // &&
    OR,         // ||
    ERROR;
    
    private Parent parent;
    
    Token(Parent parent){
        this.parent = parent;
    }
    
    public Parent getParent() { return this.parent; }
    
    public enum Parent {
        KEYWORD,
        OPERATOR,
        TYPE
    }
}
