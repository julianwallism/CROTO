/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
package semantic.symbols;

import semantic.Type;
import semantic.symbols.Structure.Instruction;

public class VarDeclaration extends Instruction {

    public boolean constant;
    public Type type;
    public Identifier id;
    public Expression expr;

    public VarDeclaration(boolean constant, Type type, Identifier id, int line, int column) {
        super(line, column);
        this.constant = constant;
        this.type = type;
        this.id = id;
        this.expr = null;
    }

    public VarDeclaration(boolean constant, Type type, Identifier id, Expression expr, int line, int column) {
        super(line, column);
        this.constant = constant;
        this.type = type;
        this.id = id;
        this.expr = expr;
    }

    @Override
    public void check(Visitor v) {
        v.visit(this);
    }
}
