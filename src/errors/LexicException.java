/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
package errors;

public class LexicException extends CrotoException {

    private String message;

    public LexicException(int line, int column, String message) {
        super(line, column);
        this.message = message;
    }

    @Override
    public String toString() {
        return "Lexic Error: Line " + line + ", column " + column + ". " + message;
    }
}