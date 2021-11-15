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

    /* Types */
    public static final int INTEGER = 0;
    public static final int FLOAT = 1;
    public static final int BOOLEAN = 2;
    public static final int CHARACTER = 3;
    public static final int CONSTANT = 4;
    public static final int STRING = 89;

    /* Keywords */
    public static final int IF = 5;
    public static final int ELSE = 6;
    public static final int WHILE = 7;
    public static final int FOR = 8;
    public static final int CROTOFUNC = 9;
    public static final int SWITCH = 10;
    public static final int CASE = 11;
    public static final int BREAK = 12;
    public static final int RETURN = 13;
    public static final int TRUE = 14;
    public static final int FALSE = 15;
    public static final int AND = 16;
    public static final int OR = 17;
    public static final int NOT = 18;

    /* Literal */
    public static final int STRING_VALUE = 19;
    public static final int INTEGER_VALUE = 20;
    public static final int DECIMAL_VALUE = 21;
    public static final int CHAR_VALUE = 22;

    /* Operators */
    public static final int ASSIGNMENT = 23;
    public static final int ADDITION = 24;
    public static final int SUBTRACTION = 25;
    public static final int MULTIPLICATION = 26;
    public static final int DIVISION = 27;
    public static final int EQUAL = 28;
    public static final int DIFFERENT = 29;
    public static final int GREATER = 30;
    public static final int LOWER = 31;
    public static final int GREATER_EQUAL = 32;
    public static final int LOWER_EQUAL = 33;
    public static final int DECREMENT = 34;
    public static final int INCREMENT = 35;

    public static final int IDENTIFIER = 38;

    /* Separators */
    public static final int LPAREN = 39;
    public static final int RPAREN = 40;
    public static final int LBRACE = 41;
    public static final int RBRACE = 42;
    public static final int LBRACK = 43;
    public static final int RBRACK = 44;
    public static final int SEMICOLON = 45;
    public static final int COLON = 46;
    public static final int PERIOD = 47;
    public static final int COMMA = 48;
    
    public static final int EOF = 50;
}
