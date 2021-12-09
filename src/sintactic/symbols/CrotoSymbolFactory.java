/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sintactic.symbols;

import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java_cup.runtime.SymbolFactory;

/**
 *
 * @author elsho
 */
public class CrotoSymbolFactory implements SymbolFactory{

    public Symbol newSymbol(String name, int id, Location left, Location right, Object value) {
        return new CrotoSymbol(name, id, left, right, value);
    }

    /**
     * newSymbol creates a complex symbol with Location objects for left and right
     * boundaries; this is used for terminals without values!
     */
    public Symbol newSymbol(String name, int id, Location left, Location right) {
        return new CrotoSymbol(name, id, left, right);
    }

    
    @Override
    public Symbol newSymbol(String string, int i, Symbol symbol, Symbol symbol1, Object o) {
        return new CrotoSymbol(string, i, symbol, symbol1, o);
    }

    @Override
    public Symbol newSymbol(String string, int i, Symbol symbol, Symbol symbol1) {
        return new CrotoSymbol(string, i, symbol, symbol1);
    }

    @Override
    public Symbol newSymbol(String string, int i, Symbol symbol, Object o) {
        return new CrotoSymbol(string, i, symbol, o);
    }

    @Override
    public Symbol newSymbol(String string, int i, Object o) {
        return new CrotoSymbol(string, i, o);
    }

    @Override
    public Symbol newSymbol(String string, int i) {
        return new CrotoSymbol(string, i);
    }

    @Override
    public Symbol startSymbol(String string, int i, int i1) {
        return new CrotoSymbol(string, i, i1);
    }
    
}
