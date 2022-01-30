/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
package errors;


public abstract class CrotoException extends Exception {
    
    protected int line, column;
    
    public CrotoException(int line, int column){
        this.line = line;
        this.column = column;
    }
}
