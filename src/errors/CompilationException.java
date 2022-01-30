/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
package errors;

public class CompilationException extends CrotoException {


    public CompilationException(int line, int column) {
        super(line, column);
    }

    @Override
    public String toString() {
        return "Compilation Error: Line " + line + ", column " + column + ".";
    }
}