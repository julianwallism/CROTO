/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package errors;

/**
 *
 * @author elsho
 */
public abstract class CrotoException extends Exception {
    
    protected int line, column;
    
    public CrotoException(int line, int column){
        this.line = line;
        this.column = column;
    }
}
