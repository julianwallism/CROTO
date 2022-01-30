/**
 * Practica Final Compiladores - 2021/2022
 * 
 * Jonathan Salisbury Vega
 * Julián Wallis Medina
 * Joan Sansó Pericàs
 */
package semantic.symbols;

import java.util.ArrayList;

import semantic.Type;

public class Method extends Structure {

    public Identifier id;
    public CodeBlock codeBlock;
    public Type returnType;
    public ArrayList<Parameter> params;

    public Method(Identifier id, CodeBlock cb, int line, int column) {
        super(line, column);
        this.id = id;
        this.codeBlock = cb;
        params = new ArrayList<Parameter>();
        this.returnType = Type.VOID;
    }

    public Method(Type type, Identifier id, CodeBlock cb, int line, int column) {
        super(line, column);
        this.id = id;
        this.codeBlock = cb;
        this.returnType = type;
        params = new ArrayList<Parameter>();
    }

    public Method(Identifier id, ArrayList<Parameter> params, CodeBlock cb, int line, int column) {
        super(line, column);
        this.id = id;
        this.codeBlock = cb;
        this.params = params;
        this.returnType = Type.VOID;
    }

    public Method(Type type, Identifier id, ArrayList<Parameter> params, CodeBlock cb,
            int line, int column) {
        super(line, column);
        this.id = id;
        this.codeBlock = cb;
        this.returnType = type;
        this.params = params;
    }

    public static class Parameter extends Structure {

        public Type type;
        public Identifier id;

        public Parameter(Type type, Identifier id, int line, int column) {
            super(line, column);
            this.type = type;
            this.id = id;
        }

        @Override
        public void check(Visitor v) {
            v.visit(this);
        }
    }

    @Override
    public void check(Visitor v) {
        v.visit(this);
    }
}
