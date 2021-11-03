/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package croto.lexic;

/**
 *
 * @author elsho
 */
public enum Token {
    INTEGER,
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
    ERROR
}
