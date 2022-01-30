/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
package sintactic.symbols;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.ComplexSymbol;
import java_cup.runtime.Symbol;

public class CrotoSymbol extends ComplexSymbol {

    public CrotoSymbol(String name, int id) {
        super(name, id);
    }

    public CrotoSymbol(String name, int id, Object value) {
        super(name, id, value);
    }

    public CrotoSymbol(String name, int id, int state) {
        super(name, id, state);
    }

    public CrotoSymbol(String name, int id, Symbol left, Symbol right) {
        super(name, id, left, right);
    }

    public CrotoSymbol(String name, int id, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right) {
        super(name, id, left, right);
    }

    public CrotoSymbol(String name, int id, Symbol left, Symbol right, Object value) {
        super(name, id, left, right, value);
    }

    public CrotoSymbol(String name, int id, Symbol left, Object value) {
        super(name, id, left, value);
    }

    public CrotoSymbol(String name, int id, ComplexSymbolFactory.Location left, ComplexSymbolFactory.Location right,
            Object value) {
        super(name, id, left, right, value);
    }

    @Override
    public String toString() {
        return name + " line: " + xleft.getLine() + ", column: " + xleft.getColumn();
    }
}
