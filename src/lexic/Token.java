/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package lexic;

/**
 *
 * @author elsho
 */
public class Token {
 
    public enum Type{
        INT,
        FLOAT,
        BOOL,
        CHAR,
        CONST
    }
    
    public enum Operator{
        ASSIGNMENT,     // =
        ADDITION,       // +
        SUBTRACTION,    // -
        MULTIPLICATION, // *
        DIVISION,       // /
        EQUAL,          // ==
        DIFFERENT,      // !=
        GREATER,        // >
        LOWER,          // <
        AND,
        OR,
        NOT
    }
    
    public enum Keywords{
        IF,
        ELSE,
        WHILE,
        FOR,
        CROTOFUNC
    }
    
    public enum Literal{
        STRING_LITERAL,
        INTEGER_LITERAL,
        DECIMAL_LITERAL,
        TRUE,
        FALSE
    }
    
    public enum Other{
        IDENTIFIER,
        END_OF_LINE,
        COMMENT,
        OPEN_P,         // (
        CLOSED_P,       // )
        OPEN_B,         // {
        CLOSED_B,       // }
        SEMICOLON,      // ;
        COLON,          // ,
        PERIOD          // .
    }
}
