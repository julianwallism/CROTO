/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
package semantic.symbols;

public class Identifier extends Structure {

    public String name;

    public Identifier(String id) {
        this.name = id;
    }

    public Identifier(String name, int line, int column) {
        super(line, column);
        this.name = name;
    }

    @Override
    public void check(Visitor v) {
        v.visit(this);
    }
}
