package semantic.symbols;

import java.util.ArrayList;

import semantic.Type;

public class Method extends Structure {

    public Identifier id;
    public CodeBlock cb;
    public Type returnType;
    public ArrayList<Parameter> params;
    public Expression returnExpression;

    public Method(Identifier id, CodeBlock cb, int line, int column) {
        super(line, column);
        this.id = id;
        this.cb = cb;
        params = new ArrayList<Parameter>();
        this.returnExpression = null;
    }

    public Method(Type type, Identifier id, CodeBlock cb, Expression returnExpression, int line, int column) {
        super(line, column);
        this.id = id;
        this.cb = cb;
        this.returnType = type;
        params = new ArrayList<Parameter>();
        this.returnExpression = returnExpression;
    }

    public Method(Identifier id, ArrayList<Parameter> params, CodeBlock cb, int line, int column) {
        super(line, column);
        this.id = id;
        this.cb = cb;
        this.params = params;
        this.returnExpression = null;
    }

    public Method(Type type, Identifier id, ArrayList<Parameter> params, CodeBlock cb, Expression returnExpression,
            int line, int column) {
        super(line, column);
        this.id = id;
        this.cb = cb;
        this.returnType = type;
        this.params = params;
        this.returnExpression = returnExpression;
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
